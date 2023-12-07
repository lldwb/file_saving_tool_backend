package top.lldwb.file.saving.tool.server.dao;

import top.lldwb.file.saving.tool.server.pojo.entity.FileInfo;

import java.util.List;

public interface FileInfoDao {
    /**
     * 根据ID获取文件
     *
     * @param fileInfoId
     * @return
     */
    FileInfo getFileInfoByFileInfoId(Integer fileInfoId);

    FileInfo getFileInfoByPathANDUserId(FileInfo fileInfo);

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
