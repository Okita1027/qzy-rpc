package learn.qzy.examplespringbootconsumer;

import learn.qzy.example.common.model.User;
import learn.qzy.example.common.service.UserService;
import learn.qzy.rpc.springboot.starter.annotation.RpcReference;
import learn.qzy.rpc.springboot.starter.annotation.RpcService;
import org.springframework.stereotype.Service;

/**
 * @author qzy
 * @time 2025年1月25日 18:34 星期六
 * @title
 */
@Service
public class ExampleServiceImpl {
    @RpcReference
    private UserService userService;

    public void test() {
        User user = new User();
        user.setName("qzy");
        User resultUser = userService.getuser(user);
        System.out.println("resultUser = " + resultUser.getName());
    }
}
