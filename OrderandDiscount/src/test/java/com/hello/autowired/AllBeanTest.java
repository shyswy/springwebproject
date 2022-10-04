package com.hello.autowired;

import hello.AutoAppConfig;
import hello.discount.DIscountPolicy;
import hello.member.Grade;
import hello.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


//같은 타입의 빈 모두 조회하여 map에 ( discout > rate, fixed)
//클라이언트가 할인의 종류(rate, fix)를 선택할 수 있는 로직 구현시!
public class AllBeanTest {

    @Test
    void findAllBean() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AutoAppConfig.class, DiscountService.class);
    }

/*
    Map<String, DiscountPolicy> : map의 키에 스프링 빈의 이름을 넣어주고, 그 값으로 DiscountPolicy 타입으로 조회한 모든 스프링 빈을 담아준다.
    List<DiscountPolicy> : DiscountPolicy 타입으로 조회한 모든 스프링 빈을 담아준다.
    만약 해당하는 타입의 스프링 빈이 없으면, 빈 컬렉션이나 Map을 주입한다.
    AnnotationConfigApplicationContext는 스프링 컨테이너 그 자체다.
    스프링 컨테이너에 스프링 빈을 등록하는 여러 가지 방법이 있는데, 그 중 다음 방법도 있다.
new AnnotationConfigApplicationContext(클래스)
    그러면 넘어간 클래스가 스프링 빈으로 자동 등록된다.
    스프링 컨테이너가 자동으로 빈을 찾아 컬렉션에 맞게 주입해주어 policyMap과 policyList에 들어간다.

    */

    static class DiscountService {
        private final Map<String, DIscountPolicy> policyMap;
        private final List<DIscountPolicy> policyList;

        @Autowired
        public DiscountService(Map<String, DIscountPolicy> policyMap, List<DIscountPolicy> policyList) {
            this.policyMap = policyMap;
            this.policyList = policyList;
            System.out.println("policyMap = " + policyMap);
            System.out.println("policyList = " + policyList);
        }
    }
}

