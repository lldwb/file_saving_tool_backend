package top.lldwb.file.saving.tool.server.pojo.doc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * 路径映射文档对象
 *
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/11/30
 * @time 20:32
 * @PROJECT_NAME file_saving_tool_backend
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PathMappingDoc {
    /**
     * ID
     */
    @Id
    @Field(index = false, type = FieldType.Integer)
    private Integer pathMappingId;
    /**
     * 本地路径
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String pathMappingLocalPath;
    /**
     * 文件夹id(外键)
     */
    @Field(type = FieldType.Integer)
    private Integer directoryInfoId;
    /**
     * 类型(1上传2下载3同步，删除为对应的负数)
     */
    @Field(type = FieldType.Integer)
    private Integer pathMappingType;
    /**
     * 客户端id(外键)
     */
    @Field(type = FieldType.Integer)
    private Integer clientId;
    /**
     * 用户对象id(外键)
     */
    @Field(type = FieldType.Integer)
    private Integer userId;
    /**
     * 创建时间
     */
    @Field(type = FieldType.Integer)
    private Integer createTime;
    /**
     * 更新时间
     */
    @Field(type = FieldType.Integer)
    private Integer updateTime;
}
