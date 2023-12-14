package top.lldwb.file.saving.tool.pojo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/14
 * @time 9:52
 * @PROJECT_NAME file_saving_tool_backend
 */
@Data
/**
 * 忽略其他不映射的字段
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SyncMessage {
    /**
     * 采集的数据
     */
    private List<?> data;
    /**
     * 表名
     */
    private String table;
    /**
     * 操作类型（INSERT、UPDATE、DELETE）
     */
    private String type;
}
