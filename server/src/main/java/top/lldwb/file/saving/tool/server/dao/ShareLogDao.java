package top.lldwb.file.saving.tool.server.dao;

import top.lldwb.file.saving.tool.pojo.entity.ShareLog;

import java.util.List;

/**
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2024/1/5
 * @time 8:55
 * @PROJECT_NAME file_saving_tool_backend
 */
public interface ShareLogDao {
    void add(ShareLog shareLog);
    void update(ShareLog shareLog);
    ShareLog getShareLogById(Integer shareLogId);
    ShareLog getShareLogByFileInfoId(Integer fileInfoId);
    ShareLog getShareLogByDirectoryInfoId(Integer directoryInfoId);
    List<ShareLog> listShareLogsByUserId(Integer userId);
    List<ShareLog> listShareLogsByDirectoryInfoId(Integer directoryInfoId);

}