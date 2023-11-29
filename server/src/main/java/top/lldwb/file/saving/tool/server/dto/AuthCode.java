package top.lldwb.file.saving.tool.server.dto;

import cn.hutool.core.util.RandomUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 验证码对象
 *
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/11/27
 * @time 19:35
 * @PROJECT_NAME file_saving_tool_backend
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthCode extends Message{
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
    private String subject = "验证码_来自个人开发者：安然的尾巴(lldwb.top)";
    /**
     * 验证码
     */
    private String AuthCode = RandomUtil.randomNumbers(6);
}
