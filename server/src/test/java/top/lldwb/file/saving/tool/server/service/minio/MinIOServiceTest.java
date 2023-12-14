package top.lldwb.file.saving.tool.server.service.minio;

import cn.hutool.crypto.digest.DigestUtil;
import io.minio.GetObjectArgs;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;
import top.lldwb.file.saving.tool.config.MinIOConfig;
import top.lldwb.file.saving.tool.pojo.dto.UpdateMessage;
import top.lldwb.file.saving.tool.pojo.entity.FileInfo;
import top.lldwb.file.saving.tool.pojo.entity.OperationLog;
import top.lldwb.file.saving.tool.server.config.RabbitConfig;
import top.lldwb.file.saving.tool.server.config.RabbitUpdate;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/14
 * @time 15:54
 * @PROJECT_NAME file_saving_tool_backend
 */
@SpringBootTest
class MinIOServiceTest {
    @Autowired
    private MinIOService minIOService;

    @Test
    void addFile() {
    }

    @Test
    void testAddFile() {

        String sha256Hex = DigestUtil.sha256Hex("inputStream");

        // 文件信息对象
        FileInfo fileInfo = new FileInfo();
        fileInfo.setUserId(1);
        // 随机生成的
        fileInfo.setFileInfoPath(sha256Hex);
        fileInfo.setFileInfoSize(1324L);
        fileInfo.setFileInfoName("wt.d");

        minIOService.addFile(fileInfo);
    }
}