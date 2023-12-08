package top.lldwb.file.saving.tool.client.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import top.lldwb.file.saving.tool.service.control.ControlService;
import top.lldwb.file.saving.tool.pojo.dto.SocketMessage;


@Service
@RequiredArgsConstructor
public class ClientHandler extends ChannelInboundHandlerAdapter {
    /**
     * 负责找到操作的Bean
     */
    private final ApplicationContext connection;

    /**
     * 接收消息事件
     *
     * @param ctx
     * @param msg 接收的消息
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws ClassNotFoundException {
        SocketMessage socketMessage = (SocketMessage) msg;

//        Class<?> clazz = Class.forName(socketMessage.getFileType());
//        System.out.println(socketMessage.getControlType());

        ControlService controlService = connection.getBean(socketMessage.getControlType(),ControlService.class);
        controlService.control(socketMessage.getData());

        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // 打印异常信息
        cause.printStackTrace();
        // 关闭连接
        ctx.close();
    }
}