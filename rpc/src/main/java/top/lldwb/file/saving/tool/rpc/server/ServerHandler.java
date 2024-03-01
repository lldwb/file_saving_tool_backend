package top.lldwb.file.saving.tool.rpc.server;

import cn.hutool.core.convert.Convert;
import cn.hutool.crypto.digest.DigestUtil;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import top.lldwb.file.saving.tool.rpc.message.SocketMessage;

import java.util.HashMap;
import java.util.Map;

/**
 * 服务端处理器
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * 负责找到操作的Bean
     */
    public final ApplicationContext connection;

    // 客户端容器
    private static Map<String, ChannelHandlerContext> clientMap = new HashMap<>();
    // 绑定容器
    private static Map<String,Boolean> binding = new HashMap<>();

    public static ChannelHandlerContext getChannelHandlerContext(String clientSecretKey) {
        return clientMap.get(clientSecretKey);
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
        // 会话id进行SHA-256加密生成秘钥
        String sha256Hex = DigestUtil.sha256Hex(ctx.channel().id().asShortText());
        // 添加客户端到容器中(秘钥,会话)
        clientMap.put(sha256Hex, ctx);

        log.info("客户端连接成功，秘钥为：" + sha256Hex);

        // 第一次连接发送秘钥给客户端(绑定)
        SocketMessage message = new SocketMessage();
        // First 第一次连接
        message.setData("virgin",sha256Hex);
        // 向客户端发送消息
        ChannelFuture f = ctx.writeAndFlush(message);

////         添加关闭监听器
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
        SocketMessage socketMessage = Convert.convert(SocketMessage.class,msg);

        log.info("操作类型：{}",socketMessage.getControlType());
        log.info("消息内容：{}",socketMessage.getData());

        // 更换成动态代理
//        ControlService controlService = connection.getBean(socketMessage.getControlType(), ControlService.class);
//        controlService.control(socketMessage);
    }

    /**
     * 关闭连接前事件
     *
     * @param ctx
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        Integer count = clientMap.size();
        // 移除
        clientMap.remove(DigestUtil.sha256Hex(ctx.channel().id().asShortText()));
        // 打印日志
        log.info("客户端会话容器长度变化 {} -> {}", count, clientMap.size());
//        System.out.println("关闭连接事件");
    }

    /**
     * 关闭连接后事件
     *
     * @param ctx
     */
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) {
//        System.out.println("未注册事件");
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