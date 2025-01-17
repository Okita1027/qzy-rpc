import learn.qzy.rpc.loadbalancer.ConsistentHashLoadBalancer;
import learn.qzy.rpc.loadbalancer.LoadBalancer;
import learn.qzy.rpc.loadbalancer.RandomLoadBalancer;
import learn.qzy.rpc.model.ServiceMetaInfo;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * @author qzy
 * @time 2025年1月17日 16:22 星期五
 * @title
 */
public class LoadBalanceTest {
    @Test
    public void test01() {
        TreeMap<Float, String> map = new TreeMap<>();
        map.put(1f, "a");
        map.put(2f, "b");
        map.put(2.1f, "bb");
        map.put(3f, "c");
        Map.Entry<Float, String> entry = map.ceilingEntry(2.05f);
        System.out.println(entry);
        entry = map.firstEntry();
        System.out.println(entry);
    }


    final LoadBalancer loadBalancer = new RandomLoadBalancer();
    @Test
    public void testSelect() {
        // 请求参数
        Map<String, Object> requestParams = new HashMap<>();
        requestParams.put("methodName", "apple");
        // 服务列表
        ServiceMetaInfo serviceMetaInfo1 = new ServiceMetaInfo();
        serviceMetaInfo1.setServiceName("myService");
        serviceMetaInfo1.setServiceVersion("1.0");
        serviceMetaInfo1.setServiceHost("localhost");
        serviceMetaInfo1.setServicePort(1234);
        ServiceMetaInfo serviceMetaInfo2 = new ServiceMetaInfo();
        serviceMetaInfo2.setServiceName("myService");
        serviceMetaInfo2.setServiceVersion("1.1");
        serviceMetaInfo2.setServiceHost("learn.qzy");
        serviceMetaInfo2.setServicePort(1235);
        List<ServiceMetaInfo> serviceMetaInfoList = Arrays.asList(serviceMetaInfo1, serviceMetaInfo2);
        // 连续调用3次
        ServiceMetaInfo serviceMetaInfo = loadBalancer.select(requestParams, serviceMetaInfoList);
        System.out.println("serviceMetaInfo = " + serviceMetaInfo);
        Assert.assertNotNull(serviceMetaInfo);
        serviceMetaInfo = loadBalancer.select(requestParams, serviceMetaInfoList);
        System.out.println("serviceMetaInfo = " + serviceMetaInfo);
        Assert.assertNotNull(serviceMetaInfo);
        serviceMetaInfo = loadBalancer.select(requestParams, serviceMetaInfoList);
        System.out.println("serviceMetaInfo = " + serviceMetaInfo);
        Assert.assertNotNull(serviceMetaInfo);
    }
}
