package top.lldwb.file.saving.tool.server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lldwb.file.saving.tool.pojo.entity.Client;
import top.lldwb.file.saving.tool.pojo.entity.PathMapping;
import top.lldwb.file.saving.tool.server.pojo.vo.ResultVO;
import top.lldwb.file.saving.tool.server.service.client.ClientService;

/**
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/14
 * @time 16:33
 * @PROJECT_NAME file_saving_tool_backend
 */
@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController extends BaseController {
    private final ClientService service;

    /**
     * 修改客户端(绑定和解绑本质上都是修改客户端的用户id)
     *
     * @param client
     * @return
     */
    @PostMapping("/updateClient")
    public ResultVO updateClient(Client client) {
        if (client.getClientId() != null) {
            service.updateClient(client);
        } else {
            service.updateClientBySecretKe(client);
        }
        return success();
    }

    /**
     * 移除客户端
     * @param clientId
     * @return
     */
    @DeleteMapping("/deleteClient")
    public ResultVO deleteClient(Integer clientId){
        Client client = new Client();
        client.setClientId(clientId);
        client.setUserId(0);
        service.updateClient(client);
        return success();
    }

    /**
     * 设置同步
     * @param pathMapping
     * @return
     */
    @PostMapping("/synchronization")
    public ResultVO synchronization(PathMapping pathMapping){
        service.synchronization(pathMapping);
        return success();
    }
}
