package top.lldwb.file.saving.tool.server.dao;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2024/1/8
 * @time 11:29
 * @PROJECT_NAME file_saving_tool_backend
 */
@Slf4j
@SpringBootTest
class DirectoryInfoDaoTest {
    @Autowired
    private DirectoryInfoDao dao;

    @Test
    void listByDirectoryInfoFatherIdAndUserId() {
        dao.listByDirectoryInfoFatherIdAndUserId(25,1).forEach(directoryInfo -> directoryInfo.getFileInfos().forEach(fileInfo -> log.info(fileInfo.getFileInfoId().toString())));
    }
}