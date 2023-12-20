package top.lldwb.file.saving.tool.client.netty.receive.file;

import cn.hutool.core.convert.Convert;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import top.lldwb.file.saving.tool.client.minio.FileMonitor;
import top.lldwb.file.saving.tool.client.minio.impl.FileMonitorImpl;
import top.lldwb.file.saving.tool.pojo.dto.SocketMessage;
import top.lldwb.file.saving.tool.pojo.entity.PathMapping;
import top.lldwb.file.saving.tool.service.control.ControlService;
import top.lldwb.file.saving.tool.service.minIO.MinIOSaveService;
import top.lldwb.file.saving.tool.service.send.SendService;

import java.util.HashMap;
import java.util.Map;

/**
 * 同步操作
 *
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/7
 * @time 11:42
 * @PROJECT_NAME file_saving_tool_backend
 */
@Service("synchronization")
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
@RequiredArgsConstructor
public class SynchronizationControl implements ControlService {
    private final MinIOSaveService minIOSaveService;

    private final SendService nettySend;
    /**
     * 存储<路径,监听管理构建器>容器
     */
    public static Map<String, FileMonitor> watchMonitorMap = new HashMap<>();

    @Override
    public void control(SocketMessage message) {
        PathMapping pathMapping = Convert.convert(PathMapping.class,message.getData());
        FileMonitor fileMonitor = new FileMonitorImpl(minIOSaveService,nettySend, pathMapping);
        watchMonitorMap.put(pathMapping.getPathMappingLocalPath(),fileMonitor);
        fileMonitor.start();
    }
}