package top.lldwb.file.saving.tool.server.dao;

import top.lldwb.file.saving.tool.server.pojo.entity.OperationLog;

/**
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/4
 * @time 11:05
 * @PROJECT_NAME file_saving_tool_backend
 */
public interface OperationLogDao {
    OperationLog getOperationLogByOperationLogId(Integer operationLogId);
    void addOperationLog(OperationLog operationLog);

}
