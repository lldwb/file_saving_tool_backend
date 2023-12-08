package top.lldwb.file.saving.tool.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import top.lldwb.file.saving.tool.config.AppConfig;
import top.lldwb.file.saving.tool.config.MinIOConfig;

@SpringBootApplication
@Import(AppConfig.class)
public class ClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

}
