package top.lldwb.file.saving.tool.server.service.minio.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.digest.DigestUtil;
import io.minio.*;
import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
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
import top.lldwb.file.saving.tool.server.config.RabbitConfig;
import top.lldwb.file.saving.tool.server.config.RabbitUpdate;
import top.lldwb.file.saving.tool.server.dao.FileInfoDao;
import top.lldwb.file.saving.tool.server.dao.OperationLogDao;
import top.lldwb.file.saving.tool.server.pojo.doc.FileInfoDoc;
import top.lldwb.file.saving.tool.server.pojo.doc.OperationLogDoc;
import top.lldwb.file.saving.tool.pojo.entity.FileInfo;
import top.lldwb.file.saving.tool.pojo.entity.OperationLog;
import top.lldwb.file.saving.tool.server.service.es.EsService;
import top.lldwb.file.saving.tool.server.service.minio.MinIOService;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/1
 * @time 9:28
 * @PROJECT_NAME file_saving_tool_backend
 */
@Service
@RequiredArgsConstructor
public class MinIOServiceImpl implements MinIOService {
    private final MinioClient minioClient;
    private final FileInfoDao fileInfoDao;
    private final OperationLogDao operationLogDao;
    private final RabbitTemplate template;
    private final EsService esService;

    private FileInfoDoc getFileInfoDoc(FileInfo fileInfo) {
        FileInfoDoc fileInfoDoc = new FileInfoDoc();
        fileInfoDoc.setFileInfoId(fileInfo.getFileInfoId());
        fileInfoDoc.setFileInfoName(fileInfo.getFileInfoName());
        fileInfoDoc.setFileInfoPath(fileInfo.getFileInfoPath());
        fileInfoDoc.setFileInfoType(fileInfo.getFileInfoType());
        fileInfoDoc.setUserId(fileInfoDoc.getUserId());
        return fileInfoDoc;
    }

    private OperationLogDoc getOperationLogDoc(OperationLog operationLog) {
        OperationLogDoc operationLogDoc = new OperationLogDoc();
        operationLogDoc.setOperationLogId(operationLog.getOperationLogId());
        operationLogDoc.setOperationLogName(operationLog.getOperationLogName());
        operationLogDoc.setOperationLogPath(operationLog.getOperationLogPath());
        operationLogDoc.setOperationLogType(operationLog.getOperationLogType());
        operationLogDoc.setOperationLogFileType(operationLog.getOperationLogFileType());
        operationLogDoc.setUserId(operationLog.getUserId());
        operationLogDoc.setFileInfoId(operationLog.getFileInfoId());
        return operationLogDoc;
    }

    /**
     * FileInfo转化成OperationLog
     *
     * @param fileInfo 文件对象
     * @param type     操作类型
     * @return
     */
    private OperationLog getOperationLog(FileInfo fileInfo, Integer type) {
        OperationLog operationLog = new OperationLog();
        operationLog.setOperationLogName(fileInfo.getFileInfoName());
        operationLog.setOperationLogPath(fileInfo.getFileInfoPath());
        operationLog.setOperationLogMinioPath(fileInfo.getFileInfoMinIOPath());
        operationLog.setOperationLogType(type);
        operationLog.setOperationLogFileType(fileInfo.getFileInfoType());
        operationLog.setOperationLogMd5(fileInfo.getFileInfoMd5());
        operationLog.setOperationLogSize(fileInfo.getFileInfoSize());
        operationLog.setUserId(fileInfo.getUserId());
        operationLog.setFileInfoId(fileInfo.getFileInfoId());
        return operationLog;
    }

    @Override
    public void addFile(MultipartFile multipartFile, Integer userId) {
        try {
            // 获取文件输入流
            InputStream inputStream = multipartFile.getInputStream();

            String UUID = IdUtil.simpleUUID();

            // 上传文件到Minio
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(MinIOConfig.BUCKET)
                            .object(UUID)
                            .stream(inputStream, multipartFile.getSize(), -1)
                            .contentType(multipartFile.getContentType())
                            .build()
            );
            // 文件信息对象
            FileInfo fileInfo = new FileInfo();
            // 用于查找文件是否覆盖的信息
            fileInfo.setFileInfoPath(multipartFile.getOriginalFilename());
            fileInfo.setUserId(userId);
            // 随机生成的
            fileInfo.setFileInfoMinIOPath(UUID);
            fileInfo.setFileInfoMd5(DigestUtil.md5Hex(multipartFile.getBytes()));
            fileInfo.setFileInfoSize(multipartFile.getSize());
            // 不是随机生成的
            fileInfo.setFileInfoType(2);
            fileInfo.setFileInfoName(StringUtils.getFilename(multipartFile.getOriginalFilename()));

            // 操作对象
            OperationLog operationLog;

            // 判断是否是文件覆盖
            FileInfo fileInfoSql = fileInfoDao.getFileInfoByPathANDUserId(fileInfo);
            if (fileInfoSql != null && 2 == fileInfoSql.getFileInfoType()) {
                fileInfo.setFileInfoId(fileInfoSql.getFileInfoId());
                fileInfoDao.updateFileInfo(fileInfo);
                // 操作类型为修改
                operationLog = getOperationLog(fileInfo, 2);
            } else {
                fileInfoDao.addFileInfo(fileInfo);
                // 操作类型为添加
                operationLog = getOperationLog(fileInfo, 1);
            }
            operationLogDao.addOperationLog(operationLog);
            // 发送文件信息到消息队列
            template.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitUpdate.QUEUE_NAME, getOperationLogDoc(operationLog));

            // 发送文件信息到消息队列
            template.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitUpdate.QUEUE_NAME, getFileInfoDoc(fileInfo));


        } catch (ServerException e) {
            throw new RuntimeException(e);
        } catch (InsufficientDataException e) {
            throw new RuntimeException(e);
        } catch (ErrorResponseException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (InvalidResponseException e) {
            throw new RuntimeException(e);
        } catch (XmlParserException e) {
            throw new RuntimeException(e);
        } catch (InternalException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteFile(Integer fileInfoId) {
//            minioClient.removeObject(RemoveObjectArgs.builder().bucket(MinIOConfig.BUCKET).object(path).build());


        // 删除文件信息
        FileInfo fileInfo = fileInfoDao.getFileInfoByFileInfoId(fileInfoId);

        // 操作对象，设置为删除
        OperationLog operationLog = getOperationLog(fileInfo, 3);
        operationLogDao.addOperationLog(operationLog);
        // 发送文件信息到消息队列
        template.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitUpdate.QUEUE_NAME, getOperationLogDoc(operationLog));

        // 设置删除
        fileInfo.setFileInfoType(fileInfo.getFileInfoType() * -1);
        fileInfo.setFileInfoMinIOPath("");
        fileInfo.setFileInfoMd5("");
        fileInfo.setFileInfoSize(0L);
        fileInfoDao.updateFileInfo(fileInfo);

        // 发送消息到消息队列
        template.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitUpdate.QUEUE_NAME, getFileInfoDoc(fileInfo));

    }

    @Override
    public FileInfo recoverFile(Integer operationLogId) {
        // 获取操作对象
        OperationLog operationLog = operationLogDao.getOperationLogByOperationLogId(operationLogId);
        // 根据操作对象获取文件对象
        FileInfo fileInfo = fileInfoDao.getFileInfoByFileInfoId(operationLog.getFileInfoId());
        // 存档恢复操作
        operationLogDao.addOperationLog(getOperationLog(fileInfo, operationLog.getOperationLogType() * -1));
        // 发送文件信息到消息队列
        template.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitUpdate.QUEUE_NAME, getOperationLogDoc(getOperationLog(fileInfo, operationLog.getOperationLogType() * -1)));
        // 恢复
        fileInfo.setFileInfoType(operationLog.getOperationLogFileType());
        fileInfo.setFileInfoMinIOPath(operationLog.getOperationLogMinioPath());
        fileInfo.setFileInfoMd5(operationLog.getOperationLogMd5());
        fileInfo.setFileInfoSize(operationLog.getOperationLogSize());

        fileInfoDao.updateFileInfo(fileInfo);

        // 发送消息到消息队列
        template.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitUpdate.QUEUE_NAME, getFileInfoDoc(fileInfo));
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
    public List<FileInfoDoc> getFiles(FileInfo fileInfo, Integer pageNum, Integer pageSize) {
        return esService.listNamesByNames(FileInfoDoc.class, pageNum, pageSize, fileInfo.getFileInfoName(), "fileInfoName");
    }

    @Override
    public FileInfo getFileInfoByFileInfoId(Integer fileInfoId) {
        return fileInfoDao.getFileInfoByFileInfoId(fileInfoId);
    }
}
