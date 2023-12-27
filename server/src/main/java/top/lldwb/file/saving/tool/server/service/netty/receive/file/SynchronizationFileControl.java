package top.lldwb.file.saving.tool.server.service.netty.receive.file;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import top.lldwb.file.saving.tool.pojo.dto.SocketMessage;
import top.lldwb.file.saving.tool.pojo.entity.DirectoryInfo;
import top.lldwb.file.saving.tool.pojo.entity.FileInfo;
import top.lldwb.file.saving.tool.pojo.entity.PathMapping;
import top.lldwb.file.saving.tool.server.dao.DirectoryInfoDao;
import top.lldwb.file.saving.tool.server.service.client.ClientService;
import top.lldwb.file.saving.tool.server.service.minio.MinIOService;
import top.lldwb.file.saving.tool.service.control.ControlService;

import java.io.File;
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
    private final DirectoryInfoDao directoryInfoDao;

    /**
     * 路径,List<文件对象>
     */
    private Map<String, List<FileInfo>> pathMap = new HashMap<>();

    @Override
    public void control(SocketMessage message) {
        String[] strings = Convert.convert(String[].class, message.getData());

        PathMapping pathMapping = JSONUtil.toBean(strings[0], PathMapping.class);
        // 遍历文件夹
        Map<String, JSONArray> stringListMap = JSONUtil.toBean(strings[1], HashMap.class);
        for (String folderPath : stringListMap.keySet()) {
            Integer directoryInfoFatherId = getDirectoryInfoId(folderPath, pathMapping, pathMapping.getDirectoryInfoId());
            // 遍历文件夹下面的所有文件对象
            for (FileInfo fileInfo : JSONUtil.toList(stringListMap.get(folderPath), FileInfo.class)) {
                log.info("文件路径：{}，用户id：{}，长度：{}，特征码：{}", fileInfo.getFileInfoName(), fileInfo.getUserId(), fileInfo.getFileInfoSize(), fileInfo.getFileInfoPath());
                fileInfo.setDirectoryInfoId(directoryInfoFatherId);
                fileInfo.setFileInfoName(FileNameUtil.getName(fileInfo.getFileInfoName()));
                minIOService.addFile(fileInfo);
            }
        }

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
        // 判断是否是根目录下的文件
        // 不是
        if (strings.length - 1 > 0) {
            String[] paths = new String[strings.length - 1];
            log.info("子文件夹数组：{}", strings);
            for (int i = 1; i < strings.length; i++) {
                log.info("子文件夹：{}", strings[i]);
                paths[i - 1] = strings[i];
            }
            return getDirectoryInfoId(paths, pathMapping, directoryInfoFatherId);
        }// 是根目录
        else {
            return pathMapping.getDirectoryInfoId();
        }
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