package top.lldwb.file.saving.tool.server.service.netty;

import cn.hutool.core.util.IdUtil;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import top.lldwb.file.saving.tool.pojo.dto.SocketMessage;
import top.lldwb.file.saving.tool.service.control.ControlService;


import java.util.HashMap;
import java.util.Map;

/**
 * 服务端处理器
 */
@Service
@RequiredArgsConstructor
public class ServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * 负责找到操作的Bean
     */
    private final ApplicationContext connection;

    // 客户端容器
    private Map<String,ChannelHandlerContext> ctxs = new HashMap<>();

    public ChannelHandlerContext getChannelHandlerContext(String UUID){
        return ctxs.get(UUID);
    }

    /**
     * 连接成功前事件
     *
     * @param ctx
     */
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) {
        System.out.println("注册事件");
    }

    /**
     * 连接成功后事件
     *
     * @param ctx
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        String UUID = IdUtil.simpleUUID();
        // 添加客户端到容器中
        ctxs.put(UUID,ctx);

        // 第一次连接发送UUID给客户端
//        SocketMessage message = new SocketMessage();
//        message.setData(UUID);
//        message.setFileType(String.class.getName());
//        // First 第一次连接
//        message.setControlType("first");
//        // 向客户端发送消息
//        ChannelFuture f = ctx.writeAndFlush(message);

        // 添加关闭监听器
//        f.addListener(ChannelFutureListener.CLOSE);
    }

    /**
     * 接收消息事件
     *
     * @param ctx
     * @param msg 接收的消息
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws ClassNotFoundException {
        SocketMessage socketMessage = (SocketMessage) msg;

        Class<?> clazz = Class.forName(socketMessage.getFileType());
        System.out.println(socketMessage.getControlType());

        ControlService controlService = connection.getBean(socketMessage.getControlType(),ControlService.class);
        controlService.control(socketMessage.getData());

        ctx.close();
    }

    /**
     * 关闭连接前事件
     *
     * @param ctx
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        // 移除
//        ctxs.remove();
        System.out.println("关闭连接事件");
    }

    /**
     * 关闭连接后事件
     *
     * @param ctx
     */
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) {
        System.out.println("未注册事件");
    }

    /**
     * 连接异常处理
     *
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // 打印异常信息
        cause.printStackTrace();
        // 关闭连接
        ctx.close();
    }
}