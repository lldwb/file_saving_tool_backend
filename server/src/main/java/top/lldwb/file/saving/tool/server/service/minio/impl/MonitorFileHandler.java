package top.lldwb.file.saving.tool.server.service.minio.impl;

import lombok.RequiredArgsConstructor;
import top.lldwb.file.saving.tool.pojo.entity.PathMapping;
import top.lldwb.file.saving.tool.service.send.SendService;

/**
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/21
 * @time 20:27
 * @PROJECT_NAME file_saving_tool_backend
 */
@RequiredArgsConstructor
public class MonitorFileHandler {
    private final SendService nettySend;
    /**
     * 用于判断操作的是哪个路径
     */
    private final PathMapping pathMapping;

}
