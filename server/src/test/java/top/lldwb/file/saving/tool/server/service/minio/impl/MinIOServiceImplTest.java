package top.lldwb.file.saving.tool.server.service.minio.impl;

import cn.hutool.crypto.digest.DigestUtil;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.lldwb.file.saving.tool.config.MinIOConfig;

import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/19
 * @time 11:32
 * @PROJECT_NAME file_saving_tool_backend
 */
@SpringBootTest
class MinIOServiceImplTest {
    @Autowired
    private MinioClient minioClient;

    @Test
    void addFile() throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        File file = new File("C:\\Users\\32471\\Downloads\\自建vps");
        String sha256Hex = DigestUtil.sha256Hex(file);
        InputStream inputStream = new FileInputStream(file);
        // 检测是否已经存在，如果存在则不上传
        System.out.println("检测是否已经存在，如果存在则不上传");
        try {
            // 检测是否已经存在，如果存在则不上传
            minioClient.getObject(GetObjectArgs.builder().bucket(MinIOConfig.BUCKET).object(sha256Hex).build());
        } catch (Exception e) {
            try {
                System.out.println("上传文件到Minio");
                // 上传文件到Minio
                minioClient.putObject(PutObjectArgs.builder().bucket(MinIOConfig.BUCKET).object(sha256Hex).stream(inputStream,file.length(),-1).build());
            } catch (ErrorResponseException ex) {
                throw new RuntimeException(ex);
            } catch (InsufficientDataException ex) {
                throw new RuntimeException(ex);
            } catch (InternalException ex) {
                throw new RuntimeException(ex);
            } catch (InvalidKeyException ex) {
                throw new RuntimeException(ex);
            } catch (InvalidResponseException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (NoSuchAlgorithmException ex) {
                throw new RuntimeException(ex);
            } catch (ServerException ex) {
                throw new RuntimeException(ex);
            } catch (XmlParserException ex) {
                throw new RuntimeException(ex);
            }
        }
        System.out.println("结束");
    }

    @Test
    void testAddFile() {
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