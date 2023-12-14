package top.lldwb.file.saving.tool.server.service.user.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import top.lldwb.file.saving.tool.pojo.dto.UpdateMessage;
import top.lldwb.file.saving.tool.server.config.RabbitConfig;
import top.lldwb.file.saving.tool.server.config.RabbitUpdate;
import top.lldwb.file.saving.tool.server.config.RedisConfig;
import top.lldwb.file.saving.tool.server.dao.UserDao;
import top.lldwb.file.saving.tool.server.pojo.doc.UserDoc;
import top.lldwb.file.saving.tool.pojo.entity.User;
import top.lldwb.file.saving.tool.server.service.es.EsService;
import top.lldwb.file.saving.tool.server.service.user.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户实现类
 *
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/11/29
 * @time 15:53
 * @PROJECT_NAME file_saving_tool_backend
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final EsService esService;
    private final RedisTemplate<String, Object> redisTemplate;
    private final RabbitTemplate template;

    /**
     * 将实体类转换成文档对象
     *
     * @param user 实体类
     * @return 文档对象
     */
    private Document getDocument(User user) {
        // 文档对象
        Document document = Document.create();
        // 索引名称
        document.setIndex(RedisConfig.ES_INDEX + "user");
        // 文档id
        document.setId(String.valueOf(user.getUserId()));
        // 文档数据
        document.put("userName", user.getUserName());
        document.put("userEmail", user.getUserEmail());
        return document;
    }

    private UserDoc getUserDoc(User user) {
        return new UserDoc(user.getUserId(), user.getUserName(), user.getUserEmail());
    }

    @Override
    public void synchronization() {
        List<User> users = userDao.getUsers();
        users.forEach(user -> {
            // redis过期
            redisTemplate.delete(RedisConfig.REDIS_INDEX + "user:" + user.getUserId());

            esService.createIndex(UserDoc.class);
            esService.createMapping(UserDoc.class);

            // 将实体类转换成文档对象
            Document document = getDocument(user);
            //检查文档是否存在
            if (esService.docExists(document.getId(), document.getIndex())) {
                // 更新文档
                esService.updateDoc(document);
            } else {
                // 创建文档
                esService.createDoc(new UserDoc(user.getUserId(), user.getUserName(), user.getUserEmail()));
            }
        });
    }

    @Override
    public void addUser(User user) {
        // 数据库添加用户
        userDao.addUser(user);
        // 根据返回的用户ID获取用户的完整信息
        user = userDao.getUserByUserId(user.getUserId());

//        template.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitUpdate.ROUTING_KEY, UpdateMessage.getUpdateMessage(getUserDoc(user)));
//        // 创建文档
//        esService.createDoc(new UserDoc(user.getUserId(), user.getUserName(), user.getUserEmail()));
    }

    @Override
    public void updateUser(User user) {
        // 数据库更新用户
        userDao.updateUser(user);
        // 将实体类转换成文档对象
        Document document = getDocument(user);
//        template.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitUpdate.ROUTING_KEY, UpdateMessage.getUpdateMessage(getUserDoc(user)));
//        // 更新文档
//        esService.updateDoc(document);
        // redis过期
        redisTemplate.delete(RedisConfig.REDIS_INDEX + "user:" + user.getUserId());
    }

    @Override
    public List<User> getUsers(String searchParam, Integer pageNum, Integer pageSize) {
        List<User> list = new ArrayList<>();
        List<UserDoc> docList;
        if (searchParam == null || "".equals(searchParam)) {
            docList = esService.listNames(UserDoc.class, pageNum, pageSize);
        } else {
            docList = esService.listNamesByName(UserDoc.class, pageNum, pageSize, searchParam, "userName");
        }
        docList.forEach(userDoc -> {
            User user = new User();
            user.setUserId(userDoc.getUserId());
            user.setUserName(userDoc.getUserName());
            user.setUserEmail(userDoc.getUserEmail());
            list.add(user);
        });
        return list;
    }

    @Override
    public User getUserByUserId(Integer userId) {
        return null;
    }
}
