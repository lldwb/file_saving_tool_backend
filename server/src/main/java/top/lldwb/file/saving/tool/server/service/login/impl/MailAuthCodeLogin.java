package top.lldwb.file.saving.tool.server.service.login.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import top.lldwb.file.saving.tool.server.dao.UserDao;
import top.lldwb.file.saving.tool.server.entity.User;
import top.lldwb.file.saving.tool.server.exception.AuthException;
import top.lldwb.file.saving.tool.server.service.login.LoginService;

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
public class MailAuthCodeLogin implements LoginService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final UserDao userDao;

    @Override
    public User login(User user, String... args) {
        String authCode = (String) redisTemplate.opsForValue().get("verification_code:" + user.getUserEmail());
        if (authCode == null || "".equals(authCode)) {
            throw new AuthException("邮箱或者验证码错误", 10002);
        }
        redisTemplate.delete("verification_code:" + user.getUserEmail());


        return null;
    }
}
