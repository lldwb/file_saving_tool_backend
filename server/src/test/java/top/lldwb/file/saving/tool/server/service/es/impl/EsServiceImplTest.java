package top.lldwb.file.saving.tool.server.service.es.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.lldwb.file.saving.tool.server.pojo.doc.UserDoc;
import top.lldwb.file.saving.tool.server.service.es.EsService;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class EsServiceImplTest {
    @Autowired
    private EsService esService;
    @Test
    void listNames() {
        esService.listNames(UserDoc.class,0,2).forEach(userDoc -> System.out.println(userDoc));
    }

    @Test
    void listNamesByName() {
        esService.listNamesByName(UserDoc.class,0,2,"lldwb","userName").forEach(userDoc -> System.out.println(userDoc));
    }

    @Test
    void listNamesByNames() {
//        esService.listNamesByNames(UserDoc.class,0,2,"lldwb","userName").forEach(userDoc -> System.out.println(userDoc));

        Map<String,String> map1 = new HashMap<>();
        map1.put("userName","lldwb");
        esService.listNamesByNames(UserDoc.class,0,2,map1).forEach(userDoc -> System.out.println(userDoc));
        Map<String,String> map2 = new HashMap<>();
        map2.put("userEmail","qq");
        esService.listNamesByNames(UserDoc.class,0,2,map2).forEach(userDoc -> System.out.println(userDoc));
    }
}