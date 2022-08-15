package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {
    private final MemberRepository memberRepository;

    @GetMapping("/")                         //로그인 안한 사용자도 들어와야하니까 false여야한다
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

}