package top.lldwb.file.saving.tool.server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import top.lldwb.file.saving.tool.pojo.entity.DirectoryInfo;
import top.lldwb.file.saving.tool.server.pojo.vo.ResultVO;
import top.lldwb.file.saving.tool.server.service.entity.DirectoryInfoService;

import java.util.List;

/**
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2024/1/3
 * @time 9:32
 * @PROJECT_NAME file_saving_tool_backend
 */
@RestController
@RequestMapping("/directoryInfo")
@RequiredArgsConstructor
public class DirectoryInfoController extends BaseController {
    private final DirectoryInfoService service;

    /**
     * 创建文件夹
     *
     * @param directoryInfo
     */
    @PutMapping("/add")
    public ResultVO add(DirectoryInfo directoryInfo) {
        service.add(directoryInfo);
        return success();
    }

    /**
     * 修改文件夹
     *
     * @param directoryInfo
     */
    @PostMapping("/update")
    public ResultVO update(DirectoryInfo directoryInfo) {
        service.update(directoryInfo);
        return success();
    }

    /**
     * 返回文件夹列表
     *
     * @param directoryInfoFatherId 父文件夹id
     * @param userId                用户id
     * @return
     */
    @GetMapping("/list")
    public ResultVO<List<DirectoryInfo>> list(Integer directoryInfoFatherId, Integer userId) {
        return success(service.list(directoryInfoFatherId, userId));
    }
}
