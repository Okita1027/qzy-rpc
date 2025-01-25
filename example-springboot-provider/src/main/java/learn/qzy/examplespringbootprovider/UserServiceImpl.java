package learn.qzy.examplespringbootprovider;

import learn.qzy.example.common.model.User;
import learn.qzy.example.common.service.UserService;
import learn.qzy.rpc.springboot.starter.annotation.RpcService;
import org.springframework.stereotype.Service;

/**
 * @author qzy
 * @time 2025年1月25日 16:57 星期六
 * @title 用户服务实现类
 */
@Service
@RpcService
public class UserServiceImpl implements UserService {
    @Override
    public User getuser(User user) {
        System.out.println("用户名：" + user.getName());
        return user;
    }
}
