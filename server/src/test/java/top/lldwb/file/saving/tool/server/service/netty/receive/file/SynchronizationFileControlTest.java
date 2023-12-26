package top.lldwb.file.saving.tool.server.service.netty.receive.file;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.lldwb.file.saving.tool.pojo.entity.DirectoryInfo;
import top.lldwb.file.saving.tool.pojo.entity.PathMapping;
import top.lldwb.file.saving.tool.server.dao.DirectoryInfoDao;

import java.io.File;

/**
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/23
 * @time 10:55
 * @PROJECT_NAME file_saving_tool_backend
 */
@Slf4j
@SpringBootTest
class SynchronizationFileControlTest {

    @Test
    void control() {
        String path = "C:\\Users\\32471\\Downloads\\2434\\7 - 副本 - 副本\\自建vps";
        log.info("文件夹路径：{}", path);
//        String[] strings = path.split("\\");
        String separator = File.separator;
        String[] strings = path.split(separator + separator);
        String[] paths = new String[strings.length - 2];
        for (int i = 1; i < strings.length - 1; i++) {
            log.info(strings[i]);
            paths[i - 1] = strings[i];
        }
    }

    @Autowired
    private DirectoryInfoDao directoryInfoDao;

    @Test
    void testControl() {
        PathMapping pathMapping = new PathMapping();
        pathMapping.setUserId(1);
        Integer directoryInfoFatherId = getDirectoryInfoId("\\Users\\32471\\Nutstore\\1\\编程\\笔记\\",pathMapping, 14);
    }

    /**
     * 生成并获取文件夹
     *
     * @param path                  路径
     * @param directoryInfoFatherId 父文件夹id
     * @return
     */
    private Integer getDirectoryInfoId(String path, PathMapping pathMapping, Integer directoryInfoFatherId) {
        log.info("文件夹路径：{}", path);
        String separator = File.separator;
        String[] strings = path.split(separator + separator);
//        String[] strings = path.split("\\");
        String[] paths = new String[strings.length - 1];
        log.info("子文件夹数组：{}", strings);
        for (int i = 1; i < strings.length; i++) {
            log.info("子文件夹：{}", strings[i]);
            paths[i - 1] = strings[i];
        }
        return getDirectoryInfoId(paths, pathMapping, directoryInfoFatherId);
    }

    /**
     * 生成并获取文件夹
     *
     * @param path                  路径
     * @param directoryInfoFatherId 父文件夹id
     * @return
     */
    private Integer getDirectoryInfoId(String[] path, PathMapping pathMapping, Integer directoryInfoFatherId) {
        // 判断文件夹是否存在，存在返回文件夹
        DirectoryInfo directoryInfo = directoryInfoDao.getDirectoryInfoByFatherIdAndName(directoryInfoFatherId, path[0]);
//        log.info("返回的文件夹：{}",directoryInfo.toString());
        // 不存在创建文件夹
        if (directoryInfo == null || directoryInfo.getDirectoryInfoName() == null || directoryInfo.getDirectoryInfoName().isEmpty()) {
            directoryInfo = new DirectoryInfo();
            directoryInfo.setDirectoryInfoName(path[0]);
            directoryInfo.setDirectoryInfoState(1);
            directoryInfo.setUserId(pathMapping.getUserId());
            directoryInfo.setDirectoryInfoFatherId(directoryInfoFatherId);
            directoryInfoDao.addDirectoryInfo(directoryInfo);
        }
        // 递归下面的文件夹
        if (path.length > 1) {
            String[] paths = new String[path.length - 1];
            for (int i = 1; i < path.length; i++) {
                paths[i - 1] = path[i];
            }
            return getDirectoryInfoId(paths, pathMapping, directoryInfo.getDirectoryInfoId());
        } else {
            // 返回文件夹id
            return directoryInfo.getDirectoryInfoId();
        }
    }
}