package hello.login.web.filter;

import hello.login.web.SessionConst;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class MyLoginFilter implements Filter {

    private static final String[] whiteList= {"/","/members/add","/login","/logout","/css/*"};

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        if(checkWhiteList(requestURI)){ // 우선 여기들어오면 sessin값이 있는지 확인을 해주어야한다
            HttpSession session = httpRequest.getSession(false);
            if(session==null || session.getAttribute(SessionConst.LOGIN_MEMBER)==null){
                httpResponse.sendRedirect("/login?redirectURL="+requestURI);
                return;
            }
        }
        chain.doFilter(request,response);

    }

    @Override
    public void destroy() {

    }


    public boolean checkWhiteList(String requestURI){
        return !PatternMatchUtils.simpleMatch(whiteList,requestURI);
    }

}



