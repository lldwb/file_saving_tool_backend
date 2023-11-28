package top.lldwb.file.saving.tool.server.service.login.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.lldwb.file.saving.tool.server.entity.User;

/**
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/11/27
 * @time 20:19
 * @PROJECT_NAME file_saving_tool_backend
 */
@SpringBootTest
class MailAuthCodeLoginTest {
    @Autowired
    private MailAuthCodeLogin mailAuthCodeLogin;
    @Test
    void login() {
        User user = new User();
        user.setUserEmail("3247187440@qq.com");
        mailAuthCodeLogin.login(user,"734032");
    }
}