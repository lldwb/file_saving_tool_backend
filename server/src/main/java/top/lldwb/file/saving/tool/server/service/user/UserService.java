package top.lldwb.file.saving.tool.server.service.user;

import org.springframework.web.bind.annotation.PostMapping;
import top.lldwb.file.saving.tool.server.entity.User;
import top.lldwb.file.saving.tool.server.vo.ResultVO;

import java.util.List;

/**
 * 用户接口
 *
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/11/29
 * @time 15:50
 * @PROJECT_NAME file_saving_tool_backend
 */
public interface UserService {
    /**
     * 同步到es，并且过期redis
     *
     * @return
     */
    void synchronization();

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

    /**
     * 获取所有用户
     *
     * @return
     */
    List<User> getUsers(String searchParam,Integer pageNum, Integer pageSize);

    /**
     * 根据用户ID获取用户
     *
     * @param userId
     * @return
     */
    User getUserByUserId(Integer userId);
}
