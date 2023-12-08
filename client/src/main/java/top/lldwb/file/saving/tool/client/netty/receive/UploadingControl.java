package top.lldwb.file.saving.tool.client.netty.receive;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.watch.WatchMonitor;
import cn.hutool.core.io.watch.Watcher;
import cn.hutool.core.lang.Console;
import top.lldwb.file.saving.tool.service.control.ControlService;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.util.Map;

/**
 * 自动上传设置操作
 *
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/7
 * @time 11:42
 * @PROJECT_NAME file_saving_tool_backend
 */
public class UploadingControl implements ControlService {
    @Override
    public void control(Map<String, Object> data) {

        File file = FileUtil.file((String) data.get("path"));
        Integer pathMappingId = Integer.valueOf((String) data.get("pathMappingId"));
        //这里只监听文件或目录的修改事件
        WatchMonitor watchMonitor = WatchMonitor.create(file, WatchMonitor.ENTRY_MODIFY);
        watchMonitor.setWatcher(new Watcher() {
            @Override
            public void onCreate(WatchEvent<?> event, Path currentPath) {
                Object obj = event.context();
                Console.log("创建：{}-> {}", currentPath, obj);
            }

            @Override
            public void onModify(WatchEvent<?> event, Path currentPath) {
                Object obj = event.context();
                Console.log("修改：{}-> {}", currentPath, obj);
            }

            @Override
            public void onDelete(WatchEvent<?> event, Path currentPath) {
                Object obj = event.context();
                Console.log("删除：{}-> {}", currentPath, obj);
            }

            // 事件丢失或出错时执行的方法
            @Override
            public void onOverflow(WatchEvent<?> event, Path currentPath) {
                Object obj = event.context();
                Console.log("Overflow：{}-> {}", currentPath, obj);
            }
        });

        //设置监听目录的最大深入，目录层级大于制定层级的变更将不被监听，默认只监听当前层级目录
        watchMonitor.setMaxDepth(Integer.MAX_VALUE);
        //启动监听
        watchMonitor.start();
//        watchMonitor.close();
    }
}