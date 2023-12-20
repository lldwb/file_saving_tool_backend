package top.lldwb.file.saving.tool.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import top.lldwb.file.saving.tool.PublicApplication;
import top.lldwb.file.saving.tool.server.service.netty.ServerNettyService;

@SpringBootApplication
@MapperScan("top.lldwb.file.saving.tool.server.dao")
@Import(PublicApplication.class)
public class ServerApplication {

    public static void main(String[] args) {
        ApplicationContext connection = SpringApplication.run(ServerApplication.class, args);
        ServerNettyService serverNettyService = connection.getBean(ServerNettyService.class);
        serverNettyService.setPort(32433);
        serverNettyService.run();
    }
}
