package learn.qzy.rpc.fault.tolerant;

import learn.qzy.rpc.model.RpcResponse;

import java.util.Map;

/**
 * @author qzy
 * @time 2025年1月18日 17:00 星期六
 * @title 容错策略——快速失败（立刻通知外层调用方）
 */
public class FailFastTolerantStrategy implements TolerantStrategy{
    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        throw new RuntimeException("服务报错", e);
    }
}
