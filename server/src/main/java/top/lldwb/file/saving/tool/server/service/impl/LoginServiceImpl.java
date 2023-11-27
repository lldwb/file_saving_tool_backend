package top.lldwb.file.saving.tool.server.service.impl;

import top.lldwb.file.saving.tool.client.entity.User;
import top.lldwb.file.saving.tool.server.service.LoginService;

public class LoginServiceImpl implements LoginService {
    @Override
    public void sendMailCode(String mail) {

    }

    @Override
    public User mailLogin(String mail, String code) {
        return null;
    }

    @Override
    public User passwordLogin(String name, String password) {
        return null;
    }
}
