package top.lldwb.file.saving.tool.server.service;

import top.lldwb.file.saving.tool.client.entity.User;

public interface LoginService{
    /**
     * 发送邮箱验证码(使用消息队列)
     * @param mail 邮箱
     */
    void sendMailCode(String mail);

    /**
     * 检验邮箱验证码，通过则返回用户会话
     * @param mail 邮箱
     * @param code 验证码
     */
    User mailLogin(String mail, String code);

    /**
     * 密码登录
     * @param name
     * @param password
     */
    User passwordLogin(String name,String password);
}
