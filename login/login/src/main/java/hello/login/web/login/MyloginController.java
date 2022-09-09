//package hello.login.web.login;
//
//import hello.login.domain.member.LoginService;
//import hello.login.domain.member.Member;
//import hello.login.domain.member.MemberRepository;
//import hello.login.web.login.form.LoginForm;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Controller;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletResponse;
//
//@Controller
//@Slf4j
//@RequiredArgsConstructor
//public class MyloginController {
//
//    private final LoginService loginService;
//    private final MemberRepository memberRepository;
//
//
//    @GetMapping("/login")
//    public String login(){
//        return "/login/loginForm";
//    }
//
//
//    @PostMapping
//    public String loginLogic(@Validated @ModelAttribute LoginForm form, BindingResult result, HttpServletResponse response){
//
//        if(result.hasErrors()){
//            return "login/loginForm";
//        }
//        //문제가없다면
//        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
//
//        if(loginMember==null){//login실패시
//            result.reject("loginFail","아이디나 비밀번호를 다시입력해주세요");
//            return "login/loginForm";
//        }
//        //로그인 성공시
//        Cookie cookie = new Cookie("memberId",String.valueOf(loginMember.getId()));
//        response.addCookie(cookie);
//        return "redirect:/";
//
//
//    }
//
//}
