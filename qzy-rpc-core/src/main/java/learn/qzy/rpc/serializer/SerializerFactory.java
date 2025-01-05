package learn.qzy.rpc.serializer;

import learn.qzy.rpc.spi.SpiLoader;

/**
 * @author qzy
 * @time 2025年1月03日 18:38 星期五
 * @title 序列化器工厂（用于获取序列化器对象）
 */
public class SerializerFactory {

    /**
     * 默认序列化器
     */
    private static final Serializer DEFAULT_SERIALIZER = new JdkSerializer();

    /**
     * 获取实例
     */
    public static Serializer getInstance(String key) {
        return SpiLoader.getInstance(Serializer.class, key);
    }

}
