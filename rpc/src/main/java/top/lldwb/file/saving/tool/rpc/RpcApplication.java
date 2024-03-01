package top.lldwb.file.saving.tool.rpc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import top.lldwb.file.saving.tool.rpc.server.ServerNettyService;


/**
 * 远程过程调用启动类
 */
@SpringBootApplication
public class RpcApplication {

    public static void main(String[] args) {
        ApplicationContext connection = SpringApplication.run(RpcApplication.class, args);
        ServerNettyService serverNettyService = connection.getBean(ServerNettyService.class);
        serverNettyService.setPort(32433);
        serverNettyService.run();
    }

}
