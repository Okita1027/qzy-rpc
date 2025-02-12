package learn.qzy.rpc;

import learn.qzy.rpc.config.RegistryConfig;
import learn.qzy.rpc.registry.Registry;
import learn.qzy.rpc.registry.RegistryFactory;
import lombok.extern.slf4j.Slf4j;
import learn.qzy.rpc.config.RpcConfig;
import learn.qzy.rpc.constant.RpcConstant;
import learn.qzy.rpc.utils.ConfigUtils;

/**
 * @author qzy
 * @time 2025年1月02日 15:53 星期四
 * @title RPC框架应用：相当于holder,存放了项目全局用到的变量。双检锁单例模式实现
 */
@Slf4j
public class RpcApplication {

    private static volatile RpcConfig rpcConfig;

    /**
     * 框架初始化，支持传入自定义配置
     */
    public static void init(RpcConfig newRpcConfig) {
        rpcConfig = newRpcConfig;
        log.info("RpcApplication init, config = {}", newRpcConfig.toString());
        // 注册中心初始化
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
        registry.init(registryConfig);
        log.info("registry init, config = {}", registryConfig);

        // 创建并注册 Shutdown Hook, JVM 退出时执行操作 下线节点
        Runtime.getRuntime().addShutdownHook(new Thread(registry::destroy));
    }

    /**
     * 初始化
     */
    public static void init() {
        RpcConfig newRpcConfig;
        try {
            newRpcConfig = ConfigUtils.loadConfig(RpcConfig.class, RpcConstant.DEFAULT_CONFIG_PREFIX);
        } catch (Exception e) {
            newRpcConfig = new RpcConfig();
        }
        init(newRpcConfig);
        // 监听配置文件的变化
        ConfigUtils.watchConfig(RpcConfig.class, RpcConstant.DEFAULT_CONFIG_PREFIX, null, config -> {
            System.out.println("RPC Config Updated:" + config);
        });
    }

    /**
     * 获取配置
     */
    public static RpcConfig getRpcConfig() {
        if (rpcConfig == null) {
            synchronized (RpcApplication.class) {
                if (rpcConfig == null) {
                    init();
                }
            }
        }
        return rpcConfig;
    }
}
