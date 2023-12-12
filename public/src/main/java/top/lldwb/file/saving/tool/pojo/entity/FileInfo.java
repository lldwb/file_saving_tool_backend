package top.lldwb.file.saving.tool.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/1
 * @time 15:15
 * @PROJECT_NAME file_saving_tool_backend
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileInfo {
    /**
     * ID
     */
    private Integer fileInfoId;
    /**
     * 名称
     */
    private String fileInfoName;
    /**
     * minio路径(SHA-256)
     */
    private String fileInfoPath;
    /**
     * 状态(1为存在，-1删除)
     */
    private Integer fileInfoState;
    /**
     * 文件大小
     */
    private Long fileInfoSize;
    /**
     * 文件夹id
     */
    private Integer directoryInfoId;
    /**
     * 用户id
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
