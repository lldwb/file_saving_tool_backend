package top.lldwb.file.saving.tool.server.service.minio;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import top.lldwb.file.saving.tool.pojo.entity.DirectoryInfo;
import top.lldwb.file.saving.tool.pojo.entity.FileInfo;
import top.lldwb.file.saving.tool.server.pojo.doc.FileInfoDoc;

import java.io.InputStream;
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
    void addFile(MultipartFile multipartFile, Integer directoryInfoId, Integer userId);

    /**
     * 添加文件(用于内部)
     *
     * @param fileInfo
     */
    void addFile(FileInfo fileInfo);

    /**
     * 添加文件夹
     *
     * @param directoryInfo
     */
    void addDirectoryInfo(DirectoryInfo directoryInfo);

    /**
     * 修改文件夹
     *
     * @param directoryInfo
     */
    void updateDirectoryInfo(DirectoryInfo directoryInfo);

    /**
     * 删除文件夹
     *
     * @param directoryInfoId
     */
    void deleteDirectoryInfo(Integer directoryInfoId);

    /**
     * 恢复文件
     *
     * @param operationLogId 操作id
     * @return
     */
    FileInfo recoverFile(Integer operationLogId);

    /**
     * 下载文件
     *
     * @param path minio路径
     * @return
     */
    ResponseEntity<InputStreamResource> downloadFile(String path);

    /**
     * 下载文件
     *
     * @param fileInfoId 文件Id
     * @return
     */
    ResponseEntity<InputStreamResource> downloadFileInfoId(Integer fileInfoId);

    /**
     * 下载文件到本地
     *
     * @param name 完整文件名称(包括路径)
     * @param path minio连接
     */
    void downloadFile(String name, String path);

    /**
     * 获取文件路径
     *
     * @param fileInfoId
     * @return
     */
    String getFilePath(Integer fileInfoId);

    /**
     * 获取文件夹路径
     *
     * @param directoryInfoId
     * @return
     */
    String getDirectoryInfoPath(Integer directoryInfoId);

    /**
     * 获取文件列表
     *
     * @param fileInfo (推荐)使用文件夹id返回所在文件夹所有文件
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<FileInfoDoc> getFiles(FileInfo fileInfo, Integer pageNum, Integer pageSize);

    /**
     * 返回文件夹所在的文件和文件夹列表
     *
     * @param directoryInfoId
     * @param userId
     * @return
     */
    List<DirectoryInfo> listDirectoryInfo(Integer directoryInfoId, Integer userId);

    /**
     * 根据路径返回文件夹所在的文件和文件夹列表
     *
     * @param path
     * @param userId
     * @return
     */
    List<DirectoryInfo> listDirectoryInfoByPath(String path,  Integer userId);

    /**
     * 根据ID获取文件
     *
     * @param fileInfoId
     * @return
     */
    FileInfo getFileInfoByFileInfoId(Integer fileInfoId);

    void saveMinIO(MultipartFile multipartFile);

    /**
     * 保存到minIO中
     *
     * @param inputStream 文件输入流
     * @param size        文件长度
     */
    void saveMinIO(InputStream inputStream,String sha256Hex, Long size);

    /**
     * 刷新es数据
     */
    void refresh();
}
