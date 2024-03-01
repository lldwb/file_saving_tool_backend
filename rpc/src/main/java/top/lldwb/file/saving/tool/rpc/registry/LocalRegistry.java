package top.lldwb.file.saving.tool.rpc.registry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 客户端服务注册中心(本地)
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2024/3/1
 * @time 8:47
 * @PROJECT_NAME file_saving_tool_backend
 */
public class LocalRegistry {
    /**
     * 客户端服务信息存储(秘钥,会话)
     */
    private static Map<String, Class<?>> map = new ConcurrentHashMap<>();

    /**
     * 获取服务
     * @return
     */
    public static Map<String, Class<?>> getMap() {
        return map;
    }

    /**
     * 注册服务
     * @param map
     */
    public static void setMap(Map<String, Class<?>> map) {
        LocalRegistry.map = map;
    }

    public static void remove(String name){
        map.remove(name);
    }
}
