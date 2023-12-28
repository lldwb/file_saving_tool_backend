package top.lldwb.file.saving.tool.client.minio.impl;

import io.minio.DownloadObjectArgs;
import io.minio.MinioClient;
import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import top.lldwb.file.saving.tool.client.minio.MinIOService;
import top.lldwb.file.saving.tool.config.MinIOConfig;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/7
 * @time 15:17
 * @PROJECT_NAME file_saving_tool_backend
 */
@Service
@RequiredArgsConstructor
public class MinIOServiceImpl implements MinIOService {
    private final MinioClient minioClient;
    @Override
    public void downloadFile(String localPath, String remotePath) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        // 删除原有文件
        File file = new File(localPath);
        if (file.isFile()){
            file.delete();
        }
        minioClient.downloadObject(DownloadObjectArgs.builder().bucket(MinIOConfig.BUCKET).object(remotePath).filename(localPath).build());
    }
}
