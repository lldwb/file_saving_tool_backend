package top.lldwb.file.saving.tool.server.controller;

import top.lldwb.file.saving.tool.server.vo.ResultVO;

/**
 * 它是所有的用户定义Servlet的父类 - 把一些公共操作的代码，抽象提取出来，在父类中定义，子类可以直接使用
 *
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/11/28
 * @time 20:36
 * @PROJECT_NAME file_saving_tool_backend
 */
public abstract class BaseController {
    /**
     * 成功响应的封装 - 默认
     *
     * @return 视图响应对象
     */
    public ResultVO success() {
        return new ResultVO();
    }

    /**
     * 成功响应的封装 - 默认
     *
     * @param data 数据
     * @return 视图响应对象
     */
    public <T> ResultVO success(T data) {
        ResultVO vo = new ResultVO();
        vo.setData(data);
        return vo;
    }
}
