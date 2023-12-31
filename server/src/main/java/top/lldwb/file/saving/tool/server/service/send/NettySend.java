package top.lldwb.file.saving.tool.server.service.send;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import top.lldwb.file.saving.tool.pojo.dto.SocketMessage;
import top.lldwb.file.saving.tool.server.service.netty.ServerHandler;
import top.lldwb.file.saving.tool.service.send.SendService;

/**
 * 给客户端发送消息的实现类
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/8
 * @time 9:45
 * @PROJECT_NAME file_saving_tool_backend
 */
@Slf4j
@Service
@Setter
//@Scope(value = "prototype",proxyMode = ScopedProxyMode.TARGET_CLASS)
@RequiredArgsConstructor
public class NettySend implements SendService<SocketMessage> {

    @Override
    public void send(SocketMessage message) {
        log.info("发送消息：{}",message.toString());
        // 发送消息并且立刻刷新(设置消息并且立刻发送)
        // 添加关闭监听器
        ServerHandler.getChannelHandlerContext(message.getSecretKey()).writeAndFlush(message);
    }
}
