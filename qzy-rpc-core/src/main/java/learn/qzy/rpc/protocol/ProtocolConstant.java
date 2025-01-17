package learn.qzy.rpc.protocol;

/**
 * @author qzy
 * @time 2025年1月17日 10:06 星期五
 * @title 协议常量
 */
public interface ProtocolConstant {
    /**
     * 消息头长度
     */
    int MESSAGE_HEADER_LENGTH = 17;

    /**
     * 协议魔数
     */
    byte PROTOCOL_MAGIC = 0x1;

    /**
     * 协议版本号
     */
    byte PROTOCOL_VERSION = 0x1;
}
