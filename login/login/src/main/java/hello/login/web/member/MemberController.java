package hello.login.web.member;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberRepository memberRepository;

    //회원가입 form이동
    @GetMapping("/add")
    public String addForm(@ModelAttribute("member") Member member){
        return "members/addMemberForm";
    }


    //회원 가입 처리
    @PostMapping("/add")        //Member 클래스에서 설정해준 것중에 error가 발생하면 bindingResult에 담긴다.
    public String save(@Validated @ModelAttribute Member member, BindingResult bindingResult){
        if(bindingResult.hasErrors()){ // 에러가 발생하면 다시 회원가입 form으로 이동시켜준다.
            return "members/addMemberForm";
        }
        memberRepository.save(member);
        return "redirect:/"; // 회원가입이 성공하게 된다면 홈화면으로 보내줄 것이다.
    }




}
