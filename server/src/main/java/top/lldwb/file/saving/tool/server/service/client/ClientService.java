package top.lldwb.file.saving.tool.server.service.client;

import top.lldwb.file.saving.tool.pojo.entity.Client;
import top.lldwb.file.saving.tool.pojo.entity.PathMapping;

/**
 * 客户端业务接口
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/8
 * @time 9:55
 * @PROJECT_NAME file_saving_tool_backend
 */
public interface ClientService {
    /**
     * 添加客户端
     * @param client
     */
    void addClient(Client client);

    /**
     * 删除客户端
     * @param client
     */
    void deleteClient(Client client);

    /**
     * 设置自动下载(服务端监听)
     * @param pathMapping
     */
    void download(PathMapping pathMapping);

    /**
     * 设置自动上传(客户端监听)
     * @param pathMapping
     */
    void uploading(PathMapping pathMapping);
}
