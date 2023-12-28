package top.lldwb.file.saving.tool.service.netty;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import top.lldwb.file.saving.tool.pojo.dto.SocketMessage;

/**
 * 发送对象编码器(放弃)
 *
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/6
 * @time 9:02
 * @PROJECT_NAME SpringBootTest
 */

@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ObjectEncoder extends MessageToByteEncoder<SocketMessage> {
    @Override
    protected void encode(ChannelHandlerContext ctx, SocketMessage msg, ByteBuf out) throws Exception {
        // 将消息对象转换为字节数组，并将其写入到ByteBuf中
        ByteBuf encoded = ctx.alloc().buffer();
        encoded.writeBytes(new ObjectMapper().writeValueAsBytes(msg));
        // 将ByteBuf写入到ChannelPipeline中
        out.writeBytes(encoded);
    }
}
