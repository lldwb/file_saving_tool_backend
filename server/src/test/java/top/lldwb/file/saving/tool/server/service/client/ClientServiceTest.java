package top.lldwb.file.saving.tool.server.service.client;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.lldwb.file.saving.tool.pojo.entity.Client;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/14
 * @time 19:14
 * @PROJECT_NAME file_saving_tool_backend
 */
@SpringBootTest
class ClientServiceTest {
    @Autowired
    private ClientService clientService;

    @Test
    void addClient() {
        Client client = new Client();
        clientService.addClient(client);
    }

    @Test
    void deleteClient() {
    }

    @Test
    void download() {
    }

    @Test
    void uploading() {
    }
}