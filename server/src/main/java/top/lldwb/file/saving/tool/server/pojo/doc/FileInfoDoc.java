package top.lldwb.file.saving.tool.server.pojo.doc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import top.lldwb.file.saving.tool.server.config.RedisConfig;

/**
 * 用户文档对象
 *
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/11/29
 * @time 14:58
 * @PROJECT_NAME file_saving_tool_backend
 */
@Document(indexName = RedisConfig.ES_INDEX + "file_info", createIndex = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileInfoDoc {
    /**
     * ID
     */
    @Id
    @Field(index = false, type = FieldType.Integer)
    private Integer fileInfoId;
    /**
     * 名称
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String fileInfoName;
    /**
     * minio路径(SHA-256)
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String fileInfoPath;
    /**
     * 状态(1为存在，-1删除，0文件夹[只存在es中])
     */
    private Integer fileInfoState;
    /**
     * 文件大小
     */
    @Field(type = FieldType.Long)
    private Long fileInfoSize;
    /**
     * 文件夹id(父文件夹id)
     */
    @Field(type = FieldType.Integer)
    private Integer directoryInfoId;
    /**
     * 用户id
     */
    @Field(type = FieldType.Integer)
    private Integer userId;
    /**
     * 更新时间
     */
    @Field(type = FieldType.Integer)
    private Integer updateTime;
}
