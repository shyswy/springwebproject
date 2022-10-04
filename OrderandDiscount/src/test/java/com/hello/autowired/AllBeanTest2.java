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

/*
* DiscountService는 Map으로 모든 DiscountPolicy 를 주입받는다. 이때 fixDiscountPolicy , rateDiscountPolicy 가 주입된다.
discount() 메서드는 discountCode로 "fixDiscountPolicy"가 넘어오면 map에서 fixDiscountPolicy 스프링 빈을 찾아서 실행한다. 물론 “rateDiscountPolicy”가 넘어오면
rateDiscountPolicy 스프링 빈을 찾아서 실행한다.
→ 다형성을 활용해 유연한 전략 패턴 발휘
*
* */

public class AllBeanTest2 {

    @Test
    void findAllBean() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AutoAppConfig.class, DiscountService.class);

        DiscountService discountService = applicationContext.getBean(DiscountService.class);
        Member member = new Member(1L, "jeongwon", Grade.VIP);
        int discountPrice = discountService.discount(member, 10000, "fixDiscountPolicy");

        assertThat(discountService).isInstanceOf(DiscountService.class);
        assertThat(discountPrice).isEqualTo(1000);

        discountPrice = discountService.discount(member, 20000, "rateDiscountPolicy");

        assertThat(discountPrice).isEqualTo(2000);
    }

    static class DiscountService {
        private final Map<String, DIscountPolicy> policyMap;
        private final List<DIscountPolicy> policyList;

        @Autowired
        public DiscountService(Map<String, DIscountPolicy> policyMap, List<DIscountPolicy> policyList) {
            this.policyMap = policyMap;   //nullpoint error!
            this.policyList = policyList;
            System.out.println("policyMap = " + policyMap);
            System.out.println("policyList = " + policyList);
        }

        public int discount(Member member, int price, String discountCode) {
            DIscountPolicy discountPolicy = policyMap.get(discountCode);
            return discountPolicy.discount(member, price);
        }
    }
}