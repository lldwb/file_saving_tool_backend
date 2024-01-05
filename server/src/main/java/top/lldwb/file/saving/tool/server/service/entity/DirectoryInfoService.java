package top.lldwb.file.saving.tool.server.service.entity;

import top.lldwb.file.saving.tool.pojo.entity.DirectoryInfo;

import java.util.List;

/**
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2024/1/3
 * @time 9:34
 * @PROJECT_NAME file_saving_tool_backend
 */
public interface DirectoryInfoService {
    /**
     * 创建文件夹
     * @param directoryInfo
     */
    void add(DirectoryInfo directoryInfo);

    /**
     * 修改文件夹
     * @param directoryInfo
     */
    void update(DirectoryInfo directoryInfo);

    /**
     * 返回文件夹列表
     * @param directoryInfoFatherId 父文件夹id
     * @param userId 用户id
     * @return
     */
    List<DirectoryInfo> list(Integer directoryInfoFatherId,Integer userId);
}
