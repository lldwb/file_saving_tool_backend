package top.lldwb.file.saving.tool.server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lldwb.file.saving.tool.server.pojo.vo.ResultVO;
import top.lldwb.file.saving.tool.server.service.entity.OperationLogService;

/**
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2024/1/5
 * @time 11:30
 * @PROJECT_NAME file_saving_tool_backend
 */
@RestController
@RequestMapping("/operationLog")
@RequiredArgsConstructor
public class OperationLogController extends BaseController {
    private final OperationLogService service;

    /**
     * 根据用户ID获取操作日志列表
     * @param userId
     * @return
     */
    @GetMapping("/listOperationLogByUserId")
    public ResultVO listOperationLogByUserId(Integer userId, Integer pageNum, Integer pageSize) {
        return success(service.listOperationLogByUserId(userId, pageNum, pageSize));
    }
}
