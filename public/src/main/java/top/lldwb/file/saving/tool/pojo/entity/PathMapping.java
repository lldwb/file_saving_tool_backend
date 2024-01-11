package top.lldwb.file.saving.tool.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

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
public class PathMapping implements Serializable {
    /**
     * 路径映射id
     */
    private Integer pathMappingId;
    /**
     * 本地路径
     */
    private String pathMappingLocalPath;
    /**
     * 文件夹id(外键)
     */
    private Integer directoryInfoId = 0;
    /**
     * 类型(1上传2下载3同步，删除为对应的负数)
     */
    private Integer pathMappingType;
    /**
     * 客户端id(外键)
     */
    private Integer clientId;
    /**
     * 用户对象id(外键)
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
