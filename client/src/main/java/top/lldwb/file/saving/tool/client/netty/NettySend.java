package top.lldwb.file.saving.tool.client.netty;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import top.lldwb.file.saving.tool.pojo.dto.SocketMessage;
import top.lldwb.file.saving.tool.service.send.SendService;

/**
 * 给客户端发送消息的实现类
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/8
 * @time 9:45
 * @PROJECT_NAME file_saving_tool_backend
 */
@Service
@Setter
//@Scope(value = "prototype",proxyMode = ScopedProxyMode.TARGET_CLASS)
@RequiredArgsConstructor
public class NettySend implements SendService<SocketMessage> {
    private final ClientHandler clientHandler;

    @Override
    public void send(SocketMessage message) {

        // 发送消息并且立刻刷新(设置消息并且立刻发送)
        // 添加关闭监听器
        ClientHandler.ctx.writeAndFlush(message);
    }
}
