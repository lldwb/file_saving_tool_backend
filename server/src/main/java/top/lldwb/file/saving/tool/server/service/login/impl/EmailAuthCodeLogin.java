package top.lldwb.file.saving.tool.server.service.login.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import top.lldwb.file.saving.tool.server.config.RedisConfig;
import top.lldwb.file.saving.tool.server.dao.UserDao;
import top.lldwb.file.saving.tool.pojo.entity.User;
import top.lldwb.file.saving.tool.server.exception.AuthException;
import top.lldwb.file.saving.tool.server.service.login.LoginService;
import top.lldwb.file.saving.tool.server.service.entity.UserService;

/**
 * 邮箱验证码登录
 *
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/11/27
 * @time 11:43
 * @PROJECT_NAME file_saving_tool_backend
 */
@Service
@RequiredArgsConstructor
public class EmailAuthCodeLogin implements LoginService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final UserDao userDao;
    private final UserService userService;

    @Override
    public User login(User user, String... args) {
        String authCode = (String) redisTemplate.opsForValue().get(RedisConfig.REDIS_INDEX + "verification_code:" + user.getUserEmail());
        if (authCode == null || "".equals(authCode) || authCode.equals(args[0])) {
            throw new AuthException("邮箱或者验证码错误", 10002);
        }
        User userLogin = userDao.getUserByMail(user.getUserEmail());
        // 判断是否注册，如果没有则自动注册
        if (userLogin == null) {
            user.setUserName(user.getUserEmail());
            userService.addUser(user);
            userLogin = userDao.getUserByMail(user.getUserEmail());
        }

        redisTemplate.delete(RedisConfig.REDIS_INDEX + "verification_code:" + user.getUserEmail());
        return userLogin;
    }
}
