package top.lldwb.file.saving.tool.server.service.netty.receive.basic;

import org.springframework.stereotype.Service;
import top.lldwb.file.saving.tool.pojo.dto.SocketMessage;
import top.lldwb.file.saving.tool.service.control.ControlService;

/**
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/15
 * @time 11:53
 * @PROJECT_NAME file_saving_tool_backend
 */
@Service("updateClient")
public class UpdateClientControl implements ControlService {
    @Override
    public void control(SocketMessage message) {

    }
}
