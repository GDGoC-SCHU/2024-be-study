package gdg.week2.config;

import gdg.week2.repository.MemberRepository;
import gdg.week2.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class Appconfig {

    @Bean
    public MemberService memberservice() {
        return new MemberService(memberRepository()); // MemberRepository 주입
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemberRepository();
    }
}

