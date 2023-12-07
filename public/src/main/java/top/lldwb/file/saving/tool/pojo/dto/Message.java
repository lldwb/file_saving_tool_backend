package top.lldwb.file.saving.tool.pojo.dto;

import lombok.Data;

/**
 * 消息实体
 *
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/11/27
 * @time 15:20
 * @PROJECT_NAME file_saving_tool_backend
 */
@Data
public class Message<T> {
    /**
     * 数据
     */
    private T Data;
}