package top.lldwb.file.saving.tool.server.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.lldwb.file.saving.tool.pojo.entity.DirectoryInfo;
import top.lldwb.file.saving.tool.pojo.entity.FileInfo;
import top.lldwb.file.saving.tool.pojo.entity.User;
import top.lldwb.file.saving.tool.server.controller.common.BaseResponse;
import top.lldwb.file.saving.tool.server.pojo.doc.FileInfoDoc;
import top.lldwb.file.saving.tool.server.pojo.vo.ResultVO;
import top.lldwb.file.saving.tool.server.service.minio.MinIOService;

import java.util.List;

/**
 * minion接口
 *
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/11/30
 * @time 19:16
 * @PROJECT_NAME file_saving_tool_backend
 */
@RestController
@RequestMapping("/minio")
@RequiredArgsConstructor
public class MinIOController extends BaseResponse {
    private final MinIOService service;

    /**
     * 上传文件如果有则覆盖
     *
     * @param multipartFile
     * @return
     */
    @PutMapping("/addFile")
    public ResultVO addFile(MultipartFile multipartFile, Integer directoryInfoId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        user = new User();
        user.setUserId(1);
        service.addFile(multipartFile, directoryInfoId, user.getUserId());
        return success();
    }

    /**
     * 删除文件
     *
     * @param fileInfoId 文件id
     * @return
     */
    @DeleteMapping("/deleteFile")
    public ResultVO deleteFile(Integer fileInfoId) {
        service.deleteFile(fileInfoId);
        return success();
    }

    /**
     * 恢复文件
     *
     * @param operationLogId 操作id
     * @return
     */
    @PutMapping("/recoverFile")
    public ResultVO recoverFile(Integer operationLogId) {
        service.recoverFile(operationLogId);
        return success();
    }

    /**
     * 返回搜索到的文件列表
     *
     * @param fileInfo
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/getFiles")
    public ResultVO<List<FileInfoDoc>> getFiles(FileInfo fileInfo, Integer pageNum, Integer pageSize) {
        // 返回文件列表
        return success(service.getFiles(fileInfo, pageNum, pageSize));
    }


    /**
     * 返回文件夹所在的文件和文件夹列表
     *
     * @param directoryInfoId
     * @param userId
     * @return
     */
    @GetMapping("/listDirectoryInfo")
    public ResultVO<List<DirectoryInfo>> listDirectoryInfo(Integer directoryInfoId, Integer userId) {
        return success(service.listDirectoryInfo(directoryInfoId, userId));
    }

    /**
     * 根据路径返回文件夹所在的文件和文件夹列表
     *
     * @param path
     * @param userId
     * @return
     */
    @GetMapping("/listDirectoryInfoByPath")
    public ResultVO<List<DirectoryInfo>> listDirectoryInfoByPath(String path, Integer userId) {
        return success(service.listDirectoryInfoByPath(path, userId));
    }

    /**
     * 返回文件路径
     *
     * @param fileInfoId
     * @return
     */
    @GetMapping("/getFileInfoByFileInfoId")
    public ResultVO<FileInfo> getFileInfoByFileInfoId(Integer fileInfoId) {
        return success(service.getFileInfoByFileInfoId(fileInfoId));
    }

    /**
     * 刷新es数据
     *
     * @return
     */
    @PutMapping("/refresh")
    public ResultVO refresh() {
        service.refresh();
        return success();
    }
}
