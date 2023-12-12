package top.lldwb.file.saving.tool.server.dao;

import top.lldwb.file.saving.tool.pojo.entity.OperationLog;

/**
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/4
 * @time 11:05
 * @PROJECT_NAME file_saving_tool_backend
 */
public interface OperationLogDao {
    /**
     * 根据id返回
     *
     * @param operationLogId
     * @return
     */
    OperationLog getOperationLogByOperationLogId(Integer operationLogId);

    /**
     * 添加
     *
     * @param operationLog
     */
    void addOperationLog(OperationLog operationLog);

}
