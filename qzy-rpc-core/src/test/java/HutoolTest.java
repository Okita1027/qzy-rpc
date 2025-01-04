import cn.hutool.core.io.resource.ResourceUtil;
import org.junit.Test;

import java.net.URL;
import java.util.List;

/**
 * @author qzy
 * @time 2025年1月04日 17:29 星期六
 * @title
 */
public class HutoolTest {
    @Test
    public void ResourceUtilTest() {
        List<URL> resources = ResourceUtil.getResources("META-INF/services/learn.qzy.rpc.serializer.Serializer");
        System.out.println("resources = " + resources);

        resources = ResourceUtil.getResources("META-INF/rpc/system/learn.qzy.rpc.serializer.Serializer");
        System.out.println("resources = " + resources);
    }
}
