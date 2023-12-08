package top.lldwb.file.saving.tool.server.service.netty.receive;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import top.lldwb.file.saving.tool.pojo.entity.OperationLog;
import top.lldwb.file.saving.tool.server.dao.OperationLogDao;
import top.lldwb.file.saving.tool.server.service.minio.MinIOService;
import top.lldwb.file.saving.tool.service.control.ControlService;

import java.util.HashMap;
import java.util.Map;

/**
 * 接收客户端的文件操作进行归档
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
    @Override
    public void control(Map<String, Object> data) {
        // 获取UUID
//        data.get("UUID");
        // 获取文件所在客户端路径
        data.get("path");
        // 获取操作类型service的方法名
        String controlType = (String) data.get("ControlType");
        OperationLog operationLog =operationLogDao.getOperationLogByOperationLogId(Integer.valueOf((String) data.get("pathMappingId")));
        if ("addFile".equals(controlType)){
//            minIOService.addFile();
        }else if ("deleteFile".equals(controlType)){
//            minIOService.deleteFile();
        }
    }
}
