package top.lldwb.file.saving.tool.client.netty.receive.file;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

/**
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/27
 * @time 11:18
 * @PROJECT_NAME file_saving_tool_backend
 */
@SpringBootTest
class DownloadControlTest {

    @Test
    void control() {
        new File("C:\\Users\\32471\\Downloads\\新建文件\\88").mkdirs();
    }

    @Test
    void testControl() {
    }
}