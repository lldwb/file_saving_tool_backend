package top.lldwb.file.saving.tool.client.minio.impl;

/**
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/18
 * @time 10:07
 * @PROJECT_NAME file_saving_tool_backend
 */

import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import top.lldwb.file.saving.tool.pojo.dto.SocketMessage;
import top.lldwb.file.saving.tool.pojo.entity.FileInfo;
import top.lldwb.file.saving.tool.pojo.entity.PathMapping;
import top.lldwb.file.saving.tool.service.minIO.MinIOSaveService;
import top.lldwb.file.saving.tool.service.send.SendService;

import java.io.File;
import java.util.*;

/**
 * 涉及文件夹中的操作
 * 修改文件夹：C:\Users\32471\Downloads\3rehdfbz
 * <p>
 * 新建和复制
 * 新建文件：C:\Users\32471\Downloads\安然的尾巴所有节点 - 副本 - 副本
 * 文件变更，进行处理
 * <p>
 * 重命名
 * 新建文件：C:\Users\32471\Downloads\234567890-=
 * 文件变更，进行处理
 * 删除文件：C:\Users\32471\Downloads\安然的尾巴所有节点 - 副本 - 副本
 * <p>
 * 移动
 * 删除文件：C:\Users\32471\Downloads\234567890-=
 * 修改文件夹：C:\Users\32471\Downloads\3rehdfbz
 * 新建文件：C:\Users\32471\Downloads\3rehdfbz\234567890-=
 * 文件变更，进行处理
 * <p>
 * 修改文件内容
 * 修改文件：C:\Users\32471\Downloads\3rehdfbz\12414.txt
 * <p>
 * 删除
 * 删除文件：C:\Users\32471\Downloads\安然的尾巴所有节点 - 副本
 */

/**
 * 文件监听处理器
 */
@Slf4j
@RequiredArgsConstructor
class FileListenerHandler extends FileAlterationListenerAdaptor {

    /**
     * 所有文件路径映射容器<文件路径,特征码>在每次监听后增量更新
     * 文件结构由es搜索引擎查询出来，
     */
    private Map<String, String> pathMap = new HashMap<>();

    /**
     * 存储修改的临时文件路径容器<文件对象,文件夹路径>
     */
    private Map<FileInfo, String> map;

    /**
     * 特征码集合，用于快速判断minio是否有对应文件
     */
    Set<String> sha256List = new HashSet<>();
    private final MinIOSaveService minIOSaveService;
    private final SendService nettySend;
    /**
     * 用于判断操作的是哪个路径
     */
    private final PathMapping pathMapping;

    @Override
    public void onStart(FileAlterationObserver observer) {
        super.onStart(observer);
//        log.info("创建上传容器");
        map = new HashMap<>();
    }

    @Override
    public void onDirectoryCreate(File directory) {
        log.info("新建文件夹：" + directory.getAbsolutePath());
    }

    @Override
    public void onDirectoryChange(File directory) {
        log.info("修改文件夹：" + directory.getAbsolutePath());
    }

    @Override
    public void onDirectoryDelete(File directory) {
        log.info("删除文件夹：" + directory.getAbsolutePath());
    }

    /**
     * 新建文件
     *
     * @param file The file created (ignored)
     */
    @Override
    public void onFileCreate(File file) {
        String compressedPath = file.getAbsolutePath();
        log.info("新建文件：" + compressedPath);
        String sha256 = DigestUtil.sha256Hex(file);
        if (sha256List.contains(sha256)) {
            log.info("minio有该文件，不需要上传");
        } else {
            log.info("minio没有该文件需要上传");
            sha256List.add(sha256);
        }

        log.info("SHA-256：" + sha256);
        if (file.canRead()) {
            // TODO 读取或重新加载文件内容
            log.info("文件变更，进行处理");
        }
        // 文件对象,截取本地路径获取相对路径映射对象中设置的相对路径
        save(file);
    }

    /**
     * 修改文件
     *
     * @param file The file created (ignored)
     */
    @Override
    public void onFileChange(File file) {
        if (file.canRead()) {
            String sha256 = DigestUtil.sha256Hex(file);
            if (sha256List.contains(sha256)) {
                log.info("minio有该文件，不需要上传");
            } else {
                log.info("minio没有该文件需要上传");
                sha256List.add(sha256);
            }
            log.info("SHA-256：" + sha256);
            save(file);
        }
        String compressedPath = file.getAbsolutePath();
        log.info("修改文件：" + compressedPath);
    }

    /**
     * 删除文件
     *
     * @param file The file created (ignored)
     */
    @Override
    public void onFileDelete(File file) {
        log.info("删除文件：" + file.getAbsolutePath());
        // 截取本地路径获取相对路径映射对象中设置的相对路径
        String path = file.getAbsolutePath().replace(pathMapping.getPathMappingLocalPath(), "");

        save(file, null, -1);
    }

    private void save(File file) {
        save(file, DigestUtil.sha256Hex(file), 1);
    }

    private void save(File file, String sha256, Integer state) {
        // 截取本地路径获取相对路径映射对象中设置的相对路径
        String path = file.getAbsolutePath().replace(pathMapping.getPathMappingLocalPath(), "");
        FileInfo fileInfo = new FileInfo();
        fileInfo.setFileInfoName(path);
        fileInfo.setFileInfoPath(sha256);
        fileInfo.setFileInfoSize(file.length());
        fileInfo.setFileInfoState(state);
        fileInfo.setUserId(pathMapping.getUserId());

        // 文件对象,文件所在的文件夹路径
        map.put(fileInfo, path.replace(FileNameUtil.getName(file), ""));
        // 删除文件
        if (sha256 == null && state == -1) {
            // pathMap.containsKey(path)
            pathMap.remove(path);
        } // 新建或者修改文件
        else {
            minIOSaveService.saveMinIO(file);
            pathMap.put(path, sha256);
        }
    }

    /**
     * 统一上传
     *
     * @param observer The file system observer (ignored)
     */
    @Override
    public void onStop(FileAlterationObserver observer) {
        super.onStop(observer);
        if (map.size() > 0) {
            log.info("统一上传");

            Map<String, List<FileInfo>> stringListMap = new HashMap<>();
            // 遍历所有临时文件对象
            for (FileInfo fileInfo : map.keySet()) {
                // 如果有文件夹
                if (stringListMap.containsKey(map.get(fileInfo))) {
                    stringListMap.get(map.get(fileInfo)).add(fileInfo);
                } else {
                    List<FileInfo> list = new ArrayList<>();
                    list.add(fileInfo);
                    stringListMap.put(map.get(fileInfo), list);
                    log.info("文件夹：{}",map.get(fileInfo));
                }
            }
            // 构建消息
//            Map<PathMapping, Map<String, List<FileInfo>>> pathMappingMapMap = new HashMap<>();
//            pathMappingMapMap.put(pathMapping, stringListMap);
//            JSONObject json = JSONUtil.createObj()
//                    .put("pathMapping", pathMapping)
//                    .put("stringListMap", stringListMap);
            String[] strings = new String[2];
            try {
                strings[0] = new ObjectMapper().writeValueAsString(pathMapping);
                strings[1] = new ObjectMapper().writeValueAsString(stringListMap);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            SocketMessage socketMessage = new SocketMessage();
            socketMessage.setData("synchronizationFile", strings);
            // 发送消息
            nettySend.send(socketMessage);
        } else {
//            log.info("没有修改通过上传");
        }

    }
}