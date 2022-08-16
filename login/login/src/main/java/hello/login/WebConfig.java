package hello.login;

import hello.login.web.filter.LogFilter;
import hello.login.web.filter.LoginCheckFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class WebConfig {

    @Bean
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

  /*  @Bean
    public FilterRegistrationBean loginCheckFilter(){
        FilterRegistrationBean filterFactory = new FilterRegistrationBean();
        filterFactory.setFilter(new LoginCheckFilter());
        filterFactory.setOrder(2);
        filterFactory.addUrlPatterns("/*");
        return filterFactory;
    }*/

    @Bean
    public FilterRegistrationBean loginCheckFilter(){
        FilterRegistrationBean<Filter> filterFactory = new FilterRegistrationBean<>();
        filterFactory.setFilter(new LoginCheckFilter());
        filterFactory.setOrder(2);
        filterFactory.addUrlPatterns("/*");
        return filterFactory;
    }



}
