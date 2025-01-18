package learn.qzy.rpc.fault.retry;

import learn.qzy.rpc.model.RpcResponse;

import java.util.concurrent.Callable;

/**
 * @author qzy
 * @time 2025年1月18日 8:58 星期六
 * @title 重试策略
 */
public interface RetryStrategy {
    /**
     * 重试
     */
    RpcResponse doRetry(Callable<RpcResponse> callable) throws Exception;
}
