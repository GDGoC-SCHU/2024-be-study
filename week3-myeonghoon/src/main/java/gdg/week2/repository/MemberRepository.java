package gdg.week2.repository;

import org.springframework.stereotype.Component;

@Component
public class MemberRepository {

    public String logic(){
        return "memberService 로직 수행";
    }
}
