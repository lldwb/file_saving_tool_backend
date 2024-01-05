package top.lldwb.file.saving.tool.server.service.entity;

import top.lldwb.file.saving.tool.pojo.entity.Client;
import top.lldwb.file.saving.tool.pojo.entity.FileInfo;
import top.lldwb.file.saving.tool.pojo.entity.PathMapping;

import java.util.List;

/**
 * 客户端业务接口
 *
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/8
 * @time 9:55
 * @PROJECT_NAME file_saving_tool_backend
 */
public interface ClientService {
    /**
     * 添加客户端
     *
     * @param client
     */
    void addClient(Client client);

    /**
     * 更新客户端
     *
     * @param client
     */
    void updateClient(Client client);

    /**
     * 更新客户端
     *
     * @param client
     */
    void updateClientBySecretKe(Client client);

    /**
     * 根据客户端id查询客户端
     *
     * @param clientId
     * @return
     */
    Client getClientById(Integer clientId);

    Client getClientBySecretKe(String clientSecretKey);

    /**
     * 设置自动下载(服务端监听)
     *
     * @param pathMapping
     */
    void download(PathMapping pathMapping);

    /**
     * 设置自动上传(客户端监听)
     *
     * @param pathMapping
     */
    void uploading(PathMapping pathMapping);

    /**
     * 设置同步
     *
     * @param pathMapping
     */
    void synchronization(PathMapping pathMapping);

    /**
     * 客户端下载
     */
    void ClientDownload(PathMapping pathMapping,FileInfo fileInfo);

    /**
     * 获取用户所属的客户端
     * @param userId
     * @return
     */
    List<Client> listClient(Integer userId);
}
