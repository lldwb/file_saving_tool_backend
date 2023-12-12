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
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/4
 * @time 15:27
 * @PROJECT_NAME file_saving_tool_backend
 */
@Document(indexName = RedisConfig.ES_INDEX + "operationLog", createIndex = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperationLogDoc {
    /**
     * ID
     */
    @Id
    @Field(index = false, type = FieldType.Integer)
    private Integer operationLogId;
    /**
     * 名称
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String operationLogName;
    /**
     * minio路径
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String operationLogPath;
    /**
     * 操作类型(1添加，2修改，3删除，恢复为对应的负数)
     */
    @Field(type = FieldType.Integer)
    private Integer operationLogType;
    /**
     * 字节大小
     */
    @Field(type = FieldType.Long)
    private Long operationLogSize;
    /**
     * 文件id(外键)
     * 如果为0则操作文件夹
     */
    @Field(type = FieldType.Integer)
    private Integer fileInfoId;
    /**
     * 文件夹id(外键)
     */
    @Field(type = FieldType.Integer)
    private Integer directoryInfoId;
    /**
     * 用户id
     */
    @Field(type = FieldType.Integer)
    private Integer userId;
    /**
     * 创建时间
     */
    @Field(type = FieldType.Integer)
    private Integer createTime;
}
