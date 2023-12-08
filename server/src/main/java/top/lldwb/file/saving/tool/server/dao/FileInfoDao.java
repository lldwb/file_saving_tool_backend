package top.lldwb.file.saving.tool.server.dao;

import top.lldwb.file.saving.tool.pojo.entity.FileInfo;

import java.util.List;

public interface FileInfoDao {
    /**
     * 根据ID获取文件
     *
     * @param fileInfoId
     * @return
     */
    FileInfo getFileInfoByFileInfoId(Integer fileInfoId);

    /**
     * 根据路径和用户ID获取FileInfo
     *
     * @param fileInfo
     * @return
     */
    FileInfo getFileInfoByPathANDUserId(FileInfo fileInfo);

    /**
     * 根据路径(路径/**)和用户ID获取FileInfo集合
     * @param fileInfoPath
     * @param userId
     * @return
     */
    List<FileInfo> getFileInfoByPathANDUserIds(String fileInfoPath, Integer userId);

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
