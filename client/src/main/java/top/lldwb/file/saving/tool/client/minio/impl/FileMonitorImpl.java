package top.lldwb.file.saving.tool.client.minio.impl;

import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import top.lldwb.file.saving.tool.client.minio.FileMonitor;
import top.lldwb.file.saving.tool.pojo.entity.PathMapping;
import top.lldwb.file.saving.tool.service.minIO.MinIOSaveService;
import top.lldwb.file.saving.tool.service.send.SendService;

import java.io.File;

/**
 * 监听管理构建器实现
 *
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/18
 * @time 10:06
 * @PROJECT_NAME file_saving_tool_backend
 */
public class FileMonitorImpl implements FileMonitor {
    /**
     * 一个可运行的对象，它生成一个监视线程，以指定的时间间隔触发任何已注册的FileAlterationObserver
     */
    private FileAlterationMonitor monitor;

    /**
     * 默认监听间隔为1000毫秒
     *
     * @param path 文件路径
     */
    public FileMonitorImpl(MinIOSaveService minIOSaveService,SendService nettySend, PathMapping pathMapping) {
//        this(minIOSaveService,nettySend, pathMapping, 1000);
        this(minIOSaveService,nettySend, pathMapping, 100);
    }

    /**
     * 创建监听管理构建器
     *
     * @param path     文件路径
     * @param interval 监听间隔
     */
    public FileMonitorImpl(MinIOSaveService minIOSaveService,SendService nettySend, PathMapping pathMapping, long interval) {
        this(pathMapping, interval, new FileListenerHandler(minIOSaveService,nettySend, pathMapping));
    }

    /**
     * 创建监听管理构建器
     *
     * @param path     文件路径
     * @param interval 监听间隔
     * @param listener 自定义文件监听处理器
     */
    public FileMonitorImpl(PathMapping pathMapping, long interval, FileAlterationListener listener) {
        monitor = new FileAlterationMonitor(interval);
        FileAlterationObserver observer = new FileAlterationObserver(new File(pathMapping.getPathMappingLocalPath()));
        observer.addListener(listener);
        monitor.addObserver(observer);
    }

    @Override
    public void start() {
        try {
            monitor.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void stop() {
        try {
            monitor.stop();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
