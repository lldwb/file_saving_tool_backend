package top.lldwb.file.saving.tool.server.service.login.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import top.lldwb.file.saving.tool.client.entity.User;
import top.lldwb.file.saving.tool.server.service.login.LoginService;

/**
 * 密码登录
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/11/27
 * @time 11:43
 * @PROJECT_NAME file_saving_tool_backend
 */
@Service
@RequiredArgsConstructor
public class PasswordLogin implements LoginService {
//    private final UserDao userDao;

    @Override
    public User login(User user, String... args) {
//        User users = userDao.getUserByName(user.getUserName());
//        if (users == null || !users.getUserPassword().equals(SecureUtil.md5(user.getUserPassword()))) {
//            throw new AuthException("账号或者密码错误", 10001);
//        }
//        return users;

        return null;
    }
}
