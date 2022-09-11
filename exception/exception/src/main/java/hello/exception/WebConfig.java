package hello.exception;

import hello.exception.filter.LogFilter;
import hello.exception.intercepter.LogIntercepter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new LogIntercepter())
                .order(1)
                .addPathPatterns("/**")  // 인터셉터는 spring관련기술이라 이렇게 빼주어야한다
                .excludePathPatterns("/css/**", "*/ico", "/error","error-page/**");
    }

    @Bean
    public FilterRegistrationBean logFilter(){

        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();

        filterRegistrationBean.setFilter(new LogFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("/*");
        //이 필터는 request랑 error 2가지경우에 사용이된다.
        filterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST,DispatcherType.ERROR);
        return filterRegistrationBean;
    }
}
