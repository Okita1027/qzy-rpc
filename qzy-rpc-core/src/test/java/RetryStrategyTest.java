import learn.qzy.rpc.fault.retry.FixedIntervalRetryStrategy;
import learn.qzy.rpc.fault.retry.NoRetryStrategy;
import learn.qzy.rpc.fault.retry.RetryStrategy;
import learn.qzy.rpc.model.RpcResponse;
import org.junit.Test;

/**
 * @author qzy
 * @time 2025年1月18日 10:02 星期六
 * @title 重试策略测试
 */
public class RetryStrategyTest {

    RetryStrategy retryStrategy = new FixedIntervalRetryStrategy();
//    RetryStrategy retryStrategy = new NoRetryStrategy();

    @Test
    public void doRetryTest() {
        try {
            RpcResponse rpcResponse = retryStrategy.doRetry(() -> {
                System.out.println("测试重试...");
                throw new RuntimeException("模拟出现异常！");
            });
            System.out.println("rpcResponse = " + rpcResponse);
        } catch (Exception e) {
            System.out.println("重试多次失败！");
            e.printStackTrace();
        }
    }
}
