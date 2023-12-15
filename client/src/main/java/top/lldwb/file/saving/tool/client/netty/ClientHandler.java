package top.lldwb.file.saving.tool.client.netty;

import cn.hutool.core.convert.Convert;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import top.lldwb.file.saving.tool.service.control.ControlService;
import top.lldwb.file.saving.tool.pojo.dto.SocketMessage;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientHandler extends ChannelInboundHandlerAdapter {
    /**
     * 负责找到操作的Bean
     */
    private final ApplicationContext connection;

    /**
     * 连接成功后事件
     *
     * @param ctx
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        log.info("连接服务器成功");
        ctx.close();
    }

    /**
     * 接收消息事件
     *
     * @param ctx
     * @param msg 接收的消息
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        SocketMessage socketMessage = Convert.convert(SocketMessage.class, msg);

        ControlService controlService = connection.getBean(socketMessage.getControlType(), ControlService.class);
        controlService.control(socketMessage);

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