package top.lldwb.file.saving.tool.rpc.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import java.util.List;

/**
 * SocketMessage解码器
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/18
 * @time 9:18
 * @PROJECT_NAME file_saving_tool_backend
 */
@Scope(value = "prototype",proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SocketMessageDecoder extends ReplayingDecoder<SocketMessage> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // 读取数据，可以像普通操作一样，不需要手动检查可读字节数
        int length = in.readInt();
        byte[] data = new byte[length];
        in.readBytes(data);

        // 解码并添加到输出列表
        out.add(new ObjectMapper().readValue(data, SocketMessage.class));
    }
}
