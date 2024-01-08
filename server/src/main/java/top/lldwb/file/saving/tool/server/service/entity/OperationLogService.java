package top.lldwb.file.saving.tool.server.service.entity;

import top.lldwb.file.saving.tool.pojo.entity.OperationLog;

import java.util.List;

/**
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2024/1/5
 * @time 11:31
 * @PROJECT_NAME file_saving_tool_backend
 */
public interface OperationLogService {
    List<OperationLog> listOperationLogByUserId(Integer userId, Integer pageNum, Integer pageSize);
}
