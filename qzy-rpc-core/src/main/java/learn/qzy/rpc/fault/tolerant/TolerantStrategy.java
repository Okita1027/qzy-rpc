package learn.qzy.rpc.fault.tolerant;

import learn.qzy.rpc.model.RpcResponse;

import java.util.Map;

/**
 * @author qzy
 * @time 2025年1月18日 16:57 星期六
 * @title 容错策略
 */
public interface TolerantStrategy {

    /**
     * 执行容错策略
     *
     * @param context 上下文，用于传递数据
     * @param e       异常
     * @return
     */
    RpcResponse doTolerant(Map<String, Object> context, Exception e);
}
