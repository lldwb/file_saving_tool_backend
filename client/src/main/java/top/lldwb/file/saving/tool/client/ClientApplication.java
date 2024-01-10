package top.lldwb.file.saving.tool.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import top.lldwb.file.saving.tool.PublicApplication;
import top.lldwb.file.saving.tool.client.netty.ClientNettyService;

@SpringBootApplication
@Import(PublicApplication.class)
public class ClientApplication {

    public static void main(String[] args) {
        ApplicationContext connection = SpringApplication.run(ClientApplication.class, args);
        ClientNettyService clientNettyService = connection.getBean(ClientNettyService.class);
//        clientNettyService.setHost("127.0.0.1");
        // 远程服务器
        clientNettyService.setHost("www");
        clientNettyService.setPort(32433);
        clientNettyService.run();
    }

}
