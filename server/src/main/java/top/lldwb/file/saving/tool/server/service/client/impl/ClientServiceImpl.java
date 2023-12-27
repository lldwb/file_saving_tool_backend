package top.lldwb.file.saving.tool.server.service.client.impl;

import cn.hutool.core.bean.BeanUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.lldwb.file.saving.tool.pojo.dto.SocketMessage;
import top.lldwb.file.saving.tool.pojo.entity.Client;
import top.lldwb.file.saving.tool.pojo.entity.DirectoryInfo;
import top.lldwb.file.saving.tool.pojo.entity.FileInfo;
import top.lldwb.file.saving.tool.pojo.entity.PathMapping;
import top.lldwb.file.saving.tool.server.dao.ClientDao;
import top.lldwb.file.saving.tool.server.dao.DirectoryInfoDao;
import top.lldwb.file.saving.tool.server.dao.FileInfoDao;
import top.lldwb.file.saving.tool.server.dao.PathMappingDao;
import top.lldwb.file.saving.tool.server.service.client.ClientService;
import top.lldwb.file.saving.tool.server.service.es.EsService;
import top.lldwb.file.saving.tool.service.send.SendService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/8
 * @time 9:56
 * @PROJECT_NAME file_saving_tool_backend
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientDao clientDao;
    private final DirectoryInfoDao directoryInfoDao;
    private final FileInfoDao fileInfoDao;
    private final SendService nettySend;
    private final PathMappingDao pathMappingDao;
    private final EsService esService;

    @Override
    public void addClient(Client client) {
        clientDao.addClient(client);

        // 修改数据库，同步es
    }

    @Override
    public void updateClient(Client client) {
        clientDao.updateClient(client);
        // 创建客户端对象消息
        SocketMessage<Client> socketMessage = new SocketMessage<>();
        socketMessage.setData("save", getClientBySecretKe(client.getClientSecretKey()));
        socketMessage.setSecretKey(client.getClientSecretKey());
        nettySend.send(socketMessage);
    }

    @Override
    public void updateClientBySecretKe(Client client) {
        client.setClientId(clientDao.getClientBySecretKe(client.getClientSecretKey()).getClientId());
        updateClient(client);
    }

    @Override
    public Client getClientById(Integer clientId) {
        return clientDao.getClientById(clientId);
    }

    @Override
    public Client getClientBySecretKe(String clientSecretKey) {
        return clientDao.getClientBySecretKe(clientSecretKey);
    }

    @Override
    public void download(PathMapping pathMapping) {
        // 获取文件列表
        List<FileInfo> fileInfos = fileInfoDao.getFileInfoByPathANDUserIds(pathMapping.getPathMappingLocalPath() + "%", pathMapping.getUserId());

        Map<String, Object> map = BeanUtil.beanToMap(pathMapping);
        map.put("fileInfos", fileInfos);

        SocketMessage socketMessage = new SocketMessage();
        socketMessage.setData("download", map);
        nettySend.send(socketMessage);

        // 修改数据库，同步es
    }

    @Override
    public void uploading(PathMapping pathMapping) {
        SocketMessage socketMessage = new SocketMessage();
        socketMessage.setData("uploading", pathMapping);
        nettySend.send(socketMessage);

        // 修改数据库，同步es
    }

    @Override
    public void synchronization(PathMapping pathMapping) {
        SocketMessage socketMessage = new SocketMessage();
        socketMessage.setData("synchronization", pathMapping);
        socketMessage.setSecretKey(clientDao.getClientById(pathMapping.getClientId()).getClientSecretKey());
        nettySend.send(socketMessage);

        // 获取首次下载需要的文件并发送
        socketMessage.setData("firstDownload", firstDownload(pathMapping));
        nettySend.send(socketMessage);
    }

    /**
     * 获取首次下载需要的文件
     *
     * @param pathMapping
     */
    private String[] firstDownload(PathMapping pathMapping) {
        /**
         * 文件夹,List<文件>
         */
        Map<String, List<FileInfo>> stringListMap = new HashMap<>();

        Map<String, Integer> directoryInfoMap = new HashMap<>();
        getDirectoryInfoMap(directoryInfoMap, "", pathMapping.getDirectoryInfoId());
        String[] strings = new String[2];
        try {
            strings[0] = new ObjectMapper().writeValueAsString(pathMapping);
            strings[1] = new ObjectMapper().writeValueAsString(getFileInfoMap(directoryInfoMap, pathMapping));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return strings;
    }

    /**
     * 根据父文件夹递归出所有子文件夹
     *
     * @param directoryInfoMap      路径，id
     * @param path                  父文件夹路径
     * @param directoryInfoFatherId 父文件夹id
     */
    private void getDirectoryInfoMap(Map<String, Integer> directoryInfoMap, String path, Integer directoryInfoFatherId) {
        List<DirectoryInfo> directoryInfos = directoryInfoDao.listByDirectoryInfoFatherId(directoryInfoFatherId);
        for (DirectoryInfo directoryInfo : directoryInfos) {
            String paths = path + "\\" + directoryInfo.getDirectoryInfoName();
            directoryInfoMap.put(paths, directoryInfo.getDirectoryInfoId());
            getDirectoryInfoMap(directoryInfoMap, paths, directoryInfo.getDirectoryInfoId());
        }
    }

    /**
     * 根据文件夹集合获取文件集合
     *
     * @param directoryInfoMap 用于获取用户id
     * @param pathMapping
     * @return
     */
    private Map<String, List<FileInfo>> getFileInfoMap(Map<String, Integer> directoryInfoMap, PathMapping pathMapping) {
        log.info("文件夹Map：{}", directoryInfoMap.toString());
        Map<String, List<FileInfo>> fileInfoMap = new HashMap<>();
        for (String path : directoryInfoMap.keySet()) {
            fileInfoMap.put(path, getFileInfo(fileInfoDao.listByDirectoryInfoIdAndUserId(directoryInfoMap.get(path), pathMapping.getUserId())));
        }
        if (pathMapping.getDirectoryInfoId() == 0) {
            fileInfoMap.put("", getFileInfo(fileInfoDao.listByDirectoryInfoIdAndUserId(0, pathMapping.getUserId())));
        }
        return fileInfoMap;
    }

    /**
     * 阉割不需要的数据
     *
     * @param lists
     * @return
     */
    private List<FileInfo> getFileInfo(List<FileInfo> lists) {
        log.info("阉割不需要的数据：{}", lists);
        List<FileInfo> list = new ArrayList<>();
        lists.forEach(fileInfo -> {
            FileInfo fileInfos = new FileInfo();
            fileInfos.setFileInfoName(fileInfo.getFileInfoName());
            fileInfos.setFileInfoPath(fileInfo.getFileInfoPath());
            list.add(fileInfos);
        });
        return list;
    }
}
