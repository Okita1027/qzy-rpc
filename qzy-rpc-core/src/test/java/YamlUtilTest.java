import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.setting.dialect.Props;
import cn.hutool.setting.yaml.YamlUtil;
import org.junit.Test;
import learn.qzy.rpc.config.RpcConfig;

/**
 * @author qzy
 * @time 2025年1月02日 17:00 星期四
 * @title
 */
public class YamlUtilTest {
    @Test
    public void testYaml() {
        String yamlFile = "application.yaml";
        // 解析YAML
        Dict dict = YamlUtil.loadByPath(yamlFile);
        RpcConfig rpcConfig = BeanUtil.toBean(dict.get("learn/qzy/rpc"), RpcConfig.class);
        System.out.println("rpcConfig = " + rpcConfig);
    }

    @Test
    public void testProps() {
        Props props = new Props("application.properties");
        RpcConfig rpcConfig = props.toBean(RpcConfig.class);
        System.out.println("rpcConfig = " + rpcConfig);
    }
}
