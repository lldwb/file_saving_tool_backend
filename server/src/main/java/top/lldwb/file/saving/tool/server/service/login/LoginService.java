package top.lldwb.file.saving.tool.server.service.login;

import top.lldwb.file.saving.tool.pojo.entity.User;
/**
 * 登录接口
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/11/27
 * @time 11:43
 * @PROJECT_NAME file_saving_tool_backend
 */
public interface LoginService{
    /**
     * 登录逻辑
     * @param user 用于登录的用户信息
     * @param args 条件
     * @return
     */
    User login(User user,String... args);
}
