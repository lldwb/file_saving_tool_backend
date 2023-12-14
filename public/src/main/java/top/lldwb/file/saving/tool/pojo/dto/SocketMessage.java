package top.lldwb.file.saving.tool.pojo.dto;

import cn.hutool.core.bean.BeanUtil;
import lombok.Data;

import java.util.Map;

/**
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/6
 * @time 16:04
 * @PROJECT_NAME file_saving_tool_backend
 */
@Data
public class SocketMessage<T> extends Message<T> {
    /**
     * 操作类型
     */
    private String controlType;

    /**
     * 文件类型
     */
    private String fileType;
    /**
     * 数据类型
     */
    private Class<T> clazz;
    private String UUID;
}
