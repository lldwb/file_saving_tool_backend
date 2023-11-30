package top.lldwb.file.saving.tool.server.entity;

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
     * 路径
     */
    private String shareLogPath;
    /**
     * 权限
     */
    private String shareLogAuthority;

    /**
     * 用户对象
     */
    private User user;
}
