package top.lldwb.file.saving.tool.pojo.entity;

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
     * 名称
     */
    private String operationLogName;
    /**
     * 路径
     */
    private String operationLogPath;
    /**
     * minio路径
     */
    private String operationLogMinioPath;
    /**
     * 操作类型(1添加，2删除，3修改)
     */
    private Integer operationLogType;
    /**
     * 文件类型(1文件夹，2文件)
     */
    private Integer operationLogFileType;

    /**
     * 文件md5特征码
     *
     * @return
     */
    private String operationLogMd5;
    /**
     * 字节大小
     */
    private Long operationLogSize;

    /**
     * 用户id(外键)
     */
    private Integer userId;
    /**
     * 文件id(外键)
     */
    private Integer fileInfoId;
    /**
     * 创建时间
     */
    private Integer createTime;
    /**
     * 更新时间
     */
    private Integer updateTime;
}
