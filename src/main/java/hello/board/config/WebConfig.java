package hello.board.config;

import hello.board.web.interceptor.LogInterceptor;
import hello.board.web.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.Model;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**") //  "/"하위에 전부 적용
                .excludePathPatterns("/css/**","/*.ico","/error");

        registry.addInterceptor(new LoginCheckInterceptor())
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**","/*.ico","/error",
                        "/","/member/add","/login","/logout","/board","/member/add/welcome",
                        "/board/find-by-writer-id/*","/board/find-by-title/*","/board/find-by-content/*","/board/find-by/*"
                        );
    }
}
