package top.lldwb.file.saving.tool.server.consumer;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.lldwb.file.saving.tool.pojo.dto.AuthCode;
import top.lldwb.file.saving.tool.server.config.RabbitConfig;
import top.lldwb.file.saving.tool.server.config.RabbitEmailAuthCode;

/**
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/11/28
 * @time 8:52
 * @PROJECT_NAME file_saving_tool_backend
 */
@SpringBootTest
class ConsumerAuthCodeTest {
    @Autowired
    private RabbitTemplate template;
    @Test
    void sendEmailAuthCode() throws InterruptedException {
        AuthCode authCode = new AuthCode();
        authCode.setFromUser("file_saving_tool@lldwb.top");
        authCode.setReceivingUser("3247187440@qq.com");

//        Message message = new Message();
//        message.setReceivingUser(authCode.getReceivingUser());
//        message.setFromUser(authCode.getFromUser());
//        message.setSubject("邮箱"+authCode.getSubject());
//        message.setContent("验证码：" + authCode.getAuthCode());
        template.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitEmailAuthCode.ROUTING_KEY, authCode);

        new Thread().sleep(15);
    }
}