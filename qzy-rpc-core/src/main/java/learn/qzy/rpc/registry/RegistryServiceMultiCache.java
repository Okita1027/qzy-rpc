package learn.qzy.rpc.registry;

import learn.qzy.rpc.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author qzy
 * @time 2025年1月06日 17:54 星期一
 * @title 注册中心服务缓存（支持多个服务）
 */
public class RegistryServiceMultiCache {
    /**
     * 服务缓存
     */
    private final Map<String, List<ServiceMetaInfo>> serviceCache = new ConcurrentHashMap<>();

    /**
     * 写缓存
     * @param serviceKey 服务键名
     * @param newServiceCache 更新后的缓存列表
     */
    public void writeCache(String serviceKey, List<ServiceMetaInfo> newServiceCache) {
        this.serviceCache.put(serviceKey, newServiceCache);
    }

    /**
     * 读缓存
     * @param serviceKey 服务键名
     * @return 服务缓存
     */
    public List<ServiceMetaInfo> readCache(String serviceKey) {
        return this.serviceCache.get(serviceKey);
    }

    /**
     * 清空缓存
     * @param serviceKey 服务键名
     */
    public void clearCache(String serviceKey) {
        this.serviceCache.remove(serviceKey);
    }
}
