package top.lldwb.file.saving.tool.server;

import cn.hutool.core.date.DateUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTHeader;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.JWTValidator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class ServerApplicationTests {

    @Test
    void contextLoads() {
        String jwtStr = JWT.create()
                // 设置签发时间
                .setIssuedAt(DateUtil.date())
                // 设置过期时间
                .setExpiresAt(DateUtil.date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 15))
                .setKey("加盐".getBytes())
                .setPayload("userId",123)
                // 签名生成JWT字符串
                .sign();

        // 检测会话是否过期
        JWTValidator.of(jwtStr).validateDate(DateUtil.date());
        final JWT jwt = JWTUtil.parseToken(jwtStr);
        jwt.getHeader(JWTHeader.TYPE);

        log.info("jwt：{}",jwt.getPayload("userId"));
    }
}
