package top.lldwb.file.saving.tool.server.service.minio.impl;

import cn.hutool.crypto.digest.DigestUtil;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.StatObjectArgs;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.lldwb.file.saving.tool.config.MinIOConfig;
import top.lldwb.file.saving.tool.service.minIO.MinIOSaveService;

import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/19
 * @time 11:32
 * @PROJECT_NAME file_saving_tool_backend
 */
@Slf4j
@SpringBootTest
class MinIOServiceImplTest {
    @Autowired
    private MinioClient minioClient;
    @Autowired
    private MinIOSaveService minIOSaveService;

    @Test
    void addFile() throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        File file = new File("C:\\Users\\32471\\Downloads\\2434\\12414 - 副本.txt");
        InputStream inputStream = new FileInputStream(file);
        String sha256Hex = DigestUtil.sha256Hex(file);
        // 检测是否已经存在，如果存在则不上传
        log.info("检测是否已经存在，如果存在则不上传");
        try {
            log.info("检测是否已经存在，如果存在则不上传");
//            minioClient.getObject(GetObjectArgs.builder().bucket(MinIOConfig.BUCKET).object(sha256Hex).length(1L).build());
            // 判断文件是否存在
            boolean exists = minioClient.statObject(StatObjectArgs.builder().bucket(MinIOConfig.BUCKET).object(sha256Hex).build()) != null;
        } catch (Exception e) {
            try {
                log.info("上传{}文件到Minio", sha256Hex);
                minioClient.putObject(PutObjectArgs.builder().bucket(MinIOConfig.BUCKET).object(sha256Hex).stream(inputStream, file.length(), -1).build());
            } catch (Exception ex) {
                log.info("上传出错");
            }
        }
        log.info("结束");
    }

    @Test
    void testAddFile() throws FileNotFoundException {
        File file = new File("C:\\Users\\32471\\Downloads\\2434\\12414 - 副本.txt");
//        minIOSaveService.saveMinIO(new File("E:\\Telegram Desktop\\1 (3).txt"));
        minIOSaveService.saveMinIO(file);
//        minIOSaveService.saveMinIO(new FileInputStream(file), file.length(),DigestUtil.sha256Hex(file));
    }

    @Test
    void deleteFile() {
    }

    @Test
    void recoverFile() {
    }

    @Test
    void downloadFile() {
    }

    @Test
    void getFilePath() {
    }

    @Test
    void getDirectoryInfoPath() {
    }

    @Test
    void getFiles() {
    }

    @Test
    void getFileInfoByFileInfoId() {
    }
}