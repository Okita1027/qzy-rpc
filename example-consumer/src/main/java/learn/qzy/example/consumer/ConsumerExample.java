package learn.qzy.example.consumer;

import learn.qzy.rpc.config.RpcConfig;
import learn.qzy.rpc.utils.ConfigUtils;

/**
 * @author qzy
 * @time 2025年1月02日 16:25 星期四
 * @title 简易服务消费者示例
 */
public class ConsumerExample {
    public static void main(String[] args) {
        RpcConfig rpc = ConfigUtils.loadConfig(RpcConfig.class, "learn/qzy/rpc");
        System.out.println("rpc = " + rpc);
    }
}
