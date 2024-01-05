package top.lldwb.file.saving.tool.server.service.entity.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import top.lldwb.file.saving.tool.pojo.entity.ShareLog;
import top.lldwb.file.saving.tool.server.dao.ShareLogDao;
import top.lldwb.file.saving.tool.server.service.entity.ShareLogService;

import java.util.List;

/**
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2024/1/5
 * @time 9:27
 * @PROJECT_NAME file_saving_tool_backend
 */
@Service
@RequiredArgsConstructor
public class ShareLogServiceImpl implements ShareLogService {
    private final ShareLogDao dao;

    @Override
    public void add(ShareLog shareLog) {
        dao.add(shareLog);
    }

    @Override
    public ShareLog getShareLogById(Integer shareLogId) {
        return dao.getShareLogById(shareLogId);
    }

    @Override
    public ShareLog getShareLogByFileInfoId(Integer fileInfoId) {
        return dao.getShareLogByFileInfoId(fileInfoId);
    }

    @Override
    public ShareLog getShareLogByDirectoryInfoId(Integer directoryInfoId) {
        return dao.getShareLogByDirectoryInfoId(directoryInfoId);
    }

    @Override
    public List<ShareLog> listShareLogsByUserId(Integer userId) {
        return dao.listShareLogsByUserId(userId);
    }

    @Override
    public List<ShareLog> listShareLogsByDirectoryInfoId(Integer directoryInfoId) {
        return dao.listShareLogsByDirectoryInfoId(directoryInfoId);
    }
}
