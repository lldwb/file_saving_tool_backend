package top.lldwb.file.saving.tool.client.netty.receive;

import cn.hutool.core.convert.Convert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.lldwb.file.saving.tool.pojo.dto.SocketMessage;
import top.lldwb.file.saving.tool.pojo.entity.Client;
import top.lldwb.file.saving.tool.service.control.ControlService;

import java.util.Map;

/**
 * 客户端接收添加客户端请求
 *
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/7
 * @time 9:48
 * @PROJECT_NAME file_saving_tool_backend
 */
@Slf4j
@Service("addClient")
public class AddClientControl implements ControlService {
    @Override
    public void control(SocketMessage message) {
        Client client = Convert.convert(Client.class, message.getData());
        log.info("接收的UUID：" + client.getClientUUID());
    }
}
