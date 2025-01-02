package rpc.server;

/**
* @time 2025年1月01日 17:56 星期三
* @author qzy
* @title HTTP 服务器接口
*/
public interface HttpServer {
    /**
     * 启动服务器
     */
    void doStart(int port);
}
