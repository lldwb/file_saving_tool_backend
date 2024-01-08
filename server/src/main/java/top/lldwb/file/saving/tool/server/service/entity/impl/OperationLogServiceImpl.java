package top.lldwb.file.saving.tool.server.service.entity.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import top.lldwb.file.saving.tool.pojo.entity.OperationLog;
import top.lldwb.file.saving.tool.server.dao.OperationLogDao;
import top.lldwb.file.saving.tool.server.service.entity.OperationLogService;

import java.util.List;

/**
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2024/1/5
 * @time 11:31
 * @PROJECT_NAME file_saving_tool_backend
 */
@Service
@RequiredArgsConstructor
public class OperationLogServiceImpl implements OperationLogService {
    private final OperationLogDao dao;

    @Override
    public List<OperationLog> listOperationLogByUserId(Integer userId, Integer pageNum, Integer pageSize) {
        return dao.listOperationLogByUserId(userId, pageNum, pageSize);
    }
}
