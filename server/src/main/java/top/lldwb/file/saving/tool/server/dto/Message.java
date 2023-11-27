package top.lldwb.file.saving.tool.server.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;

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
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    /**
     * 发送者
     */
    private String fromUser;
    /**
     * 接收者
     */
    private String receivingUser;
    /**
     * 标题
     */
    private String subject;
    /**
     * 内容
     */
    private String content;
    /**
     * 发送时间
     */
    private Date sendTime;
}