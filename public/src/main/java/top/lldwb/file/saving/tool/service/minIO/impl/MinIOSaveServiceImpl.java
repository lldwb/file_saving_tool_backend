package top.lldwb.file.saving.tool.service.minIO.impl;

import cn.hutool.crypto.digest.DigestUtil;
import io.minio.IsObjectLegalHoldEnabledArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.lldwb.file.saving.tool.config.MinIOConfig;
import top.lldwb.file.saving.tool.service.minIO.MinIOSaveService;

import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/19
 * @time 15:55
 * @PROJECT_NAME file_saving_tool_backend
 */
@Service
@RequiredArgsConstructor
public class MinIOSaveServiceImpl implements MinIOSaveService {
    private final MinioClient minioClient;

    @Override
    public void saveMinIO(File file) {
        try {
            saveMinIO(new FileInputStream(file),file.length());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveMinIO(MultipartFile multipartFile) {
        try {
            saveMinIO(multipartFile.getInputStream(),multipartFile.getSize());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void saveMinIO(InputStream inputStream, Long size) {
        String sha256Hex = DigestUtil.sha256Hex(inputStream);
        try {
            // 检测是否已经存在，如果存在则不上传
            minioClient.isObjectLegalHoldEnabled(IsObjectLegalHoldEnabledArgs.builder().bucket(MinIOConfig.BUCKET).object(sha256Hex).build());
        } catch (Exception e) {
            try {
                // 上传文件到Minio
                minioClient.putObject(PutObjectArgs.builder().bucket(MinIOConfig.BUCKET).object(sha256Hex).stream(inputStream,size,-1).build());
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
    }
}
