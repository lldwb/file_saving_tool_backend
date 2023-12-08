package top.lldwb.file.saving.tool.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
     * 路径
     */
    private String fileInfoPath;
    /**
     * MinIO路径
     */
    private String fileInfoMinIOPath;
    /**
     * 类型
     */
    private Integer fileInfoType;
    /**
     * 文件特征码
     */
    private String fileInfoMd5;
    /**
     * 文件大小
     */
    private Long fileInfoSize;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 创建时间
     */
    private Integer createTime;
    /**
     * 更新时间
     */
    private Integer updateTime;
}
