package top.lldwb.file.saving.tool.server.service.authCode.impl;

import cn.hutool.core.util.RandomUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import top.lldwb.file.saving.tool.server.config.RabbitConfig;
import top.lldwb.file.saving.tool.server.config.RabbitEmailAuthCode;
import top.lldwb.file.saving.tool.server.dto.AuthCode;
import top.lldwb.file.saving.tool.server.dto.Message;
import top.lldwb.file.saving.tool.server.service.authCode.AuthCodeService;
import top.lldwb.file.saving.tool.server.service.send.SendService;

import java.time.Duration;

/**
 * 邮箱验证码
 *
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/11/27
 * @time 19:04
 * @PROJECT_NAME file_saving_tool_backend
 */
@Service
@RequiredArgsConstructor
public class EmailAuthCode implements AuthCodeService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final RabbitTemplate template;

    @Override
    public void sendAuthCode(AuthCode authCode) {
        Message message = new Message();
        message.setReceivingUser(authCode.getReceivingUser());
        message.setFromUser(authCode.getFromUser());
        message.setSubject("邮箱"+authCode.getSubject());
        message.setContent("验证码：" + authCode.getAuthCode());

        template.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitEmailAuthCode.ROUTING_KEY, message);

        redisTemplate.opsForValue().set("verification_code:" + authCode.getReceivingUser(), authCode.getAuthCode(), Duration.ofSeconds(300));
    }
}
