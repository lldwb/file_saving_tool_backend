package top.lldwb.file.saving.tool.server.controller.common;

import top.lldwb.file.saving.tool.server.pojo.vo.ResultVO;

/**
 * 对返回值的封装，规范了成功返回和异常返回，屏蔽了项目冗余的报错细节
 *
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/11/28
 * @time 20:36
 * @PROJECT_NAME file_saving_tool_backend
 */
public abstract class BaseResponse {
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
        return success(200, data, "");
    }

    /**
     * 失败响应的封装 - 默认
     *
     * @param code
     * @param message
     * @param <T>
     * @return
     */
    public <T> ResultVO success(Integer code, String message) {
        return success(code, "", message);
    }

    /**
     * 失败响应的封装
     *
     * @param errorCode
     * @param <T>
     * @return
     */
    public <T> ResultVO success(ErrorCode errorCode) {
        return success(errorCode.getCode(), errorCode.getDescription(), errorCode.getMessage());
    }

    /**
     * 响应的封装 - 默认
     *
     * @param code
     * @param data
     * @param message
     * @param <T>
     * @return
     */
    public <T> ResultVO success(Integer code, T data, String message) {
        ResultVO vo = new ResultVO();
        vo.setCode(code);
        vo.setData(data);
        vo.setMessage(message);
        return vo;
    }
}
