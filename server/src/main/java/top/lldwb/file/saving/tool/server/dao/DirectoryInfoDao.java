package top.lldwb.file.saving.tool.server.dao;

import top.lldwb.file.saving.tool.pojo.entity.DirectoryInfo;

/**
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/12
 * @time 11:33
 * @PROJECT_NAME file_saving_tool_backend
 */
public interface DirectoryInfoDao {
    DirectoryInfo getDirectoryInfoById(Integer directoryInfoId);
}
