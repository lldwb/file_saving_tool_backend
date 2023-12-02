package top.lldwb.file.saving.tool.server.dao;

import top.lldwb.file.saving.tool.server.pojo.entity.User;

import java.util.List;

/**
 * 用户数据层
 *
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/11/27
 * @time 11:42
 * @PROJECT_NAME file_saving_tool_backend
 */
public interface UserDao {
    /**
     * 根据用户ID获取用户
     *
     * @param userId
     * @return
     */
    User getUserByUserId(Integer userId);

    /**
     * 根据用户名获取用户
     *
     * @param userName 用户名
     * @return
     */
    User getUserByName(String userName);

    /**
     * 根据邮箱获取用户
     *
     * @param mail 邮箱
     * @return
     */
    User getUserByMail(String mail);

    /**
     * 获取所有用户
     *
     * @return
     */
    List<User> getUsers();

    /**
     * 添加用户
     *
     * @param user
     */
    void addUser(User user);

    /**
     * 更新用户
     *
     * @param user
     */
    void updateUser(User user);
}