package rpc.proxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author qzy
 * @time 2025年1月03日 10:25 星期五
 * @title MOCK服务代理（JDK动态代理）
 */
@Slf4j
public class MockServiceProxy implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 根据方法的返回值类型，生成特定的默认值对象
        Class<?> returnType = method.getReturnType();
        log.info("Mock invoke {}", method.getName());
        return getDefaultObject(returnType);
    }

    /**
     * 生成指定类型的默认值对象
     */
    private Object getDefaultObject(Class<?> type) {
        // 基本类型
        if (type.isPrimitive()) {
            if (type == int.class) {
                return 1;
            } else if (type == short.class) {
                return (short) 2;
            } else if (type == long.class) {
                return 3L;
            } else if (type == float.class) {
                return 4.1f;
            } else if (type == double.class) {
                return 5.2d;
            } else if (type == boolean.class) {
                return false;
            }
        }
        // 对象类型
        return null;
    }
}
