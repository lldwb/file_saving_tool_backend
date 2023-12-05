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
     * 路径
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String operationLogPath;
    /**
     * 操作类型(1添加，2删除，3修改)
     */
    @Field(type = FieldType.Integer)
    private Integer operationLogType;
    /**
     * 文件类型(1文件夹，2文件)
     */
    @Field(type = FieldType.Integer)
    private Integer operationLogFileType;
    /**
     * 用户id
     */
    @Field(type = FieldType.Integer)
    private Integer userId;
    /**
     * 文件id
     */
    @Field(type = FieldType.Integer)
    private Integer fileInfoId;
}
