package top.lldwb.file.saving.tool.server.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 邮箱验证码队列
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/11/27
 * @time 20:02
 * @PROJECT_NAME file_saving_tool_backend
 */
@Configuration
public class RabbitEmailAuthCode {

    /**
     * 队列名称
     */
    public static final String QUEUE_NAME = "email.auth.code";
    /**
     * 路由的键
     */
    public static final String ROUTING_KEY = "email.code";

    /**
     * 装配队列
     */
    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, false);
    }

    /**
     * 将队列绑定到交换机上并指定一个路由的 key
     */
    @Bean
    public Binding binding(DirectExchange exchange) {
        return BindingBuilder.bind(queue()).to(exchange).with(ROUTING_KEY);
    }
}