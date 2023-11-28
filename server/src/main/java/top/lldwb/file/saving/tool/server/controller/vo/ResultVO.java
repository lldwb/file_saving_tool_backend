package top.lldwb.file.saving.tool.server.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 视图响应对象
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/11/28
 * @time 20:34
 * @PROJECT_NAME file_saving_tool_backend
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultVO<T> {
    private Integer code = HttpStatus.OK.value();
    private String message = "成功响应";
    private T data;
}
