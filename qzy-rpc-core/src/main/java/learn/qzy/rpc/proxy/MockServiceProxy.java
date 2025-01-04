package learn.qzy.rpc.proxy;

import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Locale;

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
    private Object getDefaultObject(Class<?> type) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Faker faker = new Faker(new Locale("zh-CN"));
        // 基本类型
        if (type.isPrimitive()) {
            if (type == int.class) {
                return faker.random().nextInt();
            } else if (type == byte.class) {
                return (byte) faker.random().nextInt(Byte.MAX_VALUE);
            } else if (type == short.class) {
                return (short) faker.random().nextInt(Short.MAX_VALUE);
            } else if (type == long.class) {
                return faker.random().nextLong();
            } else if (type == float.class) {
                return faker.random().nextFloat();
            } else if (type == double.class) {
                return faker.random().nextDouble();
            } else if (type == boolean.class) {
                return faker.random().nextBoolean();
            }
        } else if (type == LocalDateTime.class) {
            return LocalDateTime.now();
        }
        // 其它类型
        return null;
    }

}
