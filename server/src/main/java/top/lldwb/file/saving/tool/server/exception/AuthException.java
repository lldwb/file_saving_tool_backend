package top.lldwb.file.saving.tool.server.exception;

/**
 * 自定义异常
 *
 * 装饰者模式，对父类进行扩展
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/11/27
 * @time 11:48
 * @PROJECT_NAME file_saving_tool_backend
 */
public class AuthException extends RuntimeException {
    /**
     * 异常状态码
     */
    private final Integer errorCode;

    public AuthException(String message, Integer errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public Integer getErrorCode() {
        return errorCode;
    }
}