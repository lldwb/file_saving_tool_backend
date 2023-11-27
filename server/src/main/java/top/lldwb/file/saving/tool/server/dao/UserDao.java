package top.lldwb.file.saving.tool.server.dao;

import top.lldwb.file.saving.tool.client.entity.User;

/**
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/11/27
 * @time 11:42
 * @PROJECT_NAME file_saving_tool_backend
 */
public interface UserDao {
    User getUserByName(String userName);
    User getUserByMail(String mail);
}