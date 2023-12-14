package top.lldwb.file.saving.tool.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import top.lldwb.file.saving.tool.client.netty.ClientHandler;
import top.lldwb.file.saving.tool.client.netty.ClientNettyService;
import top.lldwb.file.saving.tool.pojo.dto.SocketMessage;

/**
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/7
 * @time 9:10
 * @PROJECT_NAME file_saving_tool_backend
 */
//@Lazy
@Configuration
public class NettyConfig {
    /**
     * 启动Netty客户端
     * @return
     */
//    @Bean
//    public ClientNettyService clientNettyService(ClientNettyService clientNettyService) throws InterruptedException {
//        clientNettyService.setHost("127.0.0.1");
//        clientNettyService.setPort(32433);
//        clientNettyService.run();
//        return clientNettyService;
//    }
}
