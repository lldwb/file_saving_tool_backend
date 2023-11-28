package top.lldwb.file.saving.tool.server.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import top.lldwb.file.saving.tool.server.config.RabbitEmailAuthCode;
import top.lldwb.file.saving.tool.server.dto.Message;
import top.lldwb.file.saving.tool.server.service.send.SendService;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ConsumerAuthCode {
    private final SendService sendEmail;
    @RabbitListener(queues = RabbitEmailAuthCode.QUEUE_NAME)
    public void sendEmailAuthCode(Message message){
        sendEmail.send(message);
    }
}
