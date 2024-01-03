package top.lldwb.file.saving.tool.server.service.file;

import top.lldwb.file.saving.tool.pojo.entity.FileInfo;

import java.util.List;

/**
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2024/1/3
 * @time 15:15
 * @PROJECT_NAME file_saving_tool_backend
 */
public interface FileInfoService {
    FileInfo getId(Integer fileInfoId);

    /**
     * 根据文件夹id获取文件集合
     * @param directoryInfoId
     * @param userId
     * @return
     */
    List<FileInfo> list(Integer directoryInfoId, Integer userId);
}
