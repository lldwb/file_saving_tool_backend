package top.lldwb.file.saving.tool.server.controller.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.lldwb.file.saving.tool.server.controller.common.BaseResponse;
import top.lldwb.file.saving.tool.server.pojo.vo.ResultVO;

/**
 * 自定义异常捕获
 *
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2024/1/10
 * @time 15:13
 * @PROJECT_NAME file_saving_tool_backend
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends BaseResponse {
    /**
     * 运行异常的捕获和抛出
     *
     * @param e
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    public ResultVO runtimeExceptionHandler(RuntimeException e) {
        log.error("runtimeException", e);
        return success(500, e.getMessage());
    }
}
