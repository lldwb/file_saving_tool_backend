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
import top.lldwb.file.saving.tool.server.service.minio.impl.FileListenerHandler;
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

        FileListenerHandler fileListenerHandler = new FileListenerHandler(pathMapping, this, directoryInfoDao);
        fileListenerHandler.start();
    }

    @Override
    public void ClientDownload(PathMapping pathMapping, FileInfo fileInfo) {
        SocketMessage socketMessage = new SocketMessage();
        // 获取首次下载需要的文件并发送
        socketMessage.setData("firstDownload", fileInfoDownload(pathMapping, fileInfo));
        nettySend.send(socketMessage);

    }

    /**
     * 获取下载需要的文件
     *
     * @param fileInfo
     */
    private String[] fileInfoDownload(PathMapping pathMapping, FileInfo fileInfo) {
        //获取文件信息父ID
        Integer directoryInfoFatherId = fileInfo.getDirectoryInfoId();
        //定义路径
        String path = "";
        //循环获取父ID
        do {
            //根据父ID获取文件信息
            DirectoryInfo directoryInfo = directoryInfoDao.getDirectoryInfoFatherByDirectoryInfoFatherId(directoryInfoFatherId);
            //获取父ID
            directoryInfoFatherId = directoryInfo.getDirectoryInfoFatherId();
            //判断父ID是否等于路径映射的父ID
            if (directoryInfoFatherId == pathMapping.getDirectoryInfoId()) {
                //如果相等，则跳出循环
                break;
            } else {
                //如果不相等，则拼接路径
                path = directoryInfo.getDirectoryInfoName() + "\\" + path;
            }
        } while (true);

        //定义map
        Map<String, List<FileInfo>> map = new HashMap<>();
        //定义文件信息列表
        List<FileInfo> fileInfos = new ArrayList<>();
        //将文件信息添加到列表
        fileInfos.add(fileInfo);
        //将路径和文件信息列表添加到map中
        map.put(path, fileInfos);
        // 创建一个String数组，用于存储返回值
        String[] strings = new String[2];
        try {
            // 将PathMapping对象转换为字符串
            strings[0] = new ObjectMapper().writeValueAsString(pathMapping);
            // 将文件夹信息Map对象转换为字符串，需要Map<文件夹路径,List<文件>>
            strings[1] = new ObjectMapper().writeValueAsString(map);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        // 返回String数组
        return strings;
    }


    /**
     * 获取首次下载需要的文件
     *
     * @param pathMapping
     */
    private String[] firstDownload(PathMapping pathMapping) {
        // 创建一个Map对象，用于存储文件夹信息
        Map<String, Integer> directoryInfoMap = new HashMap<>();
        // 获取文件夹信息
        getDirectoryInfoMap(directoryInfoMap, "", pathMapping.getDirectoryInfoId(), pathMapping.getUserId());
        // 创建一个String数组，用于存储返回值
        String[] strings = new String[2];
        try {
            // 将PathMapping对象转换为字符串
            strings[0] = new ObjectMapper().writeValueAsString(pathMapping);
            // 将文件夹信息Map对象转换为字符串
            strings[1] = new ObjectMapper().writeValueAsString(getFileInfoMap(directoryInfoMap, pathMapping));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        // 返回String数组
        return strings;
    }

    /**
     * 根据父文件夹递归出所有子文件夹
     *
     * @param directoryInfoMap      路径，id
     * @param path                  父文件夹路径
     * @param directoryInfoFatherId 父文件夹id
     */
    private void getDirectoryInfoMap(Map<String, Integer> directoryInfoMap, String path, Integer directoryInfoFatherId, Integer userId) {
        //根据父id获取目录信息
        List<DirectoryInfo> directoryInfos = directoryInfoDao.listByDirectoryInfoFatherIdAndUserId(directoryInfoFatherId, userId);
        //遍历目录信息
        for (DirectoryInfo directoryInfo : directoryInfos) {
            //拼接路径
            String paths = path + "\\" + directoryInfo.getDirectoryInfoName();
            //将路径和id放入map中
            directoryInfoMap.put(paths, directoryInfo.getDirectoryInfoId());
            //递归调用
            getDirectoryInfoMap(directoryInfoMap, paths, directoryInfo.getDirectoryInfoId(), userId);
        }
    }

    /**
     * 根据文件夹集合获取文件集合
     *
     * @param directoryInfoMap 用于获取用户id
     * @param pathMapping      <文件夹路径,List<文件>>
     * @return
     */
    private Map<String, List<FileInfo>> getFileInfoMap(Map<String, Integer> directoryInfoMap, PathMapping pathMapping) {
        log.info("文件夹Map：{}", directoryInfoMap.toString());
        Map<String, List<FileInfo>> fileInfoMap = new HashMap<>();
        // 遍历directoryInfoMap，获取每一个文件夹的文件信息
        for (String path : directoryInfoMap.keySet()) {
            fileInfoMap.put(path, getFileInfo(fileInfoDao.listByDirectoryInfoIdAndUserId(directoryInfoMap.get(path), pathMapping.getUserId())));
        }
        // 如果pathMapping.getDirectoryInfoId()为0，则获取用户id为pathMapping.getUserId()的文件信息
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
