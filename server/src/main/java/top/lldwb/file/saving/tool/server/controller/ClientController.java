package top.lldwb.file.saving.tool.server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import top.lldwb.file.saving.tool.pojo.dto.AuthCode;
import top.lldwb.file.saving.tool.pojo.entity.Client;
import top.lldwb.file.saving.tool.pojo.entity.User;
import top.lldwb.file.saving.tool.server.exception.AuthException;
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
     * 绑定客户端
     *
     * @param client
     * @return
     */
    @PutMapping("/binding")
    public ResultVO binding(Client client) {
        return success();
    }

    /**
     * 释放客户端
     *
     * @return
     */
    @DeleteMapping("/unbound")
    public ResultVO unbound() {
        return success();
    }
}
