package learn.qzy.rpc.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.watch.WatchMonitor;
import cn.hutool.core.io.watch.Watcher;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;
import cn.hutool.setting.yaml.YamlUtil;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.WatchEvent;

/**
 * @author qzy
 * @time 2025年1月2日 15:36 星期四
 * @title 配置工具类
 */
public class ConfigUtils {

    /**
     * 加载配置对象
     */
    public static <T> T loadConfig(Class<T> tClass, String prefix) {
        return loadConfig(tClass, prefix, "");
    }

    /**
     * 加载配置对象，支持区分环境
     */
    public static <T> T loadConfig(Class<T> tClass, String prefix, String environment) {
        StringBuilder configFileBuilder = new StringBuilder("application");
        if (StrUtil.isNotBlank(environment)) {
            configFileBuilder.append("-").append(environment);
        }
        // 解析properties文件
        String configFilePath = configFileBuilder + ".properties";
        if (FileUtil.exist(configFilePath)) {
            Props props = new Props(configFilePath, "UTF-8");
            return props.toBean(tClass, prefix);
        }
        // 解析YAML文件
        configFilePath = configFileBuilder + ".yaml";
        if (FileUtil.exist(configFilePath)) {
            Dict dict = YamlUtil.loadByPath(configFilePath);
            return BeanUtil.toBean(dict.get(prefix), tClass);
        }
        throw new RuntimeException("未找到配置文件:" + configFilePath);
    }


    /**
     * 监听配置文件变更并自动更新配置对象
     *
     * @param tClass      配置类
     * @param prefix      配置前缀
     * @param environment 环境
     * @param callback    配置更新后的回调函数
     */
    public static <T> void watchConfig(Class<T> tClass, String prefix, String environment, ConfigUpdateCallback<T> callback) {
        StringBuilder configFileBuilder = new StringBuilder("application");
        if (StrUtil.isNotBlank(environment)) {
            configFileBuilder.append("-").append(environment);
        }
        // 监听 properties 文件
        String propertiesFilePath = configFileBuilder + ".properties";
        if (FileUtil.exist(propertiesFilePath)) {
            watchFile(propertiesFilePath, () -> {
                Props props = new Props(propertiesFilePath, "UTF-8");
                T config = props.toBean(tClass, prefix);
                callback.onUpdate(config);
            });
        }
        // 监听 YAML 文件
        String yamlFilePath = configFileBuilder + ".yaml";
        if (FileUtil.exist(yamlFilePath)) {
            watchFile(yamlFilePath, () -> {
                Dict dict = YamlUtil.loadByPath(yamlFilePath);
                T config = BeanUtil.toBean(dict.get(prefix), tClass);
                callback.onUpdate(config);
            });
        }
    }

    /**
     * 监听文件变更
     *
     * @param filePath 文件路径
     * @param action   文件变更后的操作
     */
    private static void watchFile(String filePath, Runnable action) {
        File file = FileUtil.file(filePath);
        WatchMonitor watchMonitor = WatchMonitor.create(file, WatchMonitor.ENTRY_MODIFY);
        watchMonitor.setWatcher(new Watcher() {
            @Override
            public void onCreate(WatchEvent<?> event, Path currentPath) {
            }

            @Override
            public void onModify(WatchEvent<?> event, Path currentPath) {
                action.run();
            }

            @Override
            public void onDelete(WatchEvent<?> event, Path currentPath) {
            }

            @Override
            public void onOverflow(WatchEvent<?> event, Path currentPath) {
            }
        });
        watchMonitor.start();
    }

    /**
     * 配置更新回调接口
     */
    public interface ConfigUpdateCallback<T> {
        void onUpdate(T config);
    }

}
