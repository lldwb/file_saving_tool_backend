package top.lldwb.file.saving.tool.server.dao;

import top.lldwb.file.saving.tool.pojo.entity.PathMapping;

import java.util.List;

/**
 * 路径映射dao
 *
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/11/30
 * @time 20:33
 * @PROJECT_NAME file_saving_tool_backend
 */
public interface PathMappingDao {
    /**
     * 根据路径映射ID获取路径映射
     *
     * @param pathMappingId
     * @return
     */
    PathMapping getPathMappingByPathMappingId(Integer pathMappingId);

    /**
     * 获取所有路径映射
     *
     * @return
     */
    List<PathMapping> getUsers();

    /**
     * 添加路径映射
     *
     * @param pathMapping
     */
    void addPathMapping(PathMapping pathMapping);

    /**
     * 更新路径映射
     *
     * @param pathMapping
     */
    void updatePathMapping(PathMapping pathMapping);

    /**
     * 删除路径映射
     *
     * @param pathMappingId
     */
    void deletePathMapping(Integer pathMappingId);
}
