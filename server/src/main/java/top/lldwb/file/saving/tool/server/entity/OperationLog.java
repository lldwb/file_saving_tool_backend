package top.lldwb.file.saving.tool.server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/11/30
 * @time 11:36
 * @PROJECT_NAME file_saving_tool_backend
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperationLog {
    /**
     * ID
     */
    private Integer operationLogId;
    /**
     * 原路径
     */
    private String operationLogPath;
    /**
     * 备份路径
     */
    private String operationLogBackupPath;
    /**
     * 操作类型
     */
    private Integer operationLogType;


    /**
     * 用户对象
     */
    private User user;
}
