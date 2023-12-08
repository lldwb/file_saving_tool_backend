package top.lldwb.file.saving.tool.client.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import top.lldwb.file.saving.tool.service.netty.ObjectDecoder;
import top.lldwb.file.saving.tool.service.netty.ObjectEncoder;

/**
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/7
 * @time 9:09
 * @PROJECT_NAME file_saving_tool_backend
 */
@Setter
@RequiredArgsConstructor
public class ClientNettyService {
    private final ClientHandler clientHandler;
    private final ObjectEncoder objectEncoder;
    private final ObjectDecoder objectDecoder;
    private String host;
    private Integer port;

    public void run() throws InterruptedException {
        // 创建EventLoopGroup，用于处理网络操作
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // 创建Bootstrap实例，可以轻松引导Channel以供客户端使用
            Bootstrap bootstrap = new Bootstrap();
            // 设置EventLoopGroup
            bootstrap.group(workerGroup);
            // 设置Channel类型
            bootstrap.channel(NioSocketChannel.class);
            // 设置SO_KEEPALIVE选项
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            // 设置ChannelHandler
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) {
                    ch.pipeline().addLast(objectEncoder, objectDecoder, clientHandler);
                }
            });

            // 连接服务器
            ChannelFuture future = bootstrap.connect(host, port).sync();

            // 关闭连接
            future.channel().closeFuture().sync();
        } finally {
            // 优雅关闭EventLoopGroup
            workerGroup.shutdownGracefully();
        }
    }
}
