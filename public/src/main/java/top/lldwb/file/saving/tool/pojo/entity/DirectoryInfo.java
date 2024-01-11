package top.lldwb.file.saving.tool.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

/**
 * 文件夹信息
 *
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/12
 * @time 8:54
 * @PROJECT_NAME file_saving_tool_backend
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DirectoryInfo {
    /**
     * 文件夹id
     */
    private Integer directoryInfoId;
    /**
     * 文件夹名称
     */
    private String directoryInfoName;
    /**
     * 父文件夹id
     */
    private Integer directoryInfoFatherId;
    /**
     * 状态(1为存在，-1删除)，默认1
     */
    private Integer directoryInfoState = 1;
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
     * 文件夹下面的文件
     */
    private List<FileInfo> fileInfos;
}
