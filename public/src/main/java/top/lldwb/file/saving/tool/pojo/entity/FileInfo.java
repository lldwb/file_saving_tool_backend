package top.lldwb.file.saving.tool.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
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
public class FileInfo implements Serializable {
    /**
     * 文件id
     */
    private Integer fileInfoId;
    /**
     * 文件名称
     */
    private String fileInfoName;
    /**
     * minio路径(SHA-256)
     */
    private String fileInfoPath;
    /**
     * 状态(1为存在，-1删除)，默认1
     */
    private Integer fileInfoState = 1;
    /**
     * 文件大小
     */
    private Long fileInfoSize;
    /**
     * 所在文件夹id(外键)，默认0
     */
    private Integer directoryInfoId = 0;
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
