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
    DirectoryInfo getDirectoryInfoById(Integer directoryInfoId);
    DirectoryInfo getDirectoryInfoByFatherIdAndName(Integer directoryInfoFatherId,String directoryInfoName);

    /**
     * 返回文件夹下面所有的子文件夹
     * @param directoryInfoFatherId
     * @return
     */
    List<DirectoryInfo> listByDirectoryInfoFatherId(Integer directoryInfoFatherId);
}
