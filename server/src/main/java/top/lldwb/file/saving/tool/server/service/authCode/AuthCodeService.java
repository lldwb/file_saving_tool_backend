package top.lldwb.file.saving.tool.server.service.authCode;

import top.lldwb.file.saving.tool.server.pojo.dto.AuthCode;

/**
 * 验证码
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/11/27
 * @time 19:02
 * @PROJECT_NAME file_saving_tool_backend
 */
public interface AuthCodeService {
    /**
     * 发送验证码
     * @param authCode 验证码对象
     */
    void sendAuthCode(AuthCode authCode);
}
