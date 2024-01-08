package top.lldwb.file.saving.tool.server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import top.lldwb.file.saving.tool.pojo.entity.User;
import top.lldwb.file.saving.tool.server.service.entity.UserService;
import top.lldwb.file.saving.tool.server.pojo.vo.ResultVO;

/**
 * 用户接口
 *
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/11/29
 * @time 15:03
 * @PROJECT_NAME file_saving_tool_backend
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController extends BaseController {
    private final UserService service;

    /**
     * 同步到es，并且过期redis
     *
     * @return 成功响应
     */
    @PostMapping("/synchronization")
    public ResultVO synchronization() {
        service.synchronization();
        return success();
    }

    /**
     * 创建用户
     *
     * @param user 用户对象
     * @return 成功响应
     */
    @PutMapping("/add")
    public ResultVO add(@RequestBody User user) {
        service.addUser(user);
        return success();
    }

    /**
     * 更新用户
     *
     * @param user
     */
    @PostMapping("/update")
    void update(@RequestBody User user) {
        service.updateUser(user);
    }

    /**
     * 获取用户列表
     *
     * @return 成功响应(用户列表)
     */
    @GetMapping("/getUsers")
    public ResultVO getUsers(String searchParam, Integer pageNum, Integer pageSize) {
        return success(service.getUsers(searchParam, pageNum, pageSize));
    }

    /**
     * 根据用户ID获取用户
     *
     * @return 成功响应(用户数据)
     */
    @GetMapping("/getUserByUserId")
    public ResultVO getUserByUserId(Integer userId) {
        return success(service.getUserByUserId(userId));
    }
}
