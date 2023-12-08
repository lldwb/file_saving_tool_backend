package top.lldwb.file.saving.tool.service.control;

import java.util.Map;

/**
 * 负责netty操作的接口
 *
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/7
 * @time 9:31
 * @PROJECT_NAME file_saving_tool_backend
 */
public interface ControlService {
    void control(Map<String,Object> data);
}
