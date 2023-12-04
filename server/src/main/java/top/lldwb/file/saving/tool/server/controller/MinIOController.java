package top.lldwb.file.saving.tool.server.controller;

import io.minio.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.lldwb.file.saving.tool.server.config.MinIOConfig;
import top.lldwb.file.saving.tool.server.pojo.doc.FileInfoDoc;
import top.lldwb.file.saving.tool.server.pojo.entity.FileInfo;
import top.lldwb.file.saving.tool.server.pojo.entity.Magic;
import top.lldwb.file.saving.tool.server.pojo.entity.PathMapping;
import top.lldwb.file.saving.tool.server.pojo.entity.User;
import top.lldwb.file.saving.tool.server.pojo.vo.ResultVO;
import top.lldwb.file.saving.tool.server.service.minio.MinIOService;

import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
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
    private final MinioClient minioClient;
    private final MinIOService service;

    /**
     * 上传文件如果有则覆盖
     *
     * @param multipartFile
     * @return
     * @throws Exception
     */
    @PostMapping("/addFile")
    public ResultVO addFile(MultipartFile multipartFile, HttpSession session) throws Exception {
        User user = (User) session.getAttribute("user");
        service.addFile(multipartFile, user.getUserId());
        return success();
    }

    @DeleteMapping("/deleteFile")
    public ResultVO deleteFile(Integer fileInfoId) {
        service.deleteFile(fileInfoId);
        return success();
    }

    @GetMapping("/downloadFile/{path}")
    public ResultVO<ResponseEntity<InputStreamResource>> downloadFile(@PathVariable("path") String path) throws Exception {
        return success(service.downloadFile(path));
    }

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
