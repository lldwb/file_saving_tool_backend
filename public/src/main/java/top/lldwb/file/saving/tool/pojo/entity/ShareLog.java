package top.lldwb.file.saving.tool.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分享日志实体类
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/11/30
 * @time 11:36
 * @PROJECT_NAME file_saving_tool_backend
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShareLog {
    /**
     * ID
     */
    private Integer shareLogId;
    /**
     * 权限
     */
    private String shareLogAuthority;
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
     * 用户对象
     */
    private User user;
}
