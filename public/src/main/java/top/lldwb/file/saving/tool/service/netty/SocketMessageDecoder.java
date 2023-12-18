package top.lldwb.file.saving.tool.service.netty;

import io.netty.channel.ChannelHandler;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import top.lldwb.file.saving.tool.pojo.dto.SocketMessage;

/**
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/18
 * @time 9:18
 * @PROJECT_NAME file_saving_tool_backend
 */
@Scope(value = "prototype",proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SocketMessageDecoder extends ObjectDecoder{
    public SocketMessageDecoder() {
        super(SocketMessage.class);
    }
}
