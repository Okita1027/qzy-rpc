package learn.qzy.rpc.registry;

import learn.qzy.rpc.config.RegistryConfig;
import learn.qzy.rpc.model.ServiceMetaInfo;

import java.util.List;

/**
 * @author qzy
 * @time 2025年1月05日 10:47 星期日
 * @title 注册中心接口
 */
public interface Registry {

    /**
     * 初始化
     */
    void init(RegistryConfig registryConfig);

    /**
     * 注册服务（服务端）
     */
    void register(ServiceMetaInfo serviceMetaInfo) throws Exception;

    /**
     * 注销服务（服务端）
     */
    void unRegister(ServiceMetaInfo serviceMetaInfo);

    /**
     * 服务发现（获取某服务的所有节点，消费端）
     */
    List<ServiceMetaInfo> serviceDiscovery(String serviceKey);

    /**
     * 服务销毁
     */
    void destroy();

    /**
     * 心跳检测（服务端）
     */
    void heartBeat();

    /**
     * 服务监听（消费端）
     */
    void watch(String serviceNodeKey);

}
