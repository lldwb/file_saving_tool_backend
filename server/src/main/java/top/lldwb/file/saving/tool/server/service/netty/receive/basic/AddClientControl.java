package top.lldwb.file.saving.tool.server.service.netty.receive.basic;

import cn.hutool.core.convert.Convert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.lldwb.file.saving.tool.pojo.dto.SocketMessage;
import top.lldwb.file.saving.tool.pojo.entity.Client;
import top.lldwb.file.saving.tool.server.dao.ClientDao;
import top.lldwb.file.saving.tool.service.control.ControlService;
import top.lldwb.file.saving.tool.service.send.SendService;

/**
 * 添加客户端操作
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/15
 * @time 9:52
 * @PROJECT_NAME file_saving_tool_backend
 */
@Slf4j
@Service("addClient")
@RequiredArgsConstructor
public class AddClientControl implements ControlService {
    private final ClientDao clientDao;
    private final SendService nettySend;

    @Override
    public void control(SocketMessage message) {
        Client client = Convert.convert(Client.class, message.getData());
        clientDao.addClient(client);
        // 创建客户端对象消息
        SocketMessage<Client> socketMessage = new SocketMessage<>();
        socketMessage.setData(clientDao.getClientById(client.getClientId()));
        socketMessage.setControlType("save");
        socketMessage.setClazz(Client.class);
        nettySend.send(socketMessage);
    }
}
