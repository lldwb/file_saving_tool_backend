package top.lldwb.file.saving.tool.server.service.entity.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import top.lldwb.file.saving.tool.pojo.entity.FileInfo;
import top.lldwb.file.saving.tool.server.dao.FileInfoDao;
import top.lldwb.file.saving.tool.server.service.entity.FileInfoService;

import java.util.List;

/**
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2024/1/3
 * @time 15:15
 * @PROJECT_NAME file_saving_tool_backend
 */
@Service
@RequiredArgsConstructor
public class FileInfoServiceImpl implements FileInfoService {
    private final FileInfoDao dao;

    @Override
    public FileInfo getId(Integer fileInfoId) {
        return dao.getFileInfoByFileInfoId(fileInfoId);
    }

    @Override
    public List<FileInfo> list(Integer directoryInfoId, Integer userId) {
        return dao.listByDirectoryInfoIdAndUserId(directoryInfoId, userId);
    }

    @Override
    public FileInfo copyFile(Integer fileInfoId) {
        FileInfo fileInfo = dao.getFileInfoByFileInfoId(fileInfoId);
        fileInfo.setFileInfoName("副本" + fileInfo.getFileInfoName());
        fileInfo.setFileInfoId(null);
        dao.addFileInfo(fileInfo);
        return fileInfo;
    }

    @Override
    public void update(FileInfo fileInfo) {
        dao.updateFileInfo(fileInfo);
    }
}
