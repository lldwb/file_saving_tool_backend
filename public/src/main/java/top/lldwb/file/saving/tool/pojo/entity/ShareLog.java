package top.lldwb.file.saving.tool.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

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
     * 分享权限(0不分享、1只读)
     */
    private Integer shareLogAuthority = 1;
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
    /**
     * 文件对象(多表查询)
     */
    private FileInfo fileInfo;
    /**
     * 文件夹对象(多表查询)
     */
    private DirectoryInfo directoryInfo;
}
