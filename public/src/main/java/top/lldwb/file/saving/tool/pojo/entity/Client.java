package top.lldwb.file.saving.tool.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

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
public class Client implements Serializable {
    /**
     * ID
     */
    private Integer clientId;
    /**
     * 秘钥
     */
    private String clientSecretKey;
    /**
     * 状态
     */
    private Integer clientState;
    /**
     * 用户对象
     */
    private Integer userId;
    /**
     * 创建时间
     */
    private Timestamp createTime;
    /**
     * 更新时间
     */
    private Timestamp updateTime;

}
