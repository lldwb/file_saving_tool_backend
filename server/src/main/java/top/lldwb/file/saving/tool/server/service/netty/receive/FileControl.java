package top.lldwb.file.saving.tool.server.service.netty.receive;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import top.lldwb.file.saving.tool.pojo.entity.FileInfo;
import top.lldwb.file.saving.tool.pojo.entity.OperationLog;
import top.lldwb.file.saving.tool.server.dao.FileInfoDao;
import top.lldwb.file.saving.tool.server.dao.OperationLogDao;
import top.lldwb.file.saving.tool.server.service.minio.MinIOService;
import top.lldwb.file.saving.tool.service.control.ControlService;

import java.util.HashMap;
import java.util.Map;

/**
 * 接收客户端的文件操作进行归档
 *
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/7
 * @time 16:15
 * @PROJECT_NAME file_saving_tool_backend
 */
@Service("file")
@RequiredArgsConstructor
public class FileControl implements ControlService {
    private final MinIOService minIOService;
    private final OperationLogDao operationLogDao;
    private final FileInfoDao fileInfoDao;

    @Override
    public void control(Map<String, Object> data) {
        // 获取UUID
//        data.get("UUID");
        // 获取文件所在客户端路径
        data.get("path");
        // 获取操作类型service的方法名
        String controlType = (String) data.get("ControlType");
        FileInfo fileInfo = new FileInfo();
        fileInfo.setFileInfoPath((String) data.get("fileInfoPath"));
        fileInfo.setUserId((Integer) data.get("userId"));

        // 获取被操作的文件
        fileInfo = fileInfoDao.getFileInfoByPathANDUserId(fileInfo);
        OperationLog operationLog = getOperationLog(fileInfo);


        if ("deleteFile".equals(controlType)) {
            minIOService.deleteFile(fileInfo.getFileInfoId());
        } else {

        }

        if ("updateFile".equals(controlType)) {
            operationLogDao.addOperationLog(operationLog);
            minIOService.deleteFile(fileInfo.getFileInfoId());
        } else if ("addFile".equals(controlType)) {
            operationLogDao.addOperationLog(operationLog);
            minIOService.deleteFile(fileInfo.getFileInfoId());
        }

    }

    @NotNull
    private static OperationLog getOperationLog(FileInfo fileInfo) {
        OperationLog operationLog = new OperationLog();
        operationLog.setOperationLogName(fileInfo.getFileInfoName());
        operationLog.setOperationLogPath(fileInfo.getFileInfoPath());
        operationLog.setOperationLogSize(fileInfo.getFileInfoSize());
        operationLog.setUserId(fileInfo.getUserId());
        operationLog.setFileInfoId(fileInfo.getFileInfoId());
        return operationLog;
    }
}
