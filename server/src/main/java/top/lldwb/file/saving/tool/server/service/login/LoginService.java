package top.lldwb.file.saving.tool.server.service.login;

import top.lldwb.file.saving.tool.server.entity.User;
/**
 * 登录接口
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/11/27
 * @time 11:43
 * @PROJECT_NAME file_saving_tool_backend
 */
public interface LoginService{
//    /**
//     * 发送邮箱验证码(使用消息队列)
//     * @param mail 邮箱
//     */
//    void sendMailCode(String mail);
//
//    /**
//     * 检验邮箱验证码，通过则返回用户会话
//     * @param mail 邮箱
//     * @param code 验证码
//     */
//    User mailLogin(String mail, String code);
//
//    /**
//     * 密码登录
//     * @param name
//     * @param password
//     */
//    User passwordLogin(String name,String password);

    /**
     * 登录逻辑
     * @param user 用于登录的用户信息
     * @param args 条件
     * @return
     */
    User login(User user,String... args);
}
