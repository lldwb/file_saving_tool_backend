package top.lldwb.file.saving.tool.server.service.client.impl;

import cn.hutool.core.bean.BeanUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import top.lldwb.file.saving.tool.pojo.dto.SocketMessage;
import top.lldwb.file.saving.tool.pojo.entity.Client;
import top.lldwb.file.saving.tool.pojo.entity.FileInfo;
import top.lldwb.file.saving.tool.pojo.entity.PathMapping;
import top.lldwb.file.saving.tool.server.dao.FileInfoDao;
import top.lldwb.file.saving.tool.server.dao.PathMappingDao;
import top.lldwb.file.saving.tool.server.service.client.ClientService;
import top.lldwb.file.saving.tool.server.service.es.EsService;
import top.lldwb.file.saving.tool.server.service.netty.ServerNettyService;
import top.lldwb.file.saving.tool.server.service.send.SendService;

import java.util.List;
import java.util.Map;

/**
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/8
 * @time 9:56
 * @PROJECT_NAME file_saving_tool_backend
 */
@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final SendService nettySend;
    private final PathMappingDao pathMappingDao;
    private final EsService esService;
    private final FileInfoDao fileInfoDao;

    @Override
    public void addClient(Client client) {
        SocketMessage socketMessage = new SocketMessage();
        socketMessage.setData(client);
        socketMessage.setControlType("addClient");
        socketMessage.setFileType(Client.class.getName());
        socketMessage.setClazz(Class.class);
        nettySend.send(socketMessage);

        // 修改数据库，同步es
    }

    @Override
    public void deleteClient(Client client) {
        SocketMessage socketMessage = new SocketMessage();
        socketMessage.setData(client);
        socketMessage.setControlType("deleteClient");
        socketMessage.setFileType(Client.class.getName());
        nettySend.send(socketMessage);

        // 修改数据库，同步es
    }

    @Override
    public void download(PathMapping pathMapping) {
        // 获取文件列表
        List<FileInfo> fileInfos = fileInfoDao.getFileInfoByPathANDUserIds(pathMapping.getPathMappingLocalPath() + "%", pathMapping.getUserId());

        Map<String, Object> map = BeanUtil.beanToMap(pathMapping);
        map.put("fileInfos", fileInfos);

        SocketMessage socketMessage = new SocketMessage();
        socketMessage.setData(map);
        socketMessage.setControlType("download");
        socketMessage.setFileType(PathMapping.class.getName());
        nettySend.send(socketMessage);

        // 修改数据库，同步es
    }

    @Override
    public void uploading(PathMapping pathMapping) {
        SocketMessage socketMessage = new SocketMessage();
        socketMessage.setData(pathMapping);
        socketMessage.setControlType("uploading");
        socketMessage.setFileType(PathMapping.class.getName());
        nettySend.send(socketMessage);

        // 修改数据库，同步es
    }
}
