package hello.login.web.filter;

import hello.login.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
public class LoginCheckFilter implements Filter {//javax.servlet.Filter

    //인증 체크가 필요없는 uri들
    private static final String[] whitelist = {"/","/members/add","/login","/logout","/css/*"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try {
            log.info("인증체크 필터 시작 {}",requestURI);

            if(isLoginCheckPath(requestURI)){ // true이면 이 로직을 수행한다.
                log.info("인증 체크 로직 실행 {}",requestURI);
                HttpSession session = httpRequest.getSession(false);
                if(session==null || session.getAttribute(SessionConst.LOGIN_MEMBER)==null){
                    //session이없거나 로그인한 상태가 아니라면 미인증 사용자이다. 그래서 로그인으로 다시보낸다.
                    httpResponse.sendRedirect("/login?redirectURL="+requestURI);
                    //위처럼 해준이유는 만약 로그인 안한 사용자가 들어가고싶어했던 페이지를 로그인 후에 그 페이지로 이동시키기위해서
                    return ;//여기가 중요, 미인증 사용자는 다음으로 진행하지 않고 끝!
                }
            }
                chain.doFilter(request,response); // 중요! 다음 필터로 넘겨주기위해 이것이 없으면 동작안한다
        }catch (Exception e){
            throw e;
        }finally {
           log.info("인증 테크 필터 종료 {}",requestURI);
        }

    }

    /*
    * 화이트 리스트의 경우 인증체크 x
    * */
    private boolean isLoginCheckPath(String requestURI){
        return !PatternMatchUtils.simpleMatch(whitelist, requestURI);
        //여기서 false를 반환해 인증체크를 하지 않는다.
        // 위 return 은 !가 붙어, 2개 Match가 같지 않으면 true가 반환됨.
    }

}
