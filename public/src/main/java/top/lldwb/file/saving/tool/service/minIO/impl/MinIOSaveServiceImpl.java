package top.lldwb.file.saving.tool.service.minIO.impl;

import cn.hutool.crypto.digest.DigestUtil;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.StatObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.lldwb.file.saving.tool.config.MinIOConfig;
import top.lldwb.file.saving.tool.service.minIO.MinIOSaveService;

import java.io.*;

/**
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/19
 * @time 15:55
 * @PROJECT_NAME file_saving_tool_backend
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MinIOSaveServiceImpl implements MinIOSaveService {
    private final MinioClient minioClient;

    @Override
    public void saveMinIO(File file) {
        try {
            saveMinIO(new FileInputStream(file), file.length(),DigestUtil.sha256Hex(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveMinIO(MultipartFile multipartFile) {
        try {
            saveMinIO(multipartFile.getInputStream(), multipartFile.getSize(),DigestUtil.sha256Hex(multipartFile.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveMinIO(InputStream inputStream, Long size,String sha256Hex) {
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
                minioClient.putObject(PutObjectArgs.builder().bucket(MinIOConfig.BUCKET).object(sha256Hex).stream(inputStream, size, -1).build());
            } catch (Exception ex) {
                log.info("上传出错");
            }
        }
        log.info("结束");
    }
}
