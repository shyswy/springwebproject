package com.hello.beanfind;


import hello.AppConfig;
import hello.service.MemberService;
import hello.service.MemberServiceimpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class ApplicationContextBasicFindFirst {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("빈 이름으로 조회")
    void findBeanByName() {
        MemberService memberService = ac.getBean("memberService", MemberService.class);
        assertThat(memberService).isInstanceOf(MemberServiceimpl.class);
    }


    @Test
    @DisplayName("빈을 타입으로만 조회")
    void findBeanByType() {
        MemberService memberService = ac.getBean(MemberService.class);
        assertThat(memberService).isInstanceOf(MemberServiceimpl.class);
    }

    @Test
    @DisplayName("빈 이름으로 조회")
    void findBeanByImplements() {
        MemberService memberService = ac.getBean("memberService", MemberServiceimpl.class);
        assertThat(memberService).isInstanceOf(MemberServiceimpl.class); //isinstanceOF >> 같은 타입?? 체크
    }

    @Test
    @DisplayName("등록되지 않은 빈 조회")
    void findBeanByWithoutBean() {
        assertThatThrownBy(()->{
            ac.getBean("xxxx", MemberService.class);  //여기서 에러발생
        }).isInstanceOf(NoSuchBeanDefinitionException.class);//에러의 타입이 이거 맞는가?
    }
}
