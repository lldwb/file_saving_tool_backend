package top.lldwb.file.saving.tool.server.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.lldwb.file.saving.tool.server.pojo.doc.FileInfoDoc;
import top.lldwb.file.saving.tool.pojo.entity.FileInfo;
import top.lldwb.file.saving.tool.pojo.entity.User;
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
public class MinIOController extends BaseController {
    private final MinIOService service;

    /**
     * 上传文件如果有则覆盖
     *
     * @param multipartFile
     * @return
     */
    @PutMapping("/addFile")
    public ResultVO addFile(MultipartFile multipartFile,Integer directoryInfoId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        service.addFile(multipartFile,directoryInfoId, user.getUserId());
        return success();
    }

    /**
     * 删除文件
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
     * @param operationLogId 操作id
     * @return
     */
    @PutMapping("/recoverFile")
    public ResultVO recoverFile(Integer operationLogId){
        service.recoverFile(operationLogId);
        return success();
    }

    /**
     * 获取下载
     * @param path
     * @return
     */
    @GetMapping("/downloadFile/{path}")
    public ResultVO<ResponseEntity<InputStreamResource>> downloadFile(@PathVariable("path") String path) {
        return success(service.downloadFile(path));
    }

    /**
     * 获取文件列表
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

    @GetMapping("/getFileInfoByFileInfoId")
    public ResultVO<FileInfo> getFileInfoByFileInfoId(Integer fileInfoId) {
        return success(service.getFileInfoByFileInfoId(fileInfoId));
    }
}
