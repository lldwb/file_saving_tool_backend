package top.lldwb.file.saving.tool.server.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 负责更新的消息队列
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/1
 * @time 10:42
 * @PROJECT_NAME file_saving_tool_backend
 */
@Configuration
public class RabbitUpdate {

    /**
     * 队列名称
     */
    public static final String QUEUE_NAME = "update";
    /**
     * 路由的键
     */
    public static final String ROUTING_KEY = "update";

    /**
     * 装配队列
     */
    @Bean
    public Queue queueUpdate() {
        return new Queue(QUEUE_NAME, false);
    }

    /**
     * 将队列绑定到交换机上并指定一个路由的 key
     */
    @Bean
    public Binding bindinUpdateg(DirectExchange exchange) {
        return BindingBuilder.bind(queueUpdate()).to(exchange).with(ROUTING_KEY);
    }
}
