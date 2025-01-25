package learn.qzy.rpc.fault.tolerant;

import learn.qzy.rpc.model.RpcResponse;

import java.util.Map;

/**
 * @author qzy
 * @time 2025年1月18日 17:05 星期六
 * @title 容错策略——转移到其它服务节点
 */
public class FailOverTolerantStrategy implements TolerantStrategy{
    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        // todo 获取其它服务节点并调用
        return null;
    }
}
