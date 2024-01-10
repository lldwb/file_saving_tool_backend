package top.lldwb.file.saving.tool.server.controller.exception.exception;

import lombok.Getter;
import top.lldwb.file.saving.tool.server.controller.common.ErrorCode;

/**
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2024/1/10
 * @time 16:14
 * @PROJECT_NAME file_saving_tool_backend
 */
@Getter
public class ErrorCodeException extends RuntimeException {
    private final ErrorCode errorCode;

    public ErrorCodeException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
