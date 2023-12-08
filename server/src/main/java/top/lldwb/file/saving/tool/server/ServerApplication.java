package top.lldwb.file.saving.tool.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import top.lldwb.file.saving.tool.config.AppConfig;
import top.lldwb.file.saving.tool.config.MinIOConfig;

@SpringBootApplication
@MapperScan("top.lldwb.file.saving.tool.server.dao")
@Import(AppConfig.class)
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }
}
