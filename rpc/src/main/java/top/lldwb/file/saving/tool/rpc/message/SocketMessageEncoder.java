package top.lldwb.file.saving.tool.rpc.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * SocketMessage编码器
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/28
 * @time 8:52
 * @PROJECT_NAME file_saving_tool_backend
 */
public class SocketMessageEncoder extends MessageToByteEncoder<SocketMessage> {
    @Override
    protected void encode(ChannelHandlerContext ctx, SocketMessage msg, ByteBuf out) throws Exception {
        // 将字符串编码为字节数组，并写入输出缓冲区
        byte[] data = new ObjectMapper().writeValueAsBytes(msg);
        out.writeInt(data.length);
        out.writeBytes(data);
    }
}
