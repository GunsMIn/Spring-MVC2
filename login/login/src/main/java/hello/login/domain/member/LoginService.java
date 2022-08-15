package hello.login.domain.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final MemberRepository memberRepository;


    /* return이 member를 반환하면 로그인 성공
     * return이 null일시 로그인 실패
     * */
    public Member login(String loginId, String password) {

        Optional<Member> byLoginId = memberRepository.findByLoginId(loginId);
        return byLoginId.filter(m -> m.getPassword().equals(password))
                .orElse(null);


        /*Optional<Member> byLoginId = memberRepository.findByLoginId(loginId);
        Member member = byLoginId.get();
        if(member.getPassword().equals(password)){
            return member;
        }
        return null;*/
        //위의 식을 람다로 바꾸면
    }



}