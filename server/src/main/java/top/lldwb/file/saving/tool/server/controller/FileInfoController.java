package top.lldwb.file.saving.tool.server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lldwb.file.saving.tool.server.pojo.vo.ResultVO;
import top.lldwb.file.saving.tool.server.service.entity.FileInfoService;

/**
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2024/1/3
 * @time 15:13
 * @PROJECT_NAME file_saving_tool_backend
 */
@RestController
@RequestMapping("/fileInfo")
@RequiredArgsConstructor
public class FileInfoController extends BaseController {
    private final FileInfoService service;

    /**
     * 根据id获取
     *
     * @param fileInfoId
     * @return
     */

    @GetMapping("/getId")
    public ResultVO getId(Integer fileInfoId) {
        return success(service.getId(fileInfoId));
    }

    /**
     * 根据文件夹id获取文件集合
     *
     * @param directoryInfoId
     * @param userId
     * @return
     */
    @GetMapping("/list")
    public ResultVO list(Integer directoryInfoId, Integer userId) {
        return success(service.list(directoryInfoId, userId));
    }

    /**
     * 复制文件
     *
     * @param fileInfoId
     * @return
     */
    @PutMapping("/copyFile")
    public ResultVO copyFile(Integer fileInfoId) {
        return success(service.copyFile(fileInfoId));
    }
}
