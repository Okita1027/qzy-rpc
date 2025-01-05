package learn.qzy.example.consumer;

import learn.qzy.example.common.model.User;
import learn.qzy.example.common.service.UserService;
import learn.qzy.rpc.proxy.ServiceProxyFactory;

/**
 * @author qzy
 * @time 2025年1月01日 17:45 星期三
 * @title 服务消费者主启动类
 */
public class EasyConsumerExample {
    public static void main(String[] args) {
        // 动态代理获取服务对象
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("qzy");
        // 调用
        User newUser = userService.getuser(user);
        if (newUser != null) {
            System.out.println(newUser.getName());
        } else {
            System.out.println("user == null");
        }
    }
}
