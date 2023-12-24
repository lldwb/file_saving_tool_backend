package top.lldwb.file.saving.tool.server.service.netty.receive.file;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.File;

/**
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/23
 * @time 10:55
 * @PROJECT_NAME file_saving_tool_backend
 */
@Slf4j
class SynchronizationFileControlTest {

    @Test
    void control() {
        String path  ="C:\\Users\\32471\\Downloads\\2434\\7 - 副本 - 副本\\自建vps";
        log.info("文件夹路径：{}",path);
//        String[] strings = path.split("\\");
        String separator = File.separator;
        String[] strings = path.split(separator + separator);
        String[] paths = new String[strings.length - 2];
        for (int i = 1; i < strings.length - 1; i++) {
            log.info(strings[i]);
            paths[i - 1] = strings[i];
        }
    }
}