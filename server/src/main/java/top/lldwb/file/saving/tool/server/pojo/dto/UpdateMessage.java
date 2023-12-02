package top.lldwb.file.saving.tool.server.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/1
 * @time 11:51
 * @PROJECT_NAME file_saving_tool_backend
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMessage<T> extends Message {
    private String id;
    private T Data;
}
