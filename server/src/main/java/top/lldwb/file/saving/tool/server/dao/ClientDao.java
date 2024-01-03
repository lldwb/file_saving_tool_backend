package top.lldwb.file.saving.tool.server.dao;

import top.lldwb.file.saving.tool.pojo.entity.Client;

import java.util.List;

/**
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/15
 * @time 9:53
 * @PROJECT_NAME file_saving_tool_backend
 */
public interface ClientDao {
    void addClient(Client client);

    void updateClient(Client client);

    Client getClientById(Integer clientId);

    Client getClientBySecretKe(String clientSecretKey);

    /**
     * 获取用户所属的客户端
     *
     * @param userId
     * @return
     */
    List<Client> listClient(Integer userId);
}
