package top.lldwb.file.saving.tool.server.dao;

import top.lldwb.file.saving.tool.server.pojo.entity.FileInfo;
import top.lldwb.file.saving.tool.server.pojo.entity.User;

import java.util.List;

public interface FileInfoDao {
    /**
     * 根据用户ID获取用户
     *
     * @param fileInfoId
     * @return
     */
    User getFileInfoByFileInfoId(Integer fileInfoId);

    /**
     * 获取所有用户
     *
     * @return
     */
    List<FileInfo> getFileInfos();

    /**
     * 添加用户
     *
     * @param fileInfo
     */
    void addFileInfo(FileInfo fileInfo);

    /**
     * 更新用户
     *
     * @param fileInfo
     */
    void updateFileInfo(FileInfo fileInfo);
}
