package top.lldwb.file.saving.tool.client.netty.receive.file;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.watch.WatchMonitor;
import cn.hutool.core.io.watch.Watcher;
import cn.hutool.core.lang.Console;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import top.lldwb.file.saving.tool.client.minio.FileMonitor;
import top.lldwb.file.saving.tool.client.minio.impl.FileMonitorImpl;
import top.lldwb.file.saving.tool.pojo.dto.PathSocketMessage;
import top.lldwb.file.saving.tool.pojo.dto.SocketMessage;
import top.lldwb.file.saving.tool.pojo.entity.DirectoryInfo;
import top.lldwb.file.saving.tool.pojo.entity.FileInfo;
import top.lldwb.file.saving.tool.pojo.entity.PathMapping;
import top.lldwb.file.saving.tool.service.control.ControlService;
import top.lldwb.file.saving.tool.service.minIO.MinIOSaveService;
import top.lldwb.file.saving.tool.service.send.SendService;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.util.List;
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
    public static MinIOSaveService minIOSaveService;

    @Autowired
    public static void setMinIOSaveService(MinIOSaveService minIOSaveService) {
        SynchronizationControl.minIOSaveService = minIOSaveService;
    }

    private final SendService nettySend;
    /**
     * 存储<路径,监听管理构建器>容器
     */
    public static Map<String, FileMonitor> watchMonitorMap;

    @Override
    public void control(SocketMessage message) {
        PathSocketMessage pathSocketMessage = Convert.convert(PathSocketMessage.class, message);
        FileMonitor fileMonitor = new FileMonitorImpl(nettySend, pathSocketMessage.getPathMapping());

//        File file = FileUtil.file((String) data.get("path"));

    }
}