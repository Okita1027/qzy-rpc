package learn.qzy.rpc.fault.retry;

/**
 * @author qzy
 * @time 2025年1月18日 11:28 星期六
 * @title 重试策略键名常量
 */
public interface RetryStrategyKeys {
    /**
     * 不重试
     */
    String NO = "no";

    /**
     * 固定时间间隔
     */
    String FIXED_INTERVAL = "fixedInterval";
}
