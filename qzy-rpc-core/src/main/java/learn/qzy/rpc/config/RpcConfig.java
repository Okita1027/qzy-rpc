package learn.qzy.rpc.config;

import learn.qzy.rpc.serializer.SerializerKeys;
import lombok.Data;

/**
 * @author qzy
 * @time 2025年1月02日 15:33 星期四
 * @title RPC框架配置
 */
@Data
public class RpcConfig {
    /**
     * 名称
     */
    private String name = "qzy-rpc";

    /**
     * 版本号
     */
    private String version = "1.0";

    /**
     * 服务器主机名
     */
    private String serverHost = "localhost";

    /**
     * 服务器端口号
     */
    private Integer serverPort = 8080;

    /**
     * 模拟调用
     */
    private Boolean mock = false;

    /**
     * 序列化器
     */
    private String serializer = SerializerKeys.JDK;
}
