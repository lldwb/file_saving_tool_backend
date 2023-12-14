package top.lldwb.file.saving.tool.server.service.es.consumer;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ClassUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import top.lldwb.file.saving.tool.pojo.dto.UpdateMessage;
import top.lldwb.file.saving.tool.server.config.RabbitConfig;
import top.lldwb.file.saving.tool.server.config.RabbitUpdate;
import top.lldwb.file.saving.tool.server.pojo.doc.UserDoc;
import top.lldwb.file.saving.tool.server.service.es.EsService;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/14
 * @time 11:09
 * @PROJECT_NAME file_saving_tool_backend
 */
@Slf4j
@SpringBootTest
class ConsumerUpdateTest {
    //    @Autowired
//    private ConsumerUpdate consumerUpdate;
    @Autowired
    private EsService esService;
    @Autowired
    private ConsumerUpdate consumerUpdate;

    @Autowired
    private ElasticsearchOperations template;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    void esUpdate() throws IntrospectionException, ClassNotFoundException, InvocationTargetException, IllegalAccessException {

        for (Class<?> clazz : ClassUtil.scanPackage("top.lldwb.file.saving.tool.server.pojo.doc")) {
            if (clazz.isAnnotationPresent(Document.class)) {
                log.info("class：" + clazz.getName());
                if (!template.indexOps(clazz).exists()) {
                    template.indexOps(clazz).create();
                    //创建文档映射
                    template.indexOps(clazz).putMapping();
                } else {
                    template.indexOps(clazz).delete();
                    template.indexOps(clazz).create();
                    //创建文档映射
                    template.indexOps(clazz).putMapping();
                }
            }
        }

        UserDoc userDoc = new UserDoc();
        userDoc.setUserId(1);
        userDoc.setUserEmail("lldwb");
        userDoc.setUserName("lldwb");
////        template.save(userDoc);

        UpdateMessage updateMessage = new UpdateMessage();
        updateMessage.setData(userDoc);
        updateMessage.setClazz(userDoc.getClass().getName());

//        consumerUpdate.esUpdate(updateMessage);

//        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitUpdate.QUEUE_NAME, updateMessage);

        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitUpdate.QUEUE_NAME, UpdateMessage.getUpdateMessage(userDoc));
        while (true){

        }
    }
}