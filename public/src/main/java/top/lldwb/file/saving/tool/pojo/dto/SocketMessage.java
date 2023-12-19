package top.lldwb.file.saving.tool.pojo.dto;

import lombok.Data;

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

//    /**
//     * 文件类型(搁置中，未来使用)
//     */
//    private String fileType;
    /**
     * 数据类型
     */
    private Class<T> clazz;
    /**
     * 连接秘钥(单向通讯可以为空，双向通讯不可为空)
     */
    private String secretKey;
    public void setData(String controlType,T data){
        this.setControlType(controlType);
        this.setData(data);
        this.setClazz((Class<T>) data.getClass());
    }

    @Override
    public String toString() {
        return "SocketMessage{" +
                "controlType='" + controlType + '\'' +
                ", clazz=" + clazz +
                ", secretKey='" + secretKey + '\'' +
                '}';
    }
}
