package top.lldwb.file.saving.tool.server.service.sync;

import cn.hutool.core.util.ClassUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/14
 * @time 9:45
 * @PROJECT_NAME file_saving_tool_backend
 */
//@Service
@RequiredArgsConstructor
public class EsSyncService {
    private final String PATH="top.lldwb.file.saving.tool.server.pojo.doc";

    private final ElasticsearchOperations template;

    /**
     * 扫描Doc包的所有Doc类并初始化index和mapping
     */
    @PostConstruct
    public void initIndex() {
        for (Class<?> clazz: ClassUtil.scanPackage(PATH)){
            if (clazz.isAnnotationPresent(Document.class)){
                if(!template.indexOps(clazz).exists()) {
                    template.indexOps(clazz).create();
                    //创建文档映射
                    template.indexOps(clazz).putMapping();
                }
            }
        }
    }

    /**
     * 同步添加或更新
     * @param list
     */
    public void saveOrUpdate(List<?> list) {
        list.forEach(template::save);
    }

    /**
     * 同步删除
     * @param list
     */
    public void delete(List<?> list) {
        list.forEach(template::delete);
    }
}
