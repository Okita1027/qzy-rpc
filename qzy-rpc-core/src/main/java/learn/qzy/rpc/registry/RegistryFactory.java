package learn.qzy.rpc.registry;

import learn.qzy.rpc.spi.SpiLoader;

/**
 * @author qzy
 * @time 2025年1月05日 17:33 星期日
 * @title 注册中心工厂（用于获取注册中心对象）
 */
public class RegistryFactory {
    static {
        SpiLoader.load(Registry.class);
    }

    /**
     * 默认注册中心
     */
    private static final Registry DEFAULT_REGISTRY = new EtcdRegistry();

    /**
     * 获取实例
     */
    public static Registry getInstance(String key) {
        return SpiLoader.getInstance(Registry.class, key);
    }

}
