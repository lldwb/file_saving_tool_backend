package top.lldwb.file.saving.tool.server.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lldwb.file.saving.tool.pojo.dto.AuthCode;
import top.lldwb.file.saving.tool.pojo.entity.User;
import top.lldwb.file.saving.tool.server.config.RedisConfig;
import top.lldwb.file.saving.tool.server.controller.common.BaseResponse;
import top.lldwb.file.saving.tool.server.controller.exception.exception.AuthException;
import top.lldwb.file.saving.tool.server.pojo.vo.ResultVO;
import top.lldwb.file.saving.tool.server.service.authCode.AuthCodeService;
import top.lldwb.file.saving.tool.server.service.login.LoginService;

/**
 * 登录验证中心
 *
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/11/28
 * @time 16:19
 * @PROJECT_NAME file_saving_tool_backend
 */
@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController extends BaseResponse {
    private final LoginService emailAuthCodeLogin;
    private final LoginService passwordLogin;
    private final AuthCodeService emailAuthCode;
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 发送邮箱验证码
     *
     * @param user 用户对象
     * @return 成功响应
     */
    @PostMapping("/sendEmailAuthCode")
    public ResultVO sendEmailAuthCode(User user) {
        if (user == null || user.getUserEmail() == null || "".equals(user.getUserEmail())) {
            throw new AuthException("邮箱不能为空", 10001);
        }
        // 防止重复发送验证码
        else if (redisTemplate.opsForValue().get(RedisConfig.REDIS_INDEX + "verification_code:" + user.getUserEmail()) != null) {
            throw new AuthException("不能重复发送验证码", 10001);
        }
        AuthCode code = new AuthCode();
        code.setReceivingUser(user.getUserEmail());
        code.setFromUser("file_saving_tool@lldwb.top");
        emailAuthCode.sendAuthCode(code);
        return success();
    }

    /**
     * 邮箱验证码登录
     *
     * @param user     用户对象
     * @param authCode 验证码
     * @return 成功响应(用户数据)
     */
    @PostMapping("/emailAuthCode")
    public ResultVO emailAuthCode(User user, String authCode, HttpSession session) {
        if (user == null || user.getUserEmail() == null || "".equals(user.getUserEmail()) || authCode == null || "".equals(authCode)) {
            throw new AuthException("邮箱或验证码不能为空", 10001);
        }
        user = emailAuthCodeLogin.login(user, authCode);
        session.setAttribute("user", user);
        return success(user);
    }

    /**
     * 密码登录
     *
     * @param user 用户对象
     * @return 成功响应(用户数据)
     */
    @PostMapping("/password")
    public ResultVO password(User user, HttpSession session) {
        if (user == null) {
            throw new AuthException("用户名或密码不能为空", 10001);
        }
        user = passwordLogin.login(user);
        session.setAttribute("user", user);
        return success(user);
    }
}
