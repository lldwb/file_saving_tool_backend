package top.lldwb.file.saving.tool.server.controller;

import lombok.RequiredArgsConstructor;
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

    @PostMapping("/synchronization")
    public ResultVO synchronization(PathMapping pathMapping){
        service.synchronization(pathMapping);
        return success();
    }
}
