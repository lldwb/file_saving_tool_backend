package top.lldwb.file.saving.tool.client.netty.receive;

import org.springframework.stereotype.Service;
import top.lldwb.file.saving.tool.service.control.ControlService;

import java.util.Map;

/**
 * 第一次连接操作
 *
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/7
 * @time 9:48
 * @PROJECT_NAME file_saving_tool_backend
 */
@Service("first")
public class FirstControl implements ControlService {
    @Override
    public void control(Map<String, Object> data) {
        System.out.println("接收的消息" + data.get("UUID"));
    }
}
