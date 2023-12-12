package top.lldwb.file.saving.tool.server.service.send.impl;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import top.lldwb.file.saving.tool.pojo.dto.AuthCode;
import top.lldwb.file.saving.tool.pojo.dto.Message;
import top.lldwb.file.saving.tool.pojo.dto.SocketMessage;
import top.lldwb.file.saving.tool.server.service.netty.ServerHandler;
import top.lldwb.file.saving.tool.server.service.send.SendService;

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
@Scope(value = "prototype",proxyMode = ScopedProxyMode.TARGET_CLASS)
@RequiredArgsConstructor
public class NettySend implements SendService {
//    private final ServerHandler serverHandler;
    /**
     * 需要发送消息的客户端
     */
    private String UUID;

    @Override
    public void send(Message message) {
        SocketMessage socketMessage = (SocketMessage)message;
        // 发送消息并且立刻刷新(设置消息并且立刻发送)
        // 添加关闭监听器
//        serverHandler.getChannelHandlerContext(UUID).writeAndFlush(socketMessage);

//      serverHandler.getChannelHandlerContext(UUID).writeAndFlush(socketMessage).addListener(ChannelFutureListener.CLOSE);
    }
}
