package top.lldwb.file.saving.tool.server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录风控实体类
 *
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/11/30
 * @time 11:33
 * @PROJECT_NAME file_saving_tool_backend
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRiskControl {
    /**
     * ID
     */
    private Integer loginRiskControlId;
    /**
     * 类型
     */
    private Integer loginRiskControlType;
    /**
     * 详情
     */
    private String loginRiskControlDetails;
    /**
     * 结果
     */
    private Integer loginRiskControlResult;
    /**
     * 用户对象
     */
    private User user;

}
