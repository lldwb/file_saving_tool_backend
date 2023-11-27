package top.lldwb.file.saving.tool.server.service.send.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.lldwb.file.saving.tool.server.dto.Message;
import top.lldwb.file.saving.tool.server.service.send.SendService;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/11/27
 * @time 15:36
 * @PROJECT_NAME file_saving_tool_backend
 */
@SpringBootTest
class EmailSendTest {
    @Autowired
    SendService sendEmail;

    @Test
    void send() {
        sendEmail.send(new Message("file_saving_tool@lldwb.top","3247187440@qq.com", "测试邮件头","测试邮箱体",new Date()));
    }
}