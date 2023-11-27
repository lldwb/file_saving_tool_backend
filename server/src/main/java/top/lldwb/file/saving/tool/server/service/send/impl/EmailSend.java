package top.lldwb.file.saving.tool.server.service.send.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import top.lldwb.file.saving.tool.server.dto.Message;
import top.lldwb.file.saving.tool.server.service.send.SendService;

/**
 * 发送消息到邮箱
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/11/27
 * @time 15:00
 * @PROJECT_NAME file_saving_tool_backend
 */
@Service
@RequiredArgsConstructor
public class EmailSend implements SendService {
    private final JavaMailSender javaMailSender;

    @Override
    public void send(Message message) {
        SimpleMailMessage smm = new SimpleMailMessage();
        // 发件人的邮箱
        smm.setFrom(message.getFromUser());
        // 要发给的邮箱(收件人)
        smm.setTo(message.getReceivingUser());
        // 主题
        smm.setSubject(message.getSubject());
        // 邮件内容
        smm.setText(message.getContent());
        // 发送邮件
        javaMailSender.send(smm);
    }
}
