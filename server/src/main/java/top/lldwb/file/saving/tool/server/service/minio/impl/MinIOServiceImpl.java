package top.lldwb.file.saving.tool.server.service.minio.impl;

import cn.hutool.crypto.digest.DigestUtil;
import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Item;
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
import top.lldwb.file.saving.tool.server.config.MinIOConfig;
import top.lldwb.file.saving.tool.server.config.RabbitConfig;
import top.lldwb.file.saving.tool.server.config.RabbitEmailAuthCode;
import top.lldwb.file.saving.tool.server.config.RabbitUpdate;
import top.lldwb.file.saving.tool.server.dao.FileInfoDao;
import top.lldwb.file.saving.tool.server.pojo.doc.FileInfoDoc;
import top.lldwb.file.saving.tool.server.pojo.entity.FileInfo;
import top.lldwb.file.saving.tool.server.pojo.entity.Magic;
import top.lldwb.file.saving.tool.server.pojo.entity.PathMapping;
import top.lldwb.file.saving.tool.server.service.es.EsService;
import top.lldwb.file.saving.tool.server.service.minio.MinIOService;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
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

    @Override
    public void addFile(MultipartFile multipartFile, Integer userId) {
        try {
            // 获取文件输入流
            InputStream inputStream = multipartFile.getInputStream();
            // 上传文件到Minio
            minioClient.putObject(PutObjectArgs.builder().bucket(MinIOConfig.BUCKET).object(multipartFile.getOriginalFilename()).stream(inputStream, multipartFile.getSize(), -1).contentType(multipartFile.getContentType()).build());

            FileInfo fileInfo = new FileInfo();
            fileInfo.setFileInfoName(StringUtils.getFilename(multipartFile.getOriginalFilename()));
            fileInfo.setFileInfoPath(multipartFile.getOriginalFilename());
            fileInfo.setFileInfoType(2);
            fileInfo.setFileInfoMd5(DigestUtil.md5Hex(multipartFile.getBytes()));
            fileInfo.setFileInfoSize(multipartFile.getSize());
            fileInfo.setUserId(userId);
            fileInfoDao.addFileInfo(fileInfo);

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
    public void deleteFile(String path, Integer fileInfoId) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(MinIOConfig.BUCKET).object(path).build());


            FileInfo fileInfo = new FileInfo();
            fileInfo.setFileInfoId(fileInfoId);
            fileInfo.setFileInfoType(0);
            fileInfoDao.updateFileInfo(fileInfo);

            template.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitUpdate.QUEUE_NAME, getFileInfoDoc(fileInfo));

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
        return esService.listNamesByNames(FileInfoDoc.class,pageNum,pageSize,fileInfo.getFileInfoName(),"fileInfoName");
    }
}
