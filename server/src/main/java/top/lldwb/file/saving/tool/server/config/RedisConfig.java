package top.lldwb.file.saving.tool.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/11/27
 * @time 19:08
 * @PROJECT_NAME file_saving_tool_backend
 */
@Configuration
public class RedisConfig {
    public static final String REDIS_INDEX ="file_saving_tool_backend.";
    public static final String ES_INDEX ="file_saving_tool_backend_";
    /**
     * 配置redis的序列化器
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        // 设置key的序列化方式
        template.setKeySerializer(RedisSerializer.string());
        // 设置hash的key的序列化方式
        template.setHashKeySerializer(RedisSerializer.string());
        // 设置value的序列化方式
        template.setValueSerializer(RedisSerializer.json());
        // 设置hash的value的序列化方式
        template.setHashValueSerializer(RedisSerializer.json());
        return template;
    }
}