package top.lldwb.file.saving.tool.server.service.minio.impl;

import lombok.extern.slf4j.Slf4j;
import top.lldwb.file.saving.tool.pojo.entity.DirectoryInfo;
import top.lldwb.file.saving.tool.pojo.entity.FileInfo;
import top.lldwb.file.saving.tool.pojo.entity.PathMapping;
import top.lldwb.file.saving.tool.server.dao.DirectoryInfoDao;
import top.lldwb.file.saving.tool.server.service.client.ClientService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 文件监听处理器，用于监听服务端的修改
 *
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/28
 * @time 9:52
 * @PROJECT_NAME file_saving_tool_backend
 */
@Slf4j
public class FileListenerHandler {
    /**
     * 用于存储文件监听处理器的容器
     * pathMappingId,文件监听处理器对象
     */
    private static Map<Integer, FileListenerHandler> fileListenerHandlerMap = new HashMap<>();

    public static Map<Integer, FileListenerHandler> getFileListenerHandlerMap() {
        return fileListenerHandlerMap;
    }

    private PathMapping pathMapping;
    private ClientService clientService;
    private DirectoryInfoDao directoryInfoDao;

    public FileListenerHandler(PathMapping pathMapping, ClientService clientService, DirectoryInfoDao directoryInfoDao) {
        this.pathMapping = pathMapping;
        this.clientService = clientService;
        this.directoryInfoDao = directoryInfoDao;
        fileListenerHandlerMap.put(pathMapping.getPathMappingId(), this);
    }

    /**
     * 文件夹下面所有的子文件夹
     */
    private void listByDirectoryInfoFatherId() {
        List<DirectoryInfo> directoryInfos = directoryInfoDao.listByDirectoryInfoFatherIdAndUserId(pathMapping.getDirectoryInfoId(), pathMapping.getUserId());
        for (DirectoryInfo directoryInfo : directoryInfos) {
            directoryInfoIdSet.add(directoryInfo.getDirectoryInfoId());
        }
    }

    /**
     * <文件夹id,List<文件id>>
     */
    private Set<Integer> directoryInfoIdSet;

    private Boolean start = false;

    /**
     * 启动文件监听处理器的逻辑
     */
    public void start() {
        start = true;
    }

    /**
     *
     */
    public void close() {
        start = false;
    }

    public void control(FileInfo fileInfo) {
        // 找到对应的文件夹
        if (start && directoryInfoIdSet.contains(fileInfo.getDirectoryInfoId())) {
            clientService.ClientDownload(pathMapping, fileInfo);
        }
    }

}
