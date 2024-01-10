package top.lldwb.file.saving.tool.server.dao;

import top.lldwb.file.saving.tool.pojo.entity.DirectoryInfo;

import java.util.List;

/**
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/12
 * @time 11:33
 * @PROJECT_NAME file_saving_tool_backend
 */
public interface DirectoryInfoDao {
    void addDirectoryInfo(DirectoryInfo directoryInfo);

    /**
     * 更新文件夹
     *
     * @param directoryInfo
     */
    void updateDirectoryInfo(DirectoryInfo directoryInfo);

    DirectoryInfo getDirectoryInfoById(Integer directoryInfoId);

    /**
     * 根据父文件夹id和文件夹名称查询文件夹信息
     *
     * @param directoryInfoFatherId
     * @param directoryInfoName
     * @return
     */
    DirectoryInfo getDirectoryInfoByFatherIdAndName(Integer directoryInfoFatherId, String directoryInfoName);

    /**
     * 根据父文件夹id查询父文件夹信息
     *
     * @param directoryInfoFatherId
     * @return
     */
    DirectoryInfo getDirectoryInfoFatherByDirectoryInfoFatherId(Integer directoryInfoFatherId);

    /**
     * 返回文件夹下面所有的子文件夹
     *
     * @param directoryInfoFatherId
     * @return
     */
    List<DirectoryInfo> listByDirectoryInfoFatherId(Integer directoryInfoFatherId);

    /**
     * 返回文件夹下面所有的子文件夹
     *
     * @param directoryInfoFatherId
     * @param userId
     * @return
     */
    List<DirectoryInfo> listByDirectoryInfoFatherIdAndUserId(Integer directoryInfoFatherId, Integer userId,Integer state);
}
