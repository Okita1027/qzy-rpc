package learn.qzy.example.provider;

import learn.qzy.example.common.service.UserService;
import learn.qzy.rpc.RpcApplication;
import learn.qzy.rpc.config.RegistryConfig;
import learn.qzy.rpc.config.RpcConfig;
import learn.qzy.rpc.model.ServiceMetaInfo;
import learn.qzy.rpc.registry.LocalRegistry;
import learn.qzy.rpc.registry.Registry;
import learn.qzy.rpc.registry.RegistryFactory;
import learn.qzy.rpc.server.VertxHttpServer;
import learn.qzy.rpc.server.tcp.VertxTcpServer;

/**
 * @author qzy
 * @time 2025年1月05日 20:39 星期日
 * @title
 */
public class ProviderExample {
    public static void main(String[] args) {
        // RPC框架初始化
        RpcApplication.init();

        // 注册服务
        String serviceName = UserService.class.getName();
        LocalRegistry.register(serviceName, UserServiceImpl.class);

        // 注册服务到注册中心
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName(serviceName);
        serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
        serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
        try {
            registry.register(serviceMetaInfo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // 启动 TCP 服务
        VertxTcpServer vertxTcpServer = new VertxTcpServer();
        vertxTcpServer.doStart(RpcApplication.getRpcConfig().getServerPort());

        // 启动web服务
//        VertxHttpServer httpServer = new VertxHttpServer();
//        httpServer.doStart(RpcApplication.getRpcConfig().getServerPort());

    }
}
