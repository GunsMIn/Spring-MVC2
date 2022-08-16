package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {
    private final MemberRepository memberRepository;
    private final SessionManager sessionManager;
    //홈에 왔을 때
   // @GetMapping("/")                         //로그인 안한 사용자도 들어와야하니까 false여야한다
    public String homeLogin(@CookieValue(name="memberId", required = false) Long memberId, Model model){
                                                                            //스프링이 자동 컨버팅해준다
        if(memberId==null){
            return "home";
        }
        Member loginMember = memberRepository.findById(memberId);
        if(loginMember==null){
            return "home";
        }
        model.addAttribute("member", loginMember);
        return "loginHome";
    }


    //세션을 찾아올때 유용하게 사용가능한 어노테이션
    @GetMapping("/")
    public String homeLoginSpring(@SessionAttribute(name=SessionConst.LOGIN_MEMBER,required = false) Member loginMember, Model model) {

        if(loginMember ==null){
            return "home";
        }
        //세션에 값이있으면 로그인된 홈페이지로 이동
        model.addAttribute("member", loginMember);
        return "loginHome";

    }






    //세션이용
    //@GetMapping("/")
    public String homeLoginV2(HttpServletRequest request, Model model) {
        //세션 관리자에 저장된 회원 정보 조회
        Member member = (Member)sessionManager.getSession(request);
        if (member == null) {
            return "home";
        }
        //로그인
        model.addAttribute("member", member);
        return "loginHome";
    }



    //@GetMapping("/")
    public String homeLoginV3(HttpServletRequest request, Model model) {

        //홈 화면에 처음들어오는 사람도 true로 하면 세션이 만들어지기깨문에 false로 해주어야한다.
        HttpSession session = request.getSession(false);
        if(session==null){
            return "home";
        }
       // Object loginMember = session.getAttribute(SessionConst.LOGIN_MEMBER);
        //아래처럼 캐스팅 진행
        Member loginMember = (Member)session.getAttribute(SessionConst.LOGIN_MEMBER);

        //세션에 회원 데이터가 없으면 home
        if(loginMember ==null){
            return "home";
        }
        //세션에 유지 되면 로그인된 홈페이지로 이동
        model.addAttribute("member", loginMember);
        return "loginHome";

    }



}