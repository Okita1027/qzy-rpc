package learn.qzy.example.provider;

import learn.qzy.example.common.service.UserService;
import learn.qzy.rpc.bootstrap.ProviderBootStrap;
import learn.qzy.rpc.model.ServiceRegisterInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qzy
 * @time 2025年1月05日 20:39 星期日
 * @title
 */
public class ProviderExample {
    public static void main(String[] args) {
        // 要注册的服务
        List<ServiceRegisterInfo<?>> serviceRegisterInfoList = new ArrayList<>();
        ServiceRegisterInfo serviceRegisterInfo = new ServiceRegisterInfo(UserService.class.getName(), UserServiceImpl.class);
        serviceRegisterInfoList.add(serviceRegisterInfo);
        // 服务提供者初始化
        ProviderBootStrap.init(serviceRegisterInfoList);
    }
/*    public static void main(String[] args) {
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

    }*/
}
