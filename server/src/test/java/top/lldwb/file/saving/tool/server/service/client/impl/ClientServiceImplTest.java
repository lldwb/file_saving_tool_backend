package top.lldwb.file.saving.tool.server.service.client.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.lldwb.file.saving.tool.pojo.entity.DirectoryInfo;
import top.lldwb.file.saving.tool.pojo.entity.FileInfo;
import top.lldwb.file.saving.tool.pojo.entity.PathMapping;
import top.lldwb.file.saving.tool.server.dao.DirectoryInfoDao;
import top.lldwb.file.saving.tool.server.dao.FileInfoDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/27
 * @time 9:23
 * @PROJECT_NAME file_saving_tool_backend
 */
@Slf4j
@SpringBootTest
class ClientServiceImplTest {
    @Autowired
    private DirectoryInfoDao directoryInfoDao;
    @Autowired
    private FileInfoDao fileInfoDao;

    @Test
    void synchronization() {
        Map<String, Integer> directoryInfoMap = new HashMap<>();
        String path = "";
        Integer directoryInfoFatherId = 0;
        PathMapping pathMapping = new PathMapping();
        pathMapping.setUserId(1);

        getDirectoryInfoMap(directoryInfoMap, path, directoryInfoFatherId);
        log.info(getFileInfoMap(directoryInfoMap,pathMapping).toString());

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
        Map<String, List<FileInfo>> fileInfoMap = new HashMap<>();
        for (String path : directoryInfoMap.keySet()) {
            fileInfoMap.put(path, fileInfoDao.listByDirectoryInfoIdAndUserId(directoryInfoMap.get(path), pathMapping.getUserId()));
        }
        if (pathMapping.getDirectoryInfoId() == 0) {
            fileInfoMap.put("", fileInfoDao.listByDirectoryInfoIdAndUserId(0, pathMapping.getUserId()));
        }
        return fileInfoMap;
    }
}