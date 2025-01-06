package learn.qzy.rpc.registry;

import learn.qzy.rpc.model.ServiceMetaInfo;

import java.util.List;

/**
 * @author qzy
 * @time 2025年1月06日 11:00 星期一
 * @title 注册中心服务本地缓存
 */
public class RegistryServiceCache {
    /**
     * 服务缓存
     */
    private List<ServiceMetaInfo> serviceCache = null;

    /**
     * 写缓存
     */
    public void writeCache(List<ServiceMetaInfo> newServiceCache) {
        this.serviceCache = newServiceCache;
    }

    /**
     * 读缓存
     */
    public List<ServiceMetaInfo> readCache() {
        return this.serviceCache;
    }

    /**
     * 清空缓存
     */
    public void clearCache() {
        this.serviceCache = null;
    }
}
