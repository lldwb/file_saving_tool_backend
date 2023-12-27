package top.lldwb.file.saving.tool.client.netty.receive.file;

import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.lldwb.file.saving.tool.client.minio.MinIOService;
import top.lldwb.file.saving.tool.pojo.dto.SocketMessage;
import top.lldwb.file.saving.tool.pojo.entity.PathMapping;

/**
 * 首次下载
 *
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/7
 * @time 10:33
 * @PROJECT_NAME file_saving_tool_backend
 */
@Slf4j
@Service("firstDownload")
public class FirstDownloadControl extends DownloadControl {
    @Autowired
    public FirstDownloadControl(MinIOService minIOService) {
        super(minIOService);
    }

    @Override
    public void control(SocketMessage message) {
        log.info("首次下载");
        String[] strings = Convert.convert(String[].class, message.getData());
        PathMapping pathMapping = JSONUtil.toBean(strings[0], PathMapping.class);
        super.control(message);
        // 启动
        SynchronizationControl.watchMonitorMap.get(pathMapping.getPathMappingLocalPath()).start();
    }
}
