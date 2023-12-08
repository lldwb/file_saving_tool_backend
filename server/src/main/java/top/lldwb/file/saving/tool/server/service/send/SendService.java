package top.lldwb.file.saving.tool.server.service.send;


import top.lldwb.file.saving.tool.pojo.dto.AuthCode;
import top.lldwb.file.saving.tool.pojo.dto.Message;

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
     */
    void send(Message message);
}
