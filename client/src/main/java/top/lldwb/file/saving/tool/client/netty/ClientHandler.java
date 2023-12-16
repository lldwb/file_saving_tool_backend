package top.lldwb.file.saving.tool.client.netty;

import cn.hutool.core.convert.Convert;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import top.lldwb.file.saving.tool.pojo.entity.Client;
import top.lldwb.file.saving.tool.service.control.ControlService;
import top.lldwb.file.saving.tool.pojo.dto.SocketMessage;

import java.io.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientHandler extends ChannelInboundHandlerAdapter {
    public static String sha256Hex;
    public static Client client;
    /**
     * 负责找到操作的Bean
     */
    private final ApplicationContext connection;

    public static ChannelHandlerContext ctx;
    public static final String PATH = "C:\\Users\\Public\\Documents\\lldwb\\Client\\Client.lldwb";

    /**
     * 连接成功后事件
     *
     * @param ctx
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    /**
     * 接收消息事件
     *
     * @param ctx
     * @param msg 接收的消息
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        // 接收消息并转成指定的类型
        SocketMessage socketMessage = Convert.convert(SocketMessage.class, msg);
        // 获取消息中指定的操作对象
        ControlService controlService = connection.getBean(socketMessage.getControlType(), ControlService.class);
        // 调用操作对象
        controlService.control(socketMessage);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // 打印异常信息
        cause.printStackTrace();
        // 关闭连接
        ctx.close();
    }
}