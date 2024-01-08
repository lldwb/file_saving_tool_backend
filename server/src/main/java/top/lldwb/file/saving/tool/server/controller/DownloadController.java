package top.lldwb.file.saving.tool.server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import top.lldwb.file.saving.tool.server.service.minio.MinIOService;

/**
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2024/1/8
 * @time 10:07
 * @PROJECT_NAME file_saving_tool_backend
 */
@Controller
@RequestMapping("/download")
@RequiredArgsConstructor
public class DownloadController {
    private final MinIOService service;

    /**
     * 获取下载
     *
     * @param path
     * @return
     */
    @GetMapping("/file/{path}")
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable("path") String path) {
        return service.downloadFile(path);
    }

    /**
     * 获取下载
     *
     * @param fileInfoId
     * @return
     */
    @GetMapping("/fileInfoId")
    public ResponseEntity<InputStreamResource> DownloadFileInfoId(Integer fileInfoId) {
        return service.downloadFileInfoId(fileInfoId);
    }
}
