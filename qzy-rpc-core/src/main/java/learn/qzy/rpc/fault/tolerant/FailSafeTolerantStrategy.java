package learn.qzy.rpc.fault.tolerant;

import learn.qzy.rpc.model.RpcResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author qzy
 * @time 2025年1月18日 17:02 星期六
 * @title 容错策略——静默处理异常
 */
@Slf4j
public class FailSafeTolerantStrategy implements TolerantStrategy{
    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        log.info("静默处理异常", e);
        return new RpcResponse();
    }
}
