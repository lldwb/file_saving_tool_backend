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
public class SocketMessage extends Message<Map<String, Object>> {
    /**
     * 操作类型
     */
    private String controlType;

    /**
     * 文件类型
     */
    private String fileType;

    public void setObjectData(Object object) {
        setData(BeanUtil.beanToMap(object));
    }
}
