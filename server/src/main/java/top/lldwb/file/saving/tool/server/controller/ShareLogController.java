package top.lldwb.file.saving.tool.server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lldwb.file.saving.tool.pojo.entity.ShareLog;
import top.lldwb.file.saving.tool.server.pojo.vo.ResultVO;
import top.lldwb.file.saving.tool.server.service.entity.ShareLogService;

import java.util.List;

/**
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2024/1/5
 * @time 9:31
 * @PROJECT_NAME file_saving_tool_backend
 */
@RestController
@RequestMapping("/shareLog")
@RequiredArgsConstructor
public class ShareLogController extends BaseController {
   private final ShareLogService service;

    /**
     * 添加分享日志
     * @param shareLog 分享日志
     * @return 添加成功返回成功信息
     */
    @PutMapping("/add")
    public ResultVO add(ShareLog shareLog) {
        service.add(shareLog);
        return success();
    }

    /**
     * 根据分享日志ID获取分享日志
     * @param shareLogId 分享日志ID
     * @return 获取成功返回分享日志信息
     */
    @GetMapping("/getShareLogById")
    public ResultVO getShareLogById(Integer shareLogId) {
        return success(service.getShareLogById(shareLogId));
    }

    /**
     * 根据文件信息ID获取分享日志
     * @param fileInfoId 文件信息ID
     * @return 获取成功返回分享日志信息
     */
    @GetMapping("/getShareLogByFileInfoId")
    public ResultVO getShareLogByFileInfoId(Integer fileInfoId) {
        return success(service.getShareLogByFileInfoId(fileInfoId));
    }

    /**
     * 根据目录信息ID获取分享日志
     * @param directoryInfoId 目录信息ID
     * @return 获取成功返回分享日志信息
     */
    @GetMapping("/getShareLogByDirectoryInfoId")
    public ResultVO getShareLogByDirectoryInfoId(Integer directoryInfoId) {
        return success(service.getShareLogByDirectoryInfoId(directoryInfoId));
    }

    /**
     * 根据用户ID获取分享日志列表
     * @param userId 用户ID
     * @return 获取成功返回分享日志列表
     */
    @GetMapping("/listShareLogsByUserId")
    public ResultVO<List<ShareLog>> listShareLogsByUserId(Integer userId) {
        return success(service.listShareLogsByUserId(userId));
    }

    /**
     * 根据目录信息ID获取分享日志列表
     * @param directoryInfoId 目录信息ID
     * @return 获取成功返回分享日志列表
     */
    @GetMapping("/listShareLogsByDirectoryInfoId")
    public ResultVO<List<ShareLog>> listShareLogsByDirectoryInfoId(Integer directoryInfoId) {
        return success(service.listShareLogsByDirectoryInfoId(directoryInfoId));
    }
}
