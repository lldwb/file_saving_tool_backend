package top.lldwb.file.saving.tool.server.controller.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 错误码
 *
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2024/1/10
 * @time 16:06
 * @PROJECT_NAME file_saving_tool_backend
 */
@Getter
@AllArgsConstructor
public enum ErrorCode {
    SUCCESS(0, "ok", ""),
    PARAMS_ERROR(40000, "请求参数错误", ""),
    NULL_ERROR(40001, "请求数据为空", ""),
    NOT_LOGIN(40100, "无登录", ""),
    NO_AUTH(40101, "无权限", ""),
    NO_MinIO(40200, "无法操作MinIO", ""),
    NO_FileInfo(40201, "无法操作文件", ""),
    DirectoryInfo(40202, "无法操作文件夹", ""),
    SYSTEM_ERROR(50000, "系统内部异常", "");
    /**
     * 状态码
     */
    private final int code;
    /**
     * 消息
     */
    private final String message;
    /**
     * 描述(详情)
     */
    private final String description;
}
