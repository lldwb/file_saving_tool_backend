package top.lldwb.file.saving.tool.server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lldwb.file.saving.tool.server.controller.vo.ResultVO;
import top.lldwb.file.saving.tool.server.dto.AuthCode;
import top.lldwb.file.saving.tool.server.entity.User;
import top.lldwb.file.saving.tool.server.exception.AuthException;
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
public class LoginController extends BaseController {
    private final LoginService emailAuthCodeLogin;
    private final LoginService passwordLogin;
    private final AuthCodeService emailAuthCode;

    @PostMapping("/sendEmailAuthCode")
    public ResultVO sendEmailAuthCode(User user) {
        if (user == null || user.getUserEmail() == null || "".equals(user.getUserEmail())) {
            throw new AuthException("邮箱不能为空", 10001);
        }
        AuthCode code = new AuthCode();
        code.setReceivingUser(user.getUserEmail());
        code.setFromUser("file_saving_tool@lldwb.top");
        emailAuthCode.sendAuthCode(code);
        return success();
    }

    @PostMapping("/emailAuthCode")
    public ResultVO emailAuthCode(User user, String authCode) {
        if (user == null || user.getUserEmail() == null || "".equals(user.getUserEmail()) || authCode == null || "".equals(authCode)) {
            throw new AuthException("用户名或验证码不能为空", 10001);
        }
        return success(emailAuthCodeLogin.login(user, authCode));
    }

    @PostMapping("/password")
    public ResultVO password(User user) {
        if (user == null) {
            throw new AuthException("用户名或密码不能为空", 10001);
        }
        return success(passwordLogin.login(user));
    }
}
