package top.lldwb.file.saving.tool.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * 操作日志
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
     * 操作日志id
     */
    private Integer operationLogId;
    /**
     * 操作日志名称
     */
    private String operationLogName;
    /**
     * minio路径
     */
    private String operationLogPath;
    /**
     * 操作类型(1添加，2修改，3删除，恢复为对应的负数)
     */
    private Integer operationLogType;
    /**
     * 字节大小
     */
    private Long operationLogSize;
    /**
     * 文件id(外键)
     * 如果为0则操作文件夹
     */
    private Integer fileInfoId;
    /**
     * 文件夹id(外键)
     */
    private Integer directoryInfoId;
    /**
     * 用户id(外键)
     */
    private Integer userId;
    /**
     * 创建时间
     */
    private Timestamp createTime;
    /**
     * 更新时间
     */
    private Timestamp updateTime;
}
