package top.lldwb.file.saving.tool.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2024/1/1
 * @time 19:20
 * @PROJECT_NAME file_saving_tool_backend
 */
@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {
    /**
     * 跨域配置
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*")
                .exposedHeaders("*");
        // 跨域是是否允许传递cookie，默认是不允许的
        //.allowCredentials(true)
    }
}
