package gdg.week2.service;

import gdg.week2.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) { // 생성자에서 의존성 주입
        this.memberRepository = memberRepository;
    }

    public String logic(){
        String logic = memberRepository.logic();
        return logic + "memberService 로직 수행";
    }
}
