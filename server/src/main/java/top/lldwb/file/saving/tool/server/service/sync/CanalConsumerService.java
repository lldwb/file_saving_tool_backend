package top.lldwb.file.saving.tool.server.service.sync;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import top.lldwb.file.saving.tool.pojo.dto.SyncMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/14
 * @time 9:50
 * @PROJECT_NAME file_saving_tool_backend
 */
//@Service
@Slf4j
@RequiredArgsConstructor
public class CanalConsumerService {

    /**
     * 注入同步服务
     */
    private final EsSyncService syncService;

//    @RabbitListener(queues = RabbitConfig.QUEUE_NAME)
    public void resolve(SyncMessage message) {
        System.out.println(message);
        if("user_info".equals(message.getTable())) {
            //将message转换为文档对象
            List<?> list = convertData(message.getData());
            if("INSERT".equals(message.getType()) || "UPDATE".equals(message.getType())) {
                log.info("同步添加或更新操作");
                syncService.saveOrUpdate(list);
            }else if("DELETE".equals(message.getType())) {
                log.info("同步删除操作");
                syncService.delete(list);
            }
        }
    }

    /**
     * 数据转换
     * @return
     */
    private List<?> convertData(List<?> list) {
        List<?> newList = new ArrayList<>();
//        list.forEach(userData -> {
//            UserDoc doc = new UserDoc();
//            //实现bean拷贝，将userDao拷贝到UserDoc对象中(注意：字段名和类型要相同)
//            BeanUtils.copyProperties(userData, doc);
//            //将拷贝好的doc放入新集合汇总
//            newList.add(doc);
//        });
        return newList;
    }
}