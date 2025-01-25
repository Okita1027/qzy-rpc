package learn.qzy.rpc.fault.tolerant;

import learn.qzy.rpc.model.RpcResponse;

import java.util.Map;

/**
 * @author qzy
 * @time 2025年1月18日 17:04 星期六
 * @title 容错策略——降级到其它服务
 */
public class FailBackTolerantStrategy implements TolerantStrategy{
    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        // todo 获取降低的服务并调用
        return null;
    }
}
