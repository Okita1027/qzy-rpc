package learn.qzy.rpc.fault.tolerant;

import learn.qzy.rpc.spi.SpiLoader;

/**
 * @author qzy
 * @time 2025年1月18日 17:10 星期六
 * @title
 */
public class TolerantStrategyFactory {
    static {
        SpiLoader.load(TolerantStrategy.class);
    }

    /**
     * 默认容错策略
     */
    private static final TolerantStrategy DEFAULT_RETRY_STRATEGY = new FailFastTolerantStrategy();

    /**
     * 获取实例
     */
    public static TolerantStrategy getInstance(String key) {
        return SpiLoader.getInstance(TolerantStrategy.class, key);
    }
}
