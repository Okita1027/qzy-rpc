package learn.qzy.rpc.fault.retry;

import com.github.rholder.retry.*;
import learn.qzy.rpc.model.RpcResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author qzy
 * @time 2025年1月18日 9:02 星期六
 * @title 重试策略：固定间隔重试
 */
@Slf4j
public class FixedIntervalRetryStrategy implements RetryStrategy {
    @Override
    public RpcResponse doRetry(Callable<RpcResponse> callable) throws ExecutionException, RetryException {
        Retryer<RpcResponse> retryer = RetryerBuilder.<RpcResponse>newBuilder()
                .retryIfExceptionOfType(Exception.class)    // 若出现异常则重试
                .withWaitStrategy(WaitStrategies.fixedWait(3L, TimeUnit.SECONDS))   // 每次重试间隔 3 秒
                .withStopStrategy(StopStrategies.stopAfterAttempt(3))   // 最多重试 3 次
                .withRetryListener(new RetryListener() {
                    @Override
                    public <V> void onRetry(Attempt<V> attempt) {
                        log.info("重试次数：{}", attempt.getAttemptNumber());
                        if (attempt.hasException()) {
                            log.error("重试失败，失败原因：{}", attempt.getExceptionCause().getMessage());
                        } else {
                            log.info("重试成功，结果：{}", attempt.getResult());
                        }
                    }
                }).build();
        return retryer.call(callable);
    }
}
