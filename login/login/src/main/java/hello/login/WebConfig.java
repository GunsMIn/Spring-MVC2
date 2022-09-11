package hello.login;

import hello.login.web.filter.LogFilter;

import hello.login.web.filter.LoginCheckFilter;
import hello.login.web.intercepter.LogInterceptor;

import hello.login.web.intercepter.LoginCheckInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;
@Configuration
public class WebConfig implements WebMvcConfigurer {// 스프링 인터셉터인경우에 implements WebMvcConfigurer를 사용한다


    //인터셉터
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/*.ico", "/error"); // 이 경로는 인터셉터 먹이지마 하는 것

        //로그인 인증 필터
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(2)
                .addPathPatterns("/**") // 모든 url에서 인증체크를 하는데 밑의 url은 빼주겠다
                .excludePathPatterns("/","/members/add","/login","/logout","/*.ico","/error");

        /*registry.addInterceptor(new LogInterceptor()) : 인터셉터를 등록한다.
        order(1) : 인터셉터의 호출 순서를 지정한다. 낮을 수록 먼저 호출된다.
        addPathPatterns("/**") : 인터셉터를 적용할 URL 패턴을 지정한다.
        excludePathPatterns("/css/**", "/*.ico", "/error") : 인터셉터에서 제외할 패턴을 지정한다
        * */
    }

    //밑에서 부터는 서블릿 필터를 빈으로 등록

    //@Bean
    public FilterRegistrationBean logFilter(){
        FilterRegistrationBean<Filter> filterFactory = new FilterRegistrationBean<>();
        filterFactory.setFilter(new LogFilter());//필터 등록
        filterFactory.setOrder(1); // 필터의 순서 (체인으로 여러개 들어갈 수 있으니까)
        filterFactory.addUrlPatterns("/*"); // 어떤 urI패턴에다가 적용할 것인가

        return filterFactory;
        /*setFilter(new LogFilter()) : 등록할 필터를 지정한다.
        setOrder(1) : 필터는 체인으로 동작한다. 따라서 순서가 필요하다. 낮을 수록 먼저 동작한다.
        addUrlPatterns("/*") : 필터를 적용할 URL 패턴을 지정한다. 한번에 여러 패턴을 지정할 수 있다*/
    }

   //@Bean
    public FilterRegistrationBean loginCheckFilter(){
        FilterRegistrationBean<Filter> filterFactory = new FilterRegistrationBean<>();
        filterFactory.setFilter(new LoginCheckFilter());
        filterFactory.setOrder(2);
        filterFactory.addUrlPatterns("/*");
        return filterFactory;
    }





}
