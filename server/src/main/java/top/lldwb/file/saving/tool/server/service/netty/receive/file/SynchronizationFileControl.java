package top.lldwb.file.saving.tool.server.service.netty.receive.file;

import cn.hutool.core.io.file.FileNameUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import top.lldwb.file.saving.tool.pojo.dto.SocketMessage;
import top.lldwb.file.saving.tool.pojo.entity.DirectoryInfo;
import top.lldwb.file.saving.tool.pojo.entity.FileInfo;
import top.lldwb.file.saving.tool.server.service.client.ClientService;
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
@Service("synchronizationFile")
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
@RequiredArgsConstructor
public class SynchronizationFileControl implements ControlService {
    private ClientService clientService;

    /**
     * 路径,List<文件对象>
     */
    private Map<String, List<FileInfo>> pathMap = new HashMap<>();

    @Override
    public void control(SocketMessage message) {
//        // Map<路径,特征码>，如果删除特征码为null
//        Map<String, String> pathMapping = pathSocketMessage.getData();
//        // 连接秘钥用于找到操作的客户端和对应的用户
//        String secretKey = message.getSecretKey();
//        Client client = clientService.getClientBySecretKe(secretKey);


    }

    private void setPathMap(Map<String, String> pathMapping){
        for (String path:pathMapping.keySet()){
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