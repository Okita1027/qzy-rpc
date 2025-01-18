package learn.qzy.rpc.fault.retry;

import learn.qzy.rpc.spi.SpiLoader;

/**
 * @author qzy
 * @time 2025年1月18日 12:46 星期六
 * @title 重试策略工厂（用于获取重试器对象）
 */
public class RetryStrategyFactory {

    static {
        SpiLoader.load(RetryStrategy.class);
    }

    /**
     * 默认重试器
     */
    private static final RetryStrategy DEFAULT_RETRY_STRATEGY = new NoRetryStrategy();

    /**
     * 获取实例
     */
    public static RetryStrategy getInstance(String key) {
        return SpiLoader.getInstance(RetryStrategy.class, key);
    }

}
