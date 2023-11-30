package top.lldwb.file.saving.tool.server.doc;

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
@Document(indexName = RedisConfig.ES_INDEX + "user", createIndex = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDoc {

    @Id
    @Field(index = false, type = FieldType.Integer)
    private Integer userId;
    /**
     * 用户名
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String userName;
    /**
     * 用户邮箱
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String userEmail;
}
