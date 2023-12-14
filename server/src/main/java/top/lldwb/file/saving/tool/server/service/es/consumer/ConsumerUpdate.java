package top.lldwb.file.saving.tool.server.service.es.consumer;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.stereotype.Service;
import top.lldwb.file.saving.tool.pojo.dto.UpdateMessage;
import top.lldwb.file.saving.tool.server.config.RabbitUpdate;
import top.lldwb.file.saving.tool.server.config.RedisConfig;
import top.lldwb.file.saving.tool.server.pojo.doc.UserDoc;
import top.lldwb.file.saving.tool.server.service.es.EsService;
import top.lldwb.file.saving.tool.server.service.sync.EsSyncService;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/1
 * @time 11:11
 * @PROJECT_NAME file_saving_tool_backend
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ConsumerUpdate {
    private final EsService esService;
    private final ElasticsearchOperations template;
//    private final EsSyncService esSyncService;

    @RabbitListener(queues = RabbitUpdate.QUEUE_NAME)
    public void esUpdate(UpdateMessage updateMessage) throws IntrospectionException, InvocationTargetException, IllegalAccessException, ClassNotFoundException {
        log.info("开始消费"+updateMessage.toString());
        template.save(Convert.convert(Class.forName(updateMessage.getClazz()),updateMessage.getData()));
        log.info("消费成功"+updateMessage.toString());
    }
}
