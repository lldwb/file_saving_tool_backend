package top.lldwb.file.saving.tool.server.service.minio.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.digest.DigestUtil;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import top.lldwb.file.saving.tool.config.MinIOConfig;
import top.lldwb.file.saving.tool.pojo.dto.UpdateMessage;
import top.lldwb.file.saving.tool.pojo.entity.DirectoryInfo;
import top.lldwb.file.saving.tool.pojo.entity.FileInfo;
import top.lldwb.file.saving.tool.pojo.entity.OperationLog;
import top.lldwb.file.saving.tool.server.config.RabbitConfig;
import top.lldwb.file.saving.tool.server.config.RabbitUpdate;
import top.lldwb.file.saving.tool.server.dao.DirectoryInfoDao;
import top.lldwb.file.saving.tool.server.dao.FileInfoDao;
import top.lldwb.file.saving.tool.server.dao.OperationLogDao;
import top.lldwb.file.saving.tool.server.pojo.doc.FileInfoDoc;
import top.lldwb.file.saving.tool.server.pojo.doc.OperationLogDoc;
import top.lldwb.file.saving.tool.server.service.entity.FileInfoService;
import top.lldwb.file.saving.tool.server.service.es.EsService;
import top.lldwb.file.saving.tool.server.service.minio.FileListenerHandler;
import top.lldwb.file.saving.tool.server.service.minio.MinIOService;

import java.io.*;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

/**
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/1
 * @time 9:28
 * @PROJECT_NAME file_saving_tool_backend
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MinIOServiceImpl implements MinIOService {
    private final MinioClient minioClient;
    private final FileInfoDao fileInfoDao;
    private final DirectoryInfoDao directoryInfoDao;
    private final OperationLogDao operationLogDao;
    private final RabbitTemplate template;
    private final EsService esService;

    /**
     * 文件对象转换成文件文档对象
     *
     * @param fileInfo
     * @return
     */
    public static  FileInfoDoc getFileInfoDoc(FileInfo fileInfo) {
        FileInfoDoc fileInfoDoc = new FileInfoDoc();
        BeanUtil.copyProperties(fileInfo, fileInfoDoc);
        return fileInfoDoc;
    }

    /**
     * 文件夹对象转换成文件文档对象
     *
     * @param directoryInfo
     * @return
     */
    public static FileInfoDoc getFileInfoDoc(DirectoryInfo directoryInfo) {
        FileInfoDoc fileInfoDoc = new FileInfoDoc();
        fileInfoDoc.setFileInfoId(directoryInfo.getDirectoryInfoId());
        fileInfoDoc.setFileInfoName(directoryInfo.getDirectoryInfoName());
        fileInfoDoc.setUserId(directoryInfo.getUserId());
        fileInfoDoc.setDirectoryInfoId(directoryInfo.getDirectoryInfoFatherId());
        fileInfoDoc.setFileInfoState(0);
        return fileInfoDoc;
    }

    /**
     * 操作日志对象转换成操作日志文档对象
     *
     * @param operationLog
     * @return
     */
    public static OperationLogDoc getOperationLogDoc(OperationLog operationLog) {
        OperationLogDoc operationLogDoc = new OperationLogDoc();
        BeanUtil.copyProperties(operationLog, operationLogDoc);
        return operationLogDoc;
    }

    /**
     * FileInfo文件对象转化成OperationLog操作对象
     *
     * @param fileInfo 文件对象
     * @param type     操作类型
     * @return
     */
    public void addOperationLog(FileInfo fileInfo, Integer type) {
        OperationLog operationLog = new OperationLog();
        operationLog.setOperationLogName(fileInfo.getFileInfoName());
        operationLog.setOperationLogPath(fileInfo.getFileInfoPath());
        operationLog.setOperationLogType(type);
        operationLog.setOperationLogSize(fileInfo.getFileInfoSize());
        operationLog.setUserId(fileInfo.getUserId());
        operationLog.setFileInfoId(fileInfo.getFileInfoId());
        operationLog.setDirectoryInfoId(fileInfo.getDirectoryInfoId());
        addOperationLog(operationLog);
    }

    /**
     * DirectoryInfo文件夹对象转化成OperationLog操作对象
     *
     * @param directoryInfo 文件夹对象
     * @param type          操作类型
     * @return
     */
    public void addOperationLog(DirectoryInfo directoryInfo, Integer type) {
        OperationLog operationLog = new OperationLog();
        operationLog.setOperationLogName(directoryInfo.getDirectoryInfoName());
        operationLog.setOperationLogType(type);
        operationLog.setUserId(directoryInfo.getUserId());
        operationLog.setFileInfoId(directoryInfo.getDirectoryInfoId());
        operationLog.setDirectoryInfoId(directoryInfo.getDirectoryInfoFatherId());
        addOperationLog(operationLog);
    }

    private void addOperationLog(OperationLog operationLog){
        operationLogDao.addOperationLog(operationLog);
        // 发送文件信息到消息队列
        template.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitUpdate.QUEUE_NAME, UpdateMessage.getUpdateMessage(getOperationLogDoc(operationLog)));
    }

    /**
     * operationLog转化成FileInfo
     *
     * @param operationLog 文件对象
     * @return
     */
    public static  FileInfo getFileInfo(OperationLog operationLog, FileInfo fileInfos) {
        FileInfo fileInfo = new FileInfo();
        BeanUtil.copyProperties(fileInfos, fileInfo);
        fileInfo.setFileInfoName(operationLog.getOperationLogName());
        fileInfo.setFileInfoPath(operationLog.getOperationLogPath());
        fileInfo.setFileInfoSize(operationLog.getOperationLogSize());
        fileInfo.setDirectoryInfoId(operationLog.getDirectoryInfoId());
        if (operationLog.getOperationLogType() == 3) {
            fileInfo.setFileInfoState(-1);
        } else {
            fileInfo.setFileInfoState(1);
        }
        return fileInfo;
    }

    @Override
    public void addFile(MultipartFile multipartFile, Integer directoryInfoId, Integer userId) {
        try {
            // 获取文件输入流
//            InputStream inputStream = multipartFile.getInputStream();

            String sha256Hex = DigestUtil.sha256Hex(multipartFile.getBytes());

            // 检测是否已经存在，如果存在则不上传
            saveMinIO(multipartFile);

            // 文件信息对象
            FileInfo fileInfo = new FileInfo();
            // 用于查找文件是否覆盖的信息
            fileInfo.setUserId(userId);
            // 随机生成的
            fileInfo.setFileInfoPath(sha256Hex);
            fileInfo.setFileInfoSize(multipartFile.getSize());
            fileInfo.setFileInfoName(StringUtils.getFilename(multipartFile.getOriginalFilename()));
            // 文件夹
            fileInfo.setDirectoryInfoId(directoryInfoId);

            // 操作对象
            OperationLog operationLog;

            // 判断是否是文件覆盖
            FileInfo fileInfoSql = fileInfoDao.getFileInfoByPathANDUserId(fileInfo);
            if (fileInfoSql != null) {
                fileInfo.setFileInfoId(fileInfoSql.getFileInfoId());
                fileInfoDao.updateFileInfo(fileInfo);
                // 操作类型为修改
                addOperationLog(fileInfo, 2);
            } else {
                fileInfoDao.addFileInfo(fileInfo);
                // 操作类型为添加
                addOperationLog(fileInfo, 1);
            }
            // 异步双写
            // 发送文件信息到消息队列
            template.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitUpdate.QUEUE_NAME, UpdateMessage.getUpdateMessage(getFileInfoDoc(fileInfo)));

            synchronization(fileInfo);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addFile(FileInfo fileInfo) {
        // 操作对象
        OperationLog operationLog;
        if (fileInfo.getFileInfoId() != null) {
            // 判断是否是文件覆盖
            FileInfo fileInfoSql = fileInfoDao.getFileInfoByFileInfoId(fileInfo.getFileInfoId());
            fileInfo.setFileInfoId(fileInfoSql.getFileInfoId());
            fileInfoDao.updateFileInfo(fileInfo);
            // 操作类型为修改
            addOperationLog(fileInfo, 2);
        } else {
            fileInfoDao.addFileInfo(fileInfo);
            // 操作类型为添加
            addOperationLog(fileInfo, 1);
        }
        // 异步双写
        // 发送文件信息到消息队列
        template.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitUpdate.QUEUE_NAME, UpdateMessage.getUpdateMessage(getFileInfoDoc(fileInfo)));
    }

    @Override
    public void addDirectoryInfo(DirectoryInfo directoryInfo) {
        directoryInfoDao.addDirectoryInfo(directoryInfo);
        // 异步双写
        addOperationLog(directoryInfo, 1);
        // 发送文件信息到消息队列
        template.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitUpdate.QUEUE_NAME, UpdateMessage.getUpdateMessage(getFileInfoDoc(directoryInfo)));
    }

    @Override
    public void updateDirectoryInfo(DirectoryInfo directoryInfo) {
//directoryInfoDao
        // 异步双写
         addOperationLog(directoryInfo, 1);
        // 发送文件信息到消息队列
        template.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitUpdate.QUEUE_NAME, UpdateMessage.getUpdateMessage(getFileInfoDoc(directoryInfo)));
    }

    @Override
    public void deleteDirectoryInfo(Integer directoryInfoId) {

    }

    @Override
    public FileInfo recoverFile(Integer operationLogId) {
        // 获取操作对象
        OperationLog operationLog = operationLogDao.getOperationLogByOperationLogId(operationLogId);
        // 根据操作对象获取文件对象
        FileInfo fileInfo = fileInfoDao.getFileInfoByFileInfoId(operationLog.getFileInfoId());
        // 存档恢复操作
        addOperationLog(fileInfo, -operationLog.getOperationLogType());

        // 恢复
        fileInfo = getFileInfo(operationLog, fileInfo);

        fileInfoDao.updateFileInfo(fileInfo);

        // 发送消息到消息队列
        template.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitUpdate.QUEUE_NAME, UpdateMessage.getUpdateMessage(getFileInfoDoc(fileInfo)));

        synchronization(fileInfo);
        return fileInfo;
    }

    @Override
    public ResponseEntity<InputStreamResource> downloadFile(String path) {
        try {
            InputStream inputStream = minioClient.getObject(GetObjectArgs.builder().bucket(MinIOConfig.BUCKET).object(path).build());

            // 对文件名进行编码，防止在响应头中出现乱码
            String fileName = URLEncoder.encode(path, "UTF-8");
            // 设置响应头，告诉浏览器响应的是流数据
            HttpHeaders headers = new HttpHeaders();
            // 设置头信息，将响应内容处理的方式设置为附件下载
            headers.setContentDispositionFormData("attachment", fileName);
            // 设置响应类型为流类型
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

            // 创建 InputStreamReader 对象封装输入流，用于读取服务器文件
            InputStreamResource inputStreamReader = new InputStreamResource(inputStream);
            // 创建 ResponseEntity 对象，封装(InputStreamReader，响应头 HttpHeaders，状态码 201)
            // 200 成功，201 成功但是还有数据
            ResponseEntity<InputStreamResource> response = new ResponseEntity<>(inputStreamReader, headers, HttpStatus.CREATED);
            return response;
        } catch (ErrorResponseException e) {
            throw new RuntimeException(e);
        } catch (InsufficientDataException e) {
            throw new RuntimeException(e);
        } catch (InternalException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (InvalidResponseException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (ServerException e) {
            throw new RuntimeException(e);
        } catch (XmlParserException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<InputStreamResource> downloadFileInfoId(Integer fileInfoId) {
        try {
            FileInfo fileInfo = fileInfoDao.getFileInfoByFileInfoId(fileInfoId);
            InputStream inputStream = minioClient.getObject(GetObjectArgs.builder().bucket(MinIOConfig.BUCKET).object(fileInfo.getFileInfoPath()).build());

            // 对文件名进行编码，防止在响应头中出现乱码
            String fileName = URLEncoder.encode(fileInfo.getFileInfoName(), "UTF-8");
            // 设置响应头，告诉浏览器响应的是流数据
            HttpHeaders headers = new HttpHeaders();
            // 设置头信息，将响应内容处理的方式设置为附件下载
            headers.setContentDispositionFormData("attachment", fileName);
            // 设置响应类型为流类型
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

            // 创建 InputStreamReader 对象封装输入流，用于读取服务器文件
            InputStreamResource inputStreamReader = new InputStreamResource(inputStream);
            // 创建 ResponseEntity 对象，封装(InputStreamReader，响应头 HttpHeaders，状态码 201)
            // 200 成功，201 成功但是还有数据
            ResponseEntity<InputStreamResource> response = new ResponseEntity<>(inputStreamReader, headers, HttpStatus.CREATED);
            return response;
        } catch (ErrorResponseException e) {
            throw new RuntimeException(e);
        } catch (InsufficientDataException e) {
            throw new RuntimeException(e);
        } catch (InternalException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (InvalidResponseException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (ServerException e) {
            throw new RuntimeException(e);
        } catch (XmlParserException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void downloadFile(String name, String path) {
        try {
            InputStream inputStream = minioClient.getObject(GetObjectArgs.builder().bucket(MinIOConfig.BUCKET).object(path).build());
            OutputStream outputStream = new FileOutputStream(name);

            // 返回指定的文件长度
            byte[] bytes = new byte[1];
            // 读取输入文件的数据到字节数组中
            if (inputStream.read(bytes) != -1) {
                // 将字节数组中的数据写入输出文件
                outputStream.write(bytes);
            }

            //关闭流对象
            inputStream.close();
            outputStream.close();

        } catch (ErrorResponseException e) {
            throw new RuntimeException(e);
        } catch (InsufficientDataException e) {
            throw new RuntimeException(e);
        } catch (InternalException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (InvalidResponseException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (ServerException e) {
            throw new RuntimeException(e);
        } catch (XmlParserException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getFilePath(Integer fileInfoId) {
        String path = new String();
        FileInfo fileInfo = fileInfoDao.getFileInfoByFileInfoId(fileInfoId);
        if (fileInfo.getDirectoryInfoId() != 0) {
            path = getDirectoryInfoPath(fileInfo.getDirectoryInfoId());
        }
        path += "/" + fileInfo.getFileInfoName();
        return path;
    }

    @Override
    public String getDirectoryInfoPath(Integer directoryInfoId) {
        String path = new String();
        DirectoryInfo directoryInfo = directoryInfoDao.getDirectoryInfoById(directoryInfoId);
        if (directoryInfo.getDirectoryInfoId() != 0) {
            path = getDirectoryInfoPath(directoryInfo.getDirectoryInfoFatherId());
        }
        path += "/" + directoryInfo.getDirectoryInfoName();
        return path;
    }

    @Override
    public List<FileInfoDoc> getFiles(FileInfo fileInfo, Integer pageNum, Integer pageSize) {
        Map<String, Object> map = BeanUtil.beanToMap(fileInfo);
        return esService.listNamesByNames(FileInfoDoc.class, pageNum, pageSize, map);
    }

    @Override
    public List<DirectoryInfo> listDirectoryInfo(Integer directoryInfoId, Integer userId) {
        return directoryInfoDao.listByDirectoryInfoFatherIdAndUserId(directoryInfoId, userId, null);
    }

    @Override
    public List<DirectoryInfo> listDirectoryInfoByPath(String path, Integer userId) {
        log.info("文件夹路径：{}", path);
        String separator = File.separator;
        // 分割路径
        Integer directoryInfoId = 0;
        // 遍历路径
        if (path != null && !"".equals(path)) {
            String[] strings = path.split(separator + separator);
            for (String directoryInfoName : strings) {
                // 获取路径中的文件夹
                DirectoryInfo directoryInfo = directoryInfoDao.getDirectoryInfoByFatherIdAndName(directoryInfoId, directoryInfoName);
                // 父文件夹id赋值
                directoryInfoId = directoryInfo.getDirectoryInfoId();
            }
        }
        return directoryInfoDao.listByDirectoryInfoFatherIdAndUserId(directoryInfoId, userId, null);
    }

    @Override
    public FileInfo getFileInfoByFileInfoId(Integer fileInfoId) {
        return fileInfoDao.getFileInfoByFileInfoId(fileInfoId);
    }

    @Override
    public void saveMinIO(MultipartFile multipartFile) {
        try {
            String sha256Hex = DigestUtil.sha256Hex(multipartFile.getBytes());
            saveMinIO(multipartFile.getInputStream(), sha256Hex, multipartFile.getSize());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveMinIO(InputStream inputStream, String sha256Hex, Long size) {
        // 检测是否已经存在，如果存在则不上传
        log.info("检测是否已经存在，如果存在则不上传");
        try {
            log.info("检测{}是否已经存在，如果存在则不上传", sha256Hex);
            minioClient.getObject(GetObjectArgs.builder().bucket(MinIOConfig.BUCKET).object(sha256Hex).length(0L).build());
            // 判断文件是否存在
//            boolean exists = minioClient.statObject(StatObjectArgs.builder().bucket(MinIOConfig.BUCKET).object(sha256Hex).build()) != null;
        } catch (Exception e) {
            try {
                log.info("上传{}文件到Minio", sha256Hex);
                minioClient.putObject(PutObjectArgs.builder().bucket(MinIOConfig.BUCKET).object(sha256Hex).stream(inputStream, size, -1).build());
            } catch (Exception ex) {
                log.info("上传出错");
            }
        }
        log.info("结束");
    }

    /**
     * 同步，调用文件监听处理器
     *
     * @param fileInfo
     */
    public static void synchronization(FileInfo fileInfo) {
        Map<Integer, FileListenerHandler> fileListenerHandlerMap = FileListenerHandler.getFileListenerHandlerMap();
        for (Integer pathMappingId : fileListenerHandlerMap.keySet()) {
            fileListenerHandlerMap.get(pathMappingId).control(fileInfo);
        }
    }

    @Override
    public void refresh() {
        List<FileInfo> fileInfos = fileInfoDao.getFileInfos();
        esService.deleteIndex(FileInfoDao.class);
        fileInfos.forEach(fileInfo ->
                // 发送文件信息到消息队列
                template.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitUpdate.QUEUE_NAME, UpdateMessage.getUpdateMessage(getFileInfoDoc(fileInfo))));
    }
}
