package top.lldwb.file.saving.tool.server.controller.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 进行登录会话的检测
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2024/1/4
 * @time 14:56
 * @PROJECT_NAME recruit
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userId = request.getParameter("userId");
//        if (userId != null && !"".equals(userId)) {
//            // 检测会话是否过期
//            JWTValidator.of(userId).validateDate(DateUtil.date());
//            final JWT jwt = JWTUtil.parseToken(userId);
//
//            jwt.getHeader(JWTHeader.TYPE);
//            request.setAttribute("userId",jwt.getPayload("userId"));
//        }
        return true;
    }
}
