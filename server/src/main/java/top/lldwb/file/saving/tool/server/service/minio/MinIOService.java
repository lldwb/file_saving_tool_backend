package top.lldwb.file.saving.tool.server.service.minio;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import top.lldwb.file.saving.tool.server.pojo.doc.FileInfoDoc;
import top.lldwb.file.saving.tool.pojo.entity.FileInfo;

import java.util.List;

/**
 * MinIO 服务
 *
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/1
 * @time 8:47
 * @PROJECT_NAME file_saving_tool_backend
 */
public interface MinIOService {
    /**
     * 上传文件
     *
     * @param multipartFile
     */
    void addFile(MultipartFile multipartFile, Integer userId);

    /**
     * 删除文件
     *
     */
    void deleteFile(Integer fileInfoId);

    /**
     * 恢复文件
     * @param operationLogId 操作id
     * @return
     */
    FileInfo recoverFile(Integer operationLogId);

    /**
     * 下载文件
     *
     * @param path
     * @return
     */
    ResponseEntity<InputStreamResource> downloadFile(String path);

    /**
     * 获取文件列表
     * @param fileInfo
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<FileInfoDoc> getFiles(FileInfo fileInfo, Integer pageNum, Integer pageSize);

    /**
     * 根据ID获取文件
     *
     * @param fileInfoId
     * @return
     */
    FileInfo getFileInfoByFileInfoId(Integer fileInfoId);
}
