/*
package hello.login.web.login.form;

import hello.login.domain.member.LoginService;
import hello.login.domain.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MyLogonController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String loginform(@ModelAttribute("loginForm") LoginForm form){
        return "login/loginForm";
    }

    @PostMapping("/login") // 세션제작을위해 HttpServletRequest필요
    public String login(@Validated @ModelAttribute LoginForm form, BindingResult result, HttpServletRequest request){
        //검증 실패시
        if(result.hasErrors()){
            return "login/loginForm";
        }
        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
        if(loginMember == null){
            result.reject("loginFail","아이디와 비밀번호를 다시 입력해주세요");
            return "login/loginForm";
        }

        HttpSession session = request.getSession();
        session.setAttribute("loginMember",loginMember); //세션에 저장

        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session!=null){
            session.invalidate();
        }
        return "redirect:/";
    }

}
*/
