package learn.qzy.rpc.serializer;

import java.io.IOException;

/**
 * @author qzy
 * @time 2025年1月01日 19:29 星期三
 * @title 序列号器接口
 */
public interface Serializer {
    /**
     * 序列化
     * @param object
     * @return
     * @param <T>
     * @throws IOException
     */
    <T> byte[] serialize(T object) throws IOException;

    /**
     * 反序列化
     * @param bytes
     * @param type
     * @return
     * @param <T>
     * @throws IOException
     */
    <T> T deserialize(byte[] bytes, Class<T> type) throws IOException;
}
