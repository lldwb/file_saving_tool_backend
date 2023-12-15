package top.lldwb.file.saving.tool.server.service.netty.receive.basic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.lldwb.file.saving.tool.pojo.dto.SocketMessage;
import top.lldwb.file.saving.tool.service.control.ControlService;

/**
 * 添加客户端操作
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/15
 * @time 9:52
 * @PROJECT_NAME file_saving_tool_backend
 */
@Slf4j
@Service("addClient")
public class AddClientControl implements ControlService {

    @Override
    public void control(SocketMessage message) {

    }
}
