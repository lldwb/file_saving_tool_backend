package top.lldwb.file.saving.tool.client.netty.receive;

import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import top.lldwb.file.saving.tool.client.minio.MinIOService;
import top.lldwb.file.saving.tool.service.control.ControlService;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * 自动下载
 *
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/7
 * @time 10:33
 * @PROJECT_NAME file_saving_tool_backend
 */
@Service("download")
@RequiredArgsConstructor
public class DownloadControl implements ControlService {
    private final MinIOService minIOService;

    @Override
    public void control(Map<String, Object> data) {
        for (String path:data.keySet()){
            try {
                minIOService.downloadFile(path, (String) data.get(path));
            } catch (ServerException e) {
                throw new RuntimeException(e);
            } catch (InsufficientDataException e) {
                throw new RuntimeException(e);
            } catch (ErrorResponseException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            } catch (InvalidKeyException e) {
                throw new RuntimeException(e);
            } catch (InvalidResponseException e) {
                throw new RuntimeException(e);
            } catch (XmlParserException e) {
                throw new RuntimeException(e);
            } catch (InternalException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
