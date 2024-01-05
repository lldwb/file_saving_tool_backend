package top.lldwb.file.saving.tool.server.service.entity.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import top.lldwb.file.saving.tool.pojo.entity.DirectoryInfo;
import top.lldwb.file.saving.tool.server.dao.DirectoryInfoDao;
import top.lldwb.file.saving.tool.server.service.entity.DirectoryInfoService;

import java.util.List;

/**
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2024/1/3
 * @time 9:34
 * @PROJECT_NAME file_saving_tool_backend
 */
@Service
@RequiredArgsConstructor
public class DirectoryInfoServiceImpl implements DirectoryInfoService {
    private final DirectoryInfoDao dao;

    @Override
    public void add(DirectoryInfo directoryInfo) {
        dao.addDirectoryInfo(directoryInfo);
    }

    @Override
    public void update(DirectoryInfo directoryInfo) {
        dao.updateDirectoryInfo(directoryInfo);
    }

    @Override
    public List<DirectoryInfo> list(Integer directoryInfoFatherId, Integer userId) {
        return dao.listByDirectoryInfoFatherIdAndUserId(directoryInfoFatherId, userId);
    }
}
