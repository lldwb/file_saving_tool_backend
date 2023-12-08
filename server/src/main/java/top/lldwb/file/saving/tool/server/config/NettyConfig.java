package top.lldwb.file.saving.tool.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.lldwb.file.saving.tool.pojo.dto.SocketMessage;
import top.lldwb.file.saving.tool.server.service.netty.ServerNettyService;

/**
 * Netty配置类
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/6
 * @time 15:54
 * @PROJECT_NAME file_saving_tool_backend
 */
@Configuration
public class NettyConfig {
    /**
     * 启动Netty服务端
     * @return
     */
    @Bean
    public ServerNettyService serverNettyService(ServerNettyService serverNettyService){
        serverNettyService.setPort(32433);
        serverNettyService.run();
        return serverNettyService;
    }
}
