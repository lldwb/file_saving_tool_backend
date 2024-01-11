package top.lldwb.file.saving.tool.server.service.entity.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import top.lldwb.file.saving.tool.pojo.dto.UpdateMessage;
import top.lldwb.file.saving.tool.pojo.entity.FileInfo;
import top.lldwb.file.saving.tool.server.config.RabbitConfig;
import top.lldwb.file.saving.tool.server.config.RabbitUpdate;
import top.lldwb.file.saving.tool.server.dao.FileInfoDao;
import top.lldwb.file.saving.tool.server.dao.OperationLogDao;
import top.lldwb.file.saving.tool.server.service.entity.FileInfoService;
import top.lldwb.file.saving.tool.server.service.minio.impl.MinIOServiceImpl;

import java.util.List;

import static top.lldwb.file.saving.tool.server.service.minio.impl.MinIOServiceImpl.getFileInfoDoc;
import static top.lldwb.file.saving.tool.server.service.minio.impl.MinIOServiceImpl.synchronization;

/**
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2024/1/3
 * @time 15:15
 * @PROJECT_NAME file_saving_tool_backend
 */
@Service
@RequiredArgsConstructor
public class FileInfoServiceImpl implements FileInfoService {
    private final FileInfoDao dao;
    private final RabbitTemplate template;
    private final OperationLogDao operationLogDao;
    private final MinIOServiceImpl minIOService;

    @Override
    public FileInfo getId(Integer fileInfoId) {
        return dao.getFileInfoByFileInfoId(fileInfoId);
    }

    @Override
    public List<FileInfo> list(Integer directoryInfoId, Integer userId, Integer fileInfoState) {
        return dao.listByDirectoryInfoIdAndUserId(directoryInfoId, userId, fileInfoState);
    }

    @Override
    public FileInfo copyFile(Integer fileInfoId) {
        FileInfo fileInfo = dao.getFileInfoByFileInfoId(fileInfoId);
        fileInfo.setFileInfoName("副本" + fileInfo.getFileInfoName());
        fileInfo.setFileInfoId(null);
        dao.addFileInfo(fileInfo);
        return fileInfo;
    }

    @Override
    public void update(FileInfo fileInfo) {
        dao.updateFileInfo(fileInfo);
    }

    @Override
    public void deleteFile(Integer fileInfoId) {
        // 删除文件信息
        FileInfo fileInfo = dao.getFileInfoByFileInfoId(fileInfoId);

        // 操作对象，设置为删除
        minIOService.addOperationLog(fileInfo, 3);

        // 设置删除
        fileInfo.setFileInfoState(-1);
        dao.updateFileInfo(fileInfo);

        // 发送消息到消息队列
        template.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitUpdate.QUEUE_NAME, UpdateMessage.getUpdateMessage(getFileInfoDoc(fileInfo)));

        synchronization(fileInfo);
        this.update(fileInfo);
    }
}
