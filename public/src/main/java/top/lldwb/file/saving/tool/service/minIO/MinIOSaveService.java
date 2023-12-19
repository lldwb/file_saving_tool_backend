package top.lldwb.file.saving.tool.service.minIO;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;

/**
 * 负责操作minio的业务接口
 *
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/19
 * @time 15:52
 * @PROJECT_NAME file_saving_tool_backend
 */
public interface MinIOSaveService {
    void saveMinIO(File file);

    void saveMinIO(MultipartFile multipartFile);

    /**
     * 保存到minIO中
     *
     * @param inputStream 文件输入流
     * @param size        文件长度
     */
    void saveMinIO(InputStream inputStream, Long size);
}
