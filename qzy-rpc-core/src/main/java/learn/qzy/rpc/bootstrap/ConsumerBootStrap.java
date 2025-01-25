package learn.qzy.rpc.bootstrap;

import learn.qzy.rpc.RpcApplication;

/**
 * @author qzy
 * @time 2025年1月25日 14:51 星期六
 * @title 服务消费者启动类（初始化）
 */
public class ConsumerBootStrap {
    /**
     * 初始化
     */
    public static void init() {
        // RPC框架初始化（配置和注册中心）
        RpcApplication.init();
    }
}
