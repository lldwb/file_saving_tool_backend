package top.lldwb.file.saving.tool.server.service.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import top.lldwb.file.saving.tool.service.netty.ObjectDecoder;
import top.lldwb.file.saving.tool.service.netty.ObjectEncoder;

/**
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/6
 * @time 15:56
 * @PROJECT_NAME file_saving_tool_backend
 */

@Service
@Setter
@RequiredArgsConstructor
public class ServerNettyService {
    private Integer port;
    /**
     * 负责找到操作的Bean
     */
    private final ApplicationContext connection;

    public void run() {
        // 创建两个EventLoopGroup，一个用于接受客户端连接，另一个用于处理网络操作
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // 创建ServerBootstrap实例，用于启动和绑定服务器
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) {
                            // 为每一个客户端连接创建一个ServerHandler实例
                            ch.pipeline().addLast(new ObjectEncoder(), new ObjectDecoder(), new ServerHandler(connection));
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            // 绑定端口，启动服务器
            ChannelFuture f = b.bind(port).sync();

            // 等待服务器关闭
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            // 优雅关闭EventLoopGroup，释放所有资源
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
