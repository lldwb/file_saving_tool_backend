package top.lldwb.file.saving.tool.server.service.send;

import top.lldwb.file.saving.tool.server.pojo.dto.AuthCode;

/**
 * 负责发送消息的接口
 *
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/11/27
 * @time 14:55
 * @PROJECT_NAME file_saving_tool_backend
 */
public interface SendService {
    /**
     * 发送消息的抽象方法
     * @param receiving 接收
     * @param messages 消息
     */
    void send(AuthCode authCode);
}
