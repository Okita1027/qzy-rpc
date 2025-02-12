package learn.qzy.rpc.proxy;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetClient;
import learn.qzy.rpc.RpcApplication;
import learn.qzy.rpc.config.RpcConfig;
import learn.qzy.rpc.constant.RpcConstant;
import learn.qzy.rpc.fault.retry.RetryStrategy;
import learn.qzy.rpc.fault.retry.RetryStrategyFactory;
import learn.qzy.rpc.fault.tolerant.TolerantStrategy;
import learn.qzy.rpc.fault.tolerant.TolerantStrategyFactory;
import learn.qzy.rpc.loadbalancer.LoadBalancer;
import learn.qzy.rpc.loadbalancer.LoadBalancerFactory;
import learn.qzy.rpc.model.RpcRequest;
import learn.qzy.rpc.model.RpcResponse;
import learn.qzy.rpc.model.ServiceMetaInfo;
import learn.qzy.rpc.protocol.*;
import learn.qzy.rpc.registry.Registry;
import learn.qzy.rpc.registry.RegistryFactory;
import learn.qzy.rpc.serializer.JdkSerializer;
import learn.qzy.rpc.serializer.Serializer;
import learn.qzy.rpc.serializer.SerializerFactory;
import learn.qzy.rpc.server.tcp.VertxTcpClient;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author qzy
 * @time 2025年1月01日 20:32 星期三
 * @title 服务代理（动态代理）
 */
public class ServiceProxy implements InvocationHandler {
    /**
     * 调用代理
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 指定序列化器
        // final Serializer serializer = SerializerFactory.getInstance(RpcApplication.getRpcConfig().getSerializer());

        // 构造请求
        String serviceName = method.getDeclaringClass().getName();
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(serviceName)
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();
        // try {
        // 序列化
        // byte[] bodyBytes = serializer.serialize(rpcRequest);
        // 从注册中心获取服务提供者请求地址
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        Registry registry = RegistryFactory.getInstance(rpcConfig.getRegistryConfig().getRegistry());
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName(serviceName);
        serviceMetaInfo.setServiceVersion(RpcConstant.DEFAULT_SERVICE_VERSION);
        List<ServiceMetaInfo> serviceMetaInfoList = registry.serviceDiscovery(serviceMetaInfo.getServiceKey());
        if (CollUtil.isEmpty(serviceMetaInfoList)) {
            throw new RuntimeException("暂无服务地址!");
        }

        // 负载均衡
        LoadBalancer loadBalancer = LoadBalancerFactory.getInstance(rpcConfig.getLoadBalancer());
        // 将调用方法名（请求路径）作为负载均衡参数
        Map<String, Object> requestParams = new HashMap<>();
        requestParams.put("methodName", rpcRequest.getMethodName());
        ServiceMetaInfo selectedServiceMetaInfo = loadBalancer.select(requestParams, serviceMetaInfoList);

        // 使用重试机制、发送 TCP 请求
        RpcResponse rpcResponse;
        try {
            RetryStrategy retryStrategy = RetryStrategyFactory.getInstance(rpcConfig.getRetryStrategy());
            rpcResponse = retryStrategy.doRetry(() ->
                    VertxTcpClient.doRequest(rpcRequest, selectedServiceMetaInfo)
            );
        } catch (Exception e) {
            // 容错机制
            TolerantStrategy tolerantStrategy = TolerantStrategyFactory.getInstance(rpcConfig.getTolerantStrategy());
            rpcResponse = tolerantStrategy.doTolerant(null, e);
        }
        return rpcResponse.getData();
        // } catch (Exception e) {
        //     throw new RuntimeException("调用失败!");
        // }
    }
}
