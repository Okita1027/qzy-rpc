package learn.qzy.rpc.spi;

import cn.hutool.core.io.resource.ResourceUtil;
import learn.qzy.rpc.serializer.Serializer;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author qzy
 * @time 2025年1月03日 19:26 星期五
 * @title SPI加载器（支持键值对映射）
 * 用 Map 来存储已加载的配置信息 键名 => 实现类。
 * 扫描指定路径，读取每个配置文件，获取到 键名 => 实现类 信息并存储在 Map 中。
 * 定义获取实例方法，根据用户传入的接口和键名，从 Map 中找到对应的实现类，然后通过反射获取到实现类对象。可以维护一个对象实例缓存，创建过一次的对象从缓存中读取即可。
 */
@Slf4j
public class SpiLoader {

    /**
     * 存储已加载的类：接口名 => (key => 实现类)
     */
    private static final Map<String, Map<String, Class<?>>> loaderMap = new ConcurrentHashMap<>();

    /**
     * 对象实例缓存（避免重复new），类路径 => 对象实例，单例模式
     */
    private static final Map<String, Object> instanceCache = new ConcurrentHashMap<>();

    /**
     * 系统 SPI 目录
     */
    private static final String RPC_SYSTEM_SPI_DIR = "META-INF/rpc/system/";

    /**
     * 用户自定义 SPI 目录
     */
    private static final String RPC_CUSTOM_SPI_DIR = "META-INF/rpc/custom/";

    /**
     * 扫描路径
     */
    public static final String[] SCAN_DIRS = new String[]{RPC_SYSTEM_SPI_DIR, RPC_CUSTOM_SPI_DIR};

    /**
     * 动态加载类列表
     */
    public static final List<Class<?>> LOAD_CLASS_LIST = List.of(Serializer.class);

    /**
     * 加载所有类型
     */
    public static void loadAll() {
        log.info("加载所有 SPI ");
        for (Class<?> clazz : LOAD_CLASS_LIST) {
            load(clazz);
        }
    }

    /**
     * 加载某个类型
     */
    public static void load(Class<?> loadClass) {
        log.info("加载类型为 {} 的 SPI", loadClass.getName());
        // 扫描路径，用户自定义的 SPI 优先级高于系统 SPI
        Map<String, Class<?>> keyClassMap = new HashMap<>();
        for (String scanDir : SCAN_DIRS) {
            List<URL> resources = ResourceUtil.getResources(scanDir + loadClass.getName());
            // 读取每个资源
            for (URL resource : resources) {
                try (InputStreamReader inputStreamReader = new InputStreamReader(resource.openStream());
                     BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        String[] strArray = line.split("=");
                        if (strArray.length > 1) {
                            String key = strArray[0];
                            String className = strArray[1];
                            keyClassMap.put(key, Class.forName(className));
                        }
                    }
                } catch (IOException | ClassNotFoundException e) {
                    log.error("SPI resource load error", e);
                }
            }
        }
        loaderMap.put(loadClass.getName(), keyClassMap);
    }

    /**
     * 获取某个接口的实例
     */
    public static <T> T getInstance(Class<T> tClass, String key) {
        // 懒加载序列化器
        if (!loaderMap.containsKey(tClass.getName())) {
            synchronized (loaderMap) {
                if (!loaderMap.containsKey(tClass.getName())) {
                    load(Serializer.class);
                }
            }
        }

        String tClassName = tClass.getName();
        Map<String, Class<?>> keyClassMap = loaderMap.get(tClassName);
        if (keyClassMap == null) {
            throw new RuntimeException(String.format("SpiLoader 未加载 %s 类型", tClassName));
        }
        if (!keyClassMap.containsKey(key)) {
            throw new RuntimeException(String.format("SpiLoader 的 %s 不存在 key=%s 的类型", tClassName, key));
        }
        // 获取要加载的实现类型
        Class<?> implClass = keyClassMap.get(key);
        // 从实例缓存中加载指定类型的实例
        String implClassName = implClass.getName();
        if (!instanceCache.containsKey(implClassName)) {
            try {
                instanceCache.put(implClassName, implClass.getConstructor().newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                String errorMsg = String.format("%s 类实例化失败", implClassName);
                throw new RuntimeException(errorMsg, e);
            } catch (InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
        return (T) instanceCache.get(implClassName);
    }
}
