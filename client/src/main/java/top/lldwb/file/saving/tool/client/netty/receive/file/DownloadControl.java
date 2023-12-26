package top.lldwb.file.saving.tool.client.netty.receive.file;

import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import top.lldwb.file.saving.tool.client.minio.MinIOService;
import top.lldwb.file.saving.tool.pojo.dto.SocketMessage;
import top.lldwb.file.saving.tool.pojo.entity.FileInfo;
import top.lldwb.file.saving.tool.pojo.entity.PathMapping;
import top.lldwb.file.saving.tool.service.control.ControlService;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
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
    public void control(SocketMessage message) {
        String[] strings = Convert.convert(String[].class, message.getData());
        PathMapping pathMapping = JSONUtil.toBean(strings[0], PathMapping.class);
        // 遍历文件夹
        Map<String, JSONArray> stringListMap = JSONUtil.toBean(strings[1], HashMap.class);
        for (String folderPath : stringListMap.keySet()) {
            folderPath = pathMapping.getPathMappingLocalPath() + "\\" + folderPath;
            // 遍历文件夹下面的所有文件对象
            for (FileInfo fileInfo : JSONUtil.toList(stringListMap.get(folderPath), FileInfo.class)) {
                try {
                    minIOService.downloadFile(folderPath + "\\" + fileInfo.getFileInfoName(), fileInfo.getFileInfoPath());
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
        // 启动
        SynchronizationControl.watchMonitorMap.get(pathMapping.getPathMappingLocalPath()).start();
    }

    public void control(Map<String, Object> data) {
        for (String path : data.keySet()) {
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
