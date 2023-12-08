package top.lldwb.file.saving.tool.client.minio;

import io.minio.errors.*;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import top.lldwb.file.saving.tool.pojo.entity.FileInfo;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/7
 * @time 15:17
 * @PROJECT_NAME file_saving_tool_backend
 */
public interface MinIOService {
    /**
     * 下载并覆盖
     * @param localPath 本地路径
     * @param remotePath 远程路径(用于下载)
     */
    void downloadFile(String localPath,String remotePath) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;
//    /**
//     * 上传文件
//     *
//     * @param multipartFile
//     */
//    void addFile(MultipartFile multipartFile, Integer userId);
//
//    /**
//     * 删除文件
//     */
//    void deleteFile(Integer fileInfoId);
//
//    /**
//     * 恢复文件
//     *
//     * @param operationLogId 操作id
//     * @return
//     */
//    FileInfo recoverFile(Integer operationLogId);
//
//    /**
//     * 下载文件
//     *
//     * @param path
//     * @return
//     */
//    ResponseEntity<InputStreamResource> downloadFile(String path);
}
