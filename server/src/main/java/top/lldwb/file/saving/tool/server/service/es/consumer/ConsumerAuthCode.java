package top.lldwb.file.saving.tool.server.service.es.consumer;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import top.lldwb.file.saving.tool.pojo.dto.AuthCode;
import top.lldwb.file.saving.tool.server.config.RabbitEmailAuthCode;
import top.lldwb.file.saving.tool.server.service.send.SendService;

@Service
@RequiredArgsConstructor
public class ConsumerAuthCode {
    private final SendService emailSend;
    @RabbitListener(queues = RabbitEmailAuthCode.QUEUE_NAME)
    public void sendEmailAuthCode(AuthCode authCode){
        emailSend.send(authCode);
    }
}
