package top.lldwb.file.saving.tool.server.consumer;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.stereotype.Service;
import top.lldwb.file.saving.tool.server.config.RabbitUpdate;
import top.lldwb.file.saving.tool.server.config.RedisConfig;
import top.lldwb.file.saving.tool.server.pojo.doc.UserDoc;
import top.lldwb.file.saving.tool.server.pojo.dto.UpdateMessage;
import top.lldwb.file.saving.tool.server.service.es.EsService;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/1
 * @time 11:11
 * @PROJECT_NAME file_saving_tool_backend
 */
@Service
@RequiredArgsConstructor
public class ConsumerUpdate {
    private final EsService esService;

    @RabbitListener(queues = RabbitUpdate.QUEUE_NAME)
    public <T> void esUpdate(UpdateMessage updateMessage) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        // 手动创建索引
        esService.createIndex(UserDoc.class);
        // 创建mapping
        esService.createMapping(UserDoc.class);

        // 将实体类转换成文档对象
        Document document = getDocument(updateMessage.getData(), updateMessage.getId());
        //检查文档是否存在
        if (esService.docExists(document.getId(), document.getIndex())) {
            // 更新文档
            esService.updateDoc(document);
        } else {
            // 创建文档
            esService.createDoc(updateMessage.getData());
        }
    }


    /**
     * 将实体类转换成文档对象
     *
     * @param t 实体类
     * @return 文档对象
     */
    private <T> Document getDocument(T t, String id) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        // 文档对象
        Document document = Document.create();
        // 索引名称
        document.setIndex(RedisConfig.ES_INDEX + t.getClass().getSimpleName());
        // 文档id
        document.setId(id);
        // 文档数据
        for (Field field : t.getClass().getDeclaredFields()) {
            String fieldName = field.getName();
            // 创建属性描述器,用于调用get和set方法
            PropertyDescriptor descriptor = new PropertyDescriptor(fieldName, t.getClass());
            // 获取读方法
            Method method = descriptor.getReadMethod();
            // 获取值
            Object value = method.invoke(t);
            document.put(fieldName, value);
        }
        return document;
    }
}
