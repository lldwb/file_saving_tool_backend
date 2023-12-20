package top.lldwb.file.saving.tool.client.minio;

import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;

/**
 * 监听管理构建器
 *
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/18
 * @time 10:09
 * @PROJECT_NAME file_saving_tool_backend
 */
public interface FileMonitor {
    /**
     * 开始监听
     */

    public void start();

    /**
     * 结束监听
     */
    public void stop();
}
