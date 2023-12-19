package top.lldwb.file.saving.tool.pojo.dto;

import lombok.Data;
import top.lldwb.file.saving.tool.pojo.entity.FileInfo;
import top.lldwb.file.saving.tool.pojo.entity.PathMapping;

import java.util.List;
import java.util.Map;

/**
 * 路径映射消息对象 所有文件路径映射容器<文件夹路径,List<文件对象>>
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/19
 * @time 8:42
 * @PROJECT_NAME file_saving_tool_backend
 */
@Data
public class PathSocketMessage extends SocketMessage<Map<String, List<FileInfo>>>{
    /**
     * 用于判断操作的是哪个路径
     */
    private PathMapping pathMapping;
}
