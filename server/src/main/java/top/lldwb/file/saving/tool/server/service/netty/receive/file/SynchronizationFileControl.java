package top.lldwb.file.saving.tool.server.service.netty.receive.file;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import top.lldwb.file.saving.tool.pojo.dto.SocketMessage;
import top.lldwb.file.saving.tool.pojo.entity.DirectoryInfo;
import top.lldwb.file.saving.tool.pojo.entity.FileInfo;
import top.lldwb.file.saving.tool.pojo.entity.PathMapping;
import top.lldwb.file.saving.tool.server.service.client.ClientService;
import top.lldwb.file.saving.tool.server.service.minio.MinIOService;
import top.lldwb.file.saving.tool.service.control.ControlService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 同步文件操作
 *
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/7
 * @time 11:42
 * @PROJECT_NAME file_saving_tool_backend
 */
@Slf4j
@Service("synchronizationFile")
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
@RequiredArgsConstructor
public class SynchronizationFileControl implements ControlService {
    private ClientService clientService;
    private final MinIOService minIOService;

    /**
     * 路径,List<文件对象>
     */
    private Map<String, List<FileInfo>> pathMap = new HashMap<>();

    @Override
    public void control(SocketMessage message) {
        String[] strings = Convert.convert(String[].class,message.getData());
        ObjectMapper objectMapper = new ObjectMapper();
//        PathMapping pathMapping  = objectMapper.convertValue(strings[0],PathMapping.class);
        PathMapping pathMapping  = JSONUtil.toBean(strings[0],PathMapping.class);
            log.info("pathMapping：{}", pathMapping.toString());
            // 遍历文件夹
            Map<String, JSONArray> stringListMap = JSONUtil.toBean(strings[1],HashMap.class);
            for (String folderPath : stringListMap.keySet()) {
                // 遍历文件夹下面的所有文件对象
                for (FileInfo fileInfo : JSONUtil.toList(stringListMap.get(folderPath),FileInfo.class)) {
                    log.info("文件路径：{}，用户id：{}，长度：{}，特征码：{}", fileInfo.getFileInfoName(), fileInfo.getUserId(), fileInfo.getFileInfoSize(), fileInfo.getFileInfoPath());
                    minIOService.addFile(fileInfo);
                }
            }

    }

    private void setPathMap(Map<String, String> pathMapping) {
        for (String path : pathMapping.keySet()) {
            FileInfo fileInfo = new FileInfo();
            fileInfo.setFileInfoName(FileNameUtil.getName(path));
            FileNameUtil.getName(path);
        }
    }

    /**
     * 读取对应路径文件的文件特征码
     *
     * @param path 路径
     * @return 文件特征码
     */
    private String getPath(String path) {
        // 截取本地路径并根据`/`进行拆分
        String[] paths = path.replace(path, "").split("/");
        for (int i = 0; i < paths.length; i++) {
            recursionPath(paths[i]);
        }
        return "";
    }

    /**
     * 递归路径
     *
     * @param path
     * @return
     */
    private DirectoryInfo recursionPath(String path) {
        return null;
    }
}