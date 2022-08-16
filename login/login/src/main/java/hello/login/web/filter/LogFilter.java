package hello.login.web.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

/*
* 중요 !!!!! 서블릿 필터를 사용하려면  WebConfig에서 등록을 해줘야한다
* */
@Slf4j
public class LogFilter implements Filter{//javax.servlet.FILTER
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("로그 필터 초기화");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        log.info("로그 필터 do filter");
        //다운 캐스팅을 해줘야 함
        HttpServletRequest httptRequest = (HttpServletRequest) request;
        String requestURI = httptRequest.getRequestURI();
        String uuid = UUID.randomUUID().toString();

        try{
           log.info("REQUEST[{}][{}]",uuid,requestURI);
           chain.doFilter(request,response); //중요! 다음 필터가 있으면 다음필터 호출 없으면 바로 서블릿 호출
        }catch (Exception e){
            throw e;
        }finally {
            log.info("RESPONSE[{}][{}]",uuid,requestURI);
        }

    }

    @Override
    public void destroy() {
        log.info("로그 필터 종료");
    }
}
