package top.lldwb.file.saving.tool.server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 客户端实体类
 *
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/11/30
 * @time 11:30
 * @PROJECT_NAME file_saving_tool_backend
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    /**
     * ID
     */
    private Integer clientId;
    /**
     * 秘钥
     */
    private String clientUUID;
    /**
     * 状态
     */
    private Integer clientState;
    /**
     * 用户对象
     */
    private User user;

}
