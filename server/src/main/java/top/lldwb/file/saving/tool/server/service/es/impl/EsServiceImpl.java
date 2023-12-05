package top.lldwb.file.saving.tool.server.service.es.impl;

import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.termvectors.Term;
import co.elastic.clients.util.ObjectBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Service;
import top.lldwb.file.saving.tool.server.service.es.EsService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/11/29
 * @time 15:17
 * @PROJECT_NAME file_saving_tool_backend
 */
@Service
@RequiredArgsConstructor
public class EsServiceImpl implements EsService {
    private final ElasticsearchOperations operations;

    @Override
    public void createIndex(Class<?> doType) {
        // 判断索引是否创建，如果没有则通过
        if (!existsIndex(doType)) {
            operations.indexOps(doType).create();
        }
    }

    @Override
    public void deleteIndex(Class<?> doType) {
        // 判断索引是否创建，如果有则通过
        if (existsIndex(doType)) {
            operations.indexOps(doType).delete();
        }
    }

    @Override
    public boolean existsIndex(Class<?> doType) {
        return operations.indexOps(doType).exists();
    }

    @Override
    public void createMapping(Class<?> doType) {
        operations.indexOps(doType).putMapping();
    }

    @Override
    public <T> void createDoc(T doc) {
        operations.save(doc);
    }

    @Override
    public boolean docExists(String id, String indexName) {
        return operations.exists(id, IndexCoordinates.of(indexName));
    }

    @Override
    public void updateDoc(Document document) {
        //构建更新查询
        UpdateQuery query = UpdateQuery.builder(document.getId()).withDocument(document).build();
        //更新文档
        operations.update(query, IndexCoordinates.of(document.getIndex()));
    }

    @Override
    public <T> List<T> listNames(Class<T> docType, Integer pageNum, Integer pageSize) {
        // 创建一个CriteriaQueryBuilder对象，用于构建查询条件
        CriteriaQueryBuilder builder = new CriteriaQueryBuilder(new Criteria());
        // 使用Pageable参数构建分页
        builder.withPageable(PageRequest.of(pageNum, pageSize));
        // 使用构建的查询条件搜索文档
        SearchHits<T> hits = operations.search(builder.build(), docType);
        // 创建一个空的List用于存放搜索结果
        List<T> list = new ArrayList<>();
        // 遍历搜索结果，将文档内容添加到list中
        hits.forEach(tSearchHit -> list.add(tSearchHit.getContent()));
        // 返回搜索结果
        return list;
    }

    @Override
    public <T> List<T> listNamesByName(Class<T> docType, Integer pageNum, Integer pageSize, String searchParam, String field) {
        // 创建一个CriteriaQueryBuilder对象，用于构建查询条件
        CriteriaQueryBuilder builder = new CriteriaQueryBuilder(new Criteria(field).is(searchParam));
        // 使用Pageable参数构建分页
        builder.withPageable(PageRequest.of(pageNum, pageSize));
        // 使用构建的查询条件搜索文档
        SearchHits<T> hits = operations.search(builder.build(), docType);
        // 创建一个空的List用于存放搜索结果
        List<T> list = new ArrayList<>();
        // 遍历搜索结果，将文档内容添加到list中
        hits.forEach(tSearchHit -> list.add(tSearchHit.getContent()));
        // 返回搜索结果
        return list;
    }

    @Override
    public <T> List<T> listNamesByNames(Class<T> docType, Integer pageNum, Integer pageSize, String searchParam, String... fields) {
        // 创建一个NativeQueryBuilder对象
        NativeQueryBuilder builder = new NativeQueryBuilder();
        // 设置分页信息
        builder.withPageable(PageRequest.of(pageNum, pageSize));
        // 设置查询参数
        builder.withQuery(q -> {
            return q.multiMatch(m -> m.fields(Arrays.asList(fields)).query(searchParam));
        });
        // 执行查询
        SearchHits<T> hits = operations.search(builder.build(), docType);
        // 创建一个空的ArrayList对象
        List<T> list = new ArrayList<>();
        // 遍历查询结果，将每一条结果添加到ArrayList中
        hits.forEach(tSearchHit -> list.add(tSearchHit.getContent()));
        // 返回查询结果
        return list;
    }

    @Override
    public <T> List<T> listNamesByNames(Class<T> docType, Integer pageNum, Integer pageSize, Map<String, String> Params) {
        // 创建一个NativeQueryBuilder对象
        NativeQueryBuilder queryBuilder = new NativeQueryBuilder();
        // 设置分页信息
        queryBuilder.withPageable(PageRequest.of(pageNum, pageSize));
        // 添加 query
        queryBuilder.withQuery(
                // 创建一个构建器，用于返回ObjectBuilder的子类
                builder -> {
                    // 创建BoolQuery.Builder
                    builder.bool(
                            // 通过BoolQuery.Builder返回BoolQuery
                            boolBuild -> {
                                // 设置子查询
                                List<Query> shouldList = new ArrayList<>();
                                boolBuild.should(shouldList);
                                // 设置must(检索的结果必须满足must子句)
                                List<Query> mustList = new ArrayList<>();

                                // 添加 query
                                for (String field : Params.keySet()) {
                                    // 遍历字段
                                    mustList.add(Query.of(mustBuild -> {
                                                // match查询构建器
                                                mustBuild.match(
                                                        m -> m.field(field).query(Params.get(field))
                                                );
                                                return mustBuild;
                                            })
                                    );
                                }
                                boolBuild.must(mustList);
                                return boolBuild;
                            });
                    return builder;
                });
        SearchHits<T> hits = operations.search(queryBuilder.build(), docType);
        List<T> list = new ArrayList<>();
        hits.forEach(tSearchHit -> list.add(tSearchHit.getContent()));
        return list;
    }
}
