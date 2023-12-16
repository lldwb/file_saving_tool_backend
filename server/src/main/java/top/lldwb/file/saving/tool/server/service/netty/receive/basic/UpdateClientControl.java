package top.lldwb.file.saving.tool.server.service.netty.receive.basic;

import cn.hutool.core.convert.Convert;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import top.lldwb.file.saving.tool.pojo.dto.SocketMessage;
import top.lldwb.file.saving.tool.pojo.entity.Client;
import top.lldwb.file.saving.tool.server.dao.ClientDao;
import top.lldwb.file.saving.tool.server.service.client.ClientService;
import top.lldwb.file.saving.tool.service.control.ControlService;
import top.lldwb.file.saving.tool.service.send.SendService;

/**
 * 更新客户端操作
 * 绑定后客户端重新上线更新数据库客户端数据的操作
 *
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/15
 * @time 11:53
 * @PROJECT_NAME file_saving_tool_backend
 */
@Service("updateClient")
@RequiredArgsConstructor
public class UpdateClientControl implements ControlService {
    private final ClientService service;
    private final SendService nettySend;

    @Override
    public void control(SocketMessage message) {
        Client client = Convert.convert(Client.class, message.getData());
        if (client.getClientId() != null) {
            client.setUserId(null);
            client.setClientState(null);
            service.updateClient(client);
        } else {
            service.addClient(client);
        }

        // 创建客户端对象消息
        SocketMessage<Client> socketMessage = new SocketMessage<>();
        socketMessage.setData("save", service.getClientBySecretKe(client.getClientSecretKey()));
        socketMessage.setSecretKey(client.getClientSecretKey());
        nettySend.send(socketMessage);
    }
}
