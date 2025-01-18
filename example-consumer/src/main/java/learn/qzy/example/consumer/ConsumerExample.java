package learn.qzy.example.consumer;

import learn.qzy.example.common.model.User;
import learn.qzy.example.common.service.UserService;
import learn.qzy.rpc.config.RpcConfig;
import learn.qzy.rpc.proxy.ServiceProxyFactory;
import learn.qzy.rpc.utils.ConfigUtils;

/**
 * @author qzy
 * @time 2025年1月02日 16:25 星期四
 * @title 简易服务消费者示例
 */
public class ConsumerExample {
    public static void main(String[] args) {
        // 获取UserService的实现类对象
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("qzy");
        // 调用
        User newUser = userService.getuser(user);
        newUser = userService.getuser(user);
        newUser = userService.getuser(user);
        if (newUser != null) {
            System.out.println("用户名：" + newUser.getName());
        } else {
            System.out.println("用户名不存在!");
        }

        // mock测试
//        short number = userService.getNumber();
//        System.out.println("number = " + number);

//        RpcConfig rpc = ConfigUtils.loadConfig(RpcConfig.class, "learn/qzy/rpc");
//        System.out.println("rpc = " + rpc);
    }
}
