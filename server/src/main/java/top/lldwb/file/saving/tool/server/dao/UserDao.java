package top.lldwb.file.saving.tool.server.dao;

import top.lldwb.file.saving.tool.server.entity.User;

/**
 * 用户数据层
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/11/27
 * @time 11:42
 * @PROJECT_NAME file_saving_tool_backend
 */
public interface UserDao {
    User getUserByName(String userName);
    User getUserByMail(String mail);
    void addUser(User user);
    void updateUser(User user);
}