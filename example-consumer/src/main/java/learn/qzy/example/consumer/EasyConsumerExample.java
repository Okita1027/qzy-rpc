package learn.qzy.example.consumer;

import learn.qzy.example.common.model.User;
import learn.qzy.example.common.service.UserService;
import rpc.proxy.ServiceProxyFactory;

/**
 * @author qzy
 * @time 2025年1月01日 17:45 星期三
 * @title
 */
public class EasyConsumerExample {
    public static void main(String[] args) {
        // 获取UserService的实现类对象
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("qzy");
        // 调用
        User newUser = userService.getuser(user);
        if (newUser != null) {
            System.out.println("用户名：" + newUser.getName());
        } else {
            System.out.println("用户名不存在!");
        }
    }
}
