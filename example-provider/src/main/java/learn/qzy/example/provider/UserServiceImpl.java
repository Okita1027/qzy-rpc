package learn.qzy.example.provider;

import learn.qzy.example.common.model.User;
import learn.qzy.example.common.service.UserService;

/**
 * @author qzy
 * @time 2025年01月01日 17:36 星期三
 * @title 用户服务实现类
 */
public class UserServiceImpl implements UserService {
    @Override
    public User getuser(User user) {
        System.out.println("用户名：" + user.getName());
        return user;
    }
}
