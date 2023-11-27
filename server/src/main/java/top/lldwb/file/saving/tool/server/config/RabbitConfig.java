package top.lldwb.file.saving.tool.server.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    /**
     * 交换机
     */
    private static final String EXCHANGE_NAME = "file_saving_tool_backend";

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
