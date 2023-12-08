package top.lldwb.file.saving.tool.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 路径映射表
 *
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/11/30
 * @time 11:36
 * @PROJECT_NAME file_saving_tool_backend
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PathMapping {
    /**
     * ID
     */
    private Integer pathMappingId;
    /**
     * 本地路径
     */
    private String pathMappingLocalPath;
    /**
     * 远程路径
     */
    private String pathMappingRemotePath;
    /**
     * 类型
     */
    private Integer pathMappingType;
    /**
     * 客户端id
     */
    private Integer clientId;
    /**
     * 用户对象id
     */
    private Integer userId;
}
