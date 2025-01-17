package learn.qzy.rpc.loadbalancer;

import learn.qzy.rpc.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;

/**
 * @author qzy
 * @time 2025年1月17日 15:44 星期五
 * @title 负载均衡器（消费端使用）
 */
public interface LoadBalancer {
    /**
     * 选择服务调用
     *
     * @param requestParams       请求参数
     * @param serviceMetaInfoList 可用服务列表
     * @return 服务元信息
     */
    ServiceMetaInfo select(Map<String, Object> requestParams, List<ServiceMetaInfo> serviceMetaInfoList);
}
