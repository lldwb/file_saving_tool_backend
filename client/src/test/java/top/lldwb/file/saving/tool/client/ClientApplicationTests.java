package top.lldwb.file.saving.tool.client;

import io.minio.MinioClient;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.lldwb.file.saving.tool.service.minIO.impl.MinIOSaveServiceImpl;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@SpringBootTest
class ClientApplicationTests {
    @Autowired
    private MinioClient minioClient;
    @Test
    void contextLoads() {
    }

    @Autowired
    private MinIOSaveServiceImpl minIOSaveService;
    @Test
    void saveMinIO() throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        File file = new File("C:\\Users\\32471\\Downloads\\2434\\12414 - 副本.txt");
        minIOSaveService.saveMinIO(file);
        // 上传文件到Minio
//        minioClient.putObject(PutObjectArgs.builder().bucket(MinIOConfig.BUCKET).object(DigestUtil.sha256Hex(file)).stream(new FileInputStream(file), file.length(), -1).build());
    }

}
