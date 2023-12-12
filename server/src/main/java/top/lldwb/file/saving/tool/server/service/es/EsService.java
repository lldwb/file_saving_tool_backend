package top.lldwb.file.saving.tool.server.service.es;

import org.springframework.data.elasticsearch.core.document.Document;

import java.util.List;
import java.util.Map;

/**
 * es操作接口
 *
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/11/29
 * @time 15:10
 * @PROJECT_NAME file_saving_tool_backend
 */
public interface EsService {
    /**
     * 手动创建索引
     * 根据Class类上的@Ducument注解指定的index来创建索引
     */
    void createIndex(Class<?> doType);

    /**
     * 删除索引
     *
     * @param doType
     */
    void deleteIndex(Class<?> doType);

    /**
     * 判断索引是否创建，如果有则返回true
     *
     * @param doType
     * @return
     */
    boolean existsIndex(Class<?> doType);

    /**
     * 创建mapping
     */
    void createMapping(Class<?> doType);

    /**
     * 创建文档
     *
     * @param doc 文档对象
     * @param <T> 文档对象的类型
     */
    <T> void createDoc(T doc);

    /**
     * 判断文档是否存在
     *
     * @param id        文档id
     * @param indexName 目标索引
     * @return
     */
    boolean docExists(String id, String indexName);

    /**
     * 更新文档
     *
     * @param document 文档对象
     */
    void updateDoc(Document document);

    /**
     * 查询所有的文档记录
     *
     * @param docType  文档的Class对象
     * @param pageNum  分页页数
     * @param pageSize 分页长度
     * @param <T>      Doc对象
     * @return Doc对象集合
     */
    <T> List<T> listNames(Class<T> docType, Integer pageNum, Integer pageSize);

    /**
     * 根据条件查询文档记录(match 单条件单字段)
     *
     * @param docType     文档的Class对象
     * @param pageNum     分页页数
     * @param pageSize    分页长度
     * @param searchParam 查询参数
     * @param field      检索的字段集合
     * @param <T>         Doc对象
     * @return Doc对象集合
     */
    <T> List<T> listNamesByName(Class<T> docType, Integer pageNum, Integer pageSize, String searchParam, String field);

    /**
     * 根据条件查询多个字段的文档记录(multi_Match 单条件多字段)
     *
     * @param docType     文档的Class对象
     * @param pageNum     分页页数
     * @param pageSize    分页长度
     * @param searchParam 查询参数
     * @param fields      检索的字段
     * @param <T>         Doc对象
     * @return Doc对象集合
     */
    <T> List<T> listNamesByNames(Class<T> docType, Integer pageNum, Integer pageSize, String searchParam, String... fields);

    /**
     * 根据条件查询多个字段的文档记录
     *
     * @param docType  文档的Class对象
     * @param pageNum  分页页数
     * @param pageSize 分页长度
     * @param Params   <检索的字段,查询参数>集合
     * @param <T>      Doc对象
     * @return Doc对象集合
     */
    <T> List<T> listNamesByNames(Class<T> docType, Integer pageNum, Integer pageSize, Map<String, Object> Params);
}
