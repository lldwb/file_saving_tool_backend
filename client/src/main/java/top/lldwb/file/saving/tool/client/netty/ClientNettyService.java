package top.lldwb.file.saving.tool.client.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import top.lldwb.file.saving.tool.service.netty.ObjectDecoder;
import top.lldwb.file.saving.tool.service.netty.ObjectEncoder;

/**
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/7
 * @time 9:09
 * @PROJECT_NAME file_saving_tool_backend
 */
@Service
@Setter
@RequiredArgsConstructor
public class ClientNettyService {
    /**
     * 负责找到操作的Bean
     */
    private final ApplicationContext connection;
    private String host;
    private Integer port;

    public void run() {
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
                    ChannelPipeline channel = ch.pipeline();

                    channel.addLast(new ObjectEncoder(), new ObjectDecoder(), new ClientHandler(connection));
                }
            });

            // 连接服务器
            ChannelFuture future = bootstrap.connect(host, port).sync();

            // 关闭连接
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            // 优雅关闭EventLoopGroup
            workerGroup.shutdownGracefully();
        }
    }
}
