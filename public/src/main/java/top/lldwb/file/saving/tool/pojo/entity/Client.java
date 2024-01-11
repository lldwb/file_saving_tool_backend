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
     * 客户端id
     */
    private Integer clientId;
    /**
     * 秘钥
     */
    private String clientSecretKey;
    /**
     * 状态 1在线 0离线 -1无主 默认-1
     */
    private Integer clientState;
    /**
     * 用户idv(外键)
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

    @Override
    public String toString() {
        return "Client{" +
                "clientId=" + clientId +
                ", clientSecretKey='" + clientSecretKey + '\'' +
                ", clientState=" + clientState +
                ", userId=" + userId +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
