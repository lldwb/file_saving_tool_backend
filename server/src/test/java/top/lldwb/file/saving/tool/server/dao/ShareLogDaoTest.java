package top.lldwb.file.saving.tool.server.dao;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2024/1/5
 * @time 9:55
 * @PROJECT_NAME file_saving_tool_backend
 */
@Slf4j
@SpringBootTest
class ShareLogDaoTest {
    @Autowired
    private ShareLogDao dao;

    @Test
    void listShareLogsByUserId() {
        dao.listShareLogsByUserId(1).forEach(shareLog -> log.info("shareLog：{}", shareLog));
    }

    @Test
    void listShareLogsByDirectoryInfoId() {
    }
}