package com.hello.singleton;

import hello.AppConfig;

import hello.order.OrderServiceimpl;
import hello.repository.MemberRepository;
import hello.service.MemberServiceimpl;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class ConfigurationSingletonTest { //@Configuration 마크를 통해 코드상 싱글톤이 아닌것 같은게 싱글톤으로 동작함을 체크가능!

    @Test
    void configurationTest() { //@Configuration 제거시 memberRepository() 생성자 메소드 3번 호출되어 테스트 실패 ( 싱글톤 체크 테스트)
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceimpl memberService = ac.getBean("memberService", MemberServiceimpl.class);
        OrderServiceimpl orderService = ac.getBean("orderService", OrderServiceimpl.class);
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);

        System.out.println("memberRepository = " + memberRepository);
        System.out.println("memberService.getMemberRepository() = " + memberService.getMemberRepository());
        System.out.println("orderService.getMemberRepository() = " + orderService.getMemberRepository());

        assertThat(memberService.getMemberRepository()).isSameAs(memberRepository);
        assertThat(orderService.getMemberRepository()).isSameAs(memberRepository);
    }

    @Test
    void configurationDeep() {
        ApplicationContext ac =  new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig bean = ac.getBean(AppConfig.class);

        System.out.println("bean = " + bean.getClass());
    }
}
