package learn.qzy.rpc.proxy;

import java.lang.reflect.Proxy;

/**
 * @author qzy
 * @time 2025年1月01日 21:21 星期三
 * @title 服务代理工厂（用于创建代理对象）
 */
public class ServiceProxyFactory {
    /**
     * 根据服务类获取代理对象
     */
    public static <T> T getProxy(Class<T> serviceClass) {
        return (T) Proxy.newProxyInstance(
                serviceClass.getClassLoader(),
                new Class[]{serviceClass},
                new ServiceProxy()
        );
    }
}
