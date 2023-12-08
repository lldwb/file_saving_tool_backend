package top.lldwb.file.saving.tool.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import top.lldwb.file.saving.tool.pojo.dto.SocketMessage;
import top.lldwb.file.saving.tool.service.netty.ObjectDecoder;
import top.lldwb.file.saving.tool.service.netty.ObjectEncoder;

/**
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/7
 * @time 11:32
 * @PROJECT_NAME file_saving_tool_backend
 */
@Configuration
@Import({MinIOConfig.class})
public class AppConfig {
    @Bean
    public ObjectDecoder objectDecoder(){
        ObjectDecoder objectDecoder = new ObjectDecoder(SocketMessage.class);
        return objectDecoder;
    }

    @Bean
    public ObjectEncoder objectEncoder(){
        return new ObjectEncoder();
    }
}
