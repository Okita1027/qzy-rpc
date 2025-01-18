package learn.qzy.rpc.fault.retry;

import learn.qzy.rpc.model.RpcResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

/**
 * @author qzy
 * @time 2025年1月18日 9:01 星期六
 * @title 重试策略：不重试
 */
@Slf4j
public class NoRetryStrategy implements RetryStrategy {
    @Override
    public RpcResponse doRetry(Callable<RpcResponse> callable) throws Exception {
        return callable.call();
    }
}
