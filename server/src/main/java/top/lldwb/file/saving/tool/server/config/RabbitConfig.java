package top.lldwb.file.saving.tool.server.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 装配mq
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/11/27
 * @time 19:06
 * @PROJECT_NAME file_saving_tool_backend
 */
@Configuration
public class RabbitConfig {
    /**
     * 交换机
     */
    public static final String EXCHANGE_NAME = "file.saving.tool";
    /**
     * 装配 Direct 类型的交换机 durable：是否持久化交换机(false不持久化) autoDelete：是否自动删除(true自动删除，关闭项目时自动删除)
     */
    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }
    /**
     * 下面配置对对象的支持 消息转换器，将对象转换为 JSON
     */
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
