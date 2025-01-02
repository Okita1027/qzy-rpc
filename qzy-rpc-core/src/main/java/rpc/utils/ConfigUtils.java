package rpc.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;
import cn.hutool.setting.yaml.YamlUtil;
import rpc.config.RpcConfig;

import java.io.File;
import java.io.FileReader;

import static rpc.constant.RpcConstant.DEFAULT_CONFIG_PREFIX;

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
        Dict dict = YamlUtil.loadByPath(configFilePath);
        return BeanUtil.toBean(dict.get(prefix), tClass);
    }
}
