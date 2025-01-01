package learn.qzy.example.provider;

import learn.qzy.example.common.service.UserService;
import learn.qzy.rpc.registry.LocalRegistry;
import learn.qzy.rpc.server.VertxHttpServer;

/**
 * @author qzy
 * @time 2025年1月01日 17:43 星期三
 * @title 简易服务提供者示例
 */
public class EasyProviderExample {
    public static void main(String[] args) {
        // 注册服务
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);
        // 启动web服务
        VertxHttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(8080);
    }
}
