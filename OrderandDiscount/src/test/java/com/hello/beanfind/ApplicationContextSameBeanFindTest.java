package com.hello.beanfind;


import hello.discount.DIscountPolicy;
import hello.discount.FixedDiscountPolicy;
import hello.discount.RateDiscountPolicy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ApplicationContextSameBeanFindTest {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SameBeanConfig.class);

    @Test
    @DisplayName("동일한 타입이 둘 이상인 스프링 빈을 타입으로 조회할 경우 에러가 발생한다.")
    void findBeanByTypeDuplicate() {
        assertThatThrownBy(()->{
            DIscountPolicy bean = ac.getBean(DIscountPolicy.class);
        }).isInstanceOf(NoUniqueBeanDefinitionException.class);
    }


    @Configuration
    static class SameBeanConfig {
        // 인터페이스 보이면 그 타입확인해서 구현체 매칭>> 동일한 타입 여러개 등록되있으면 문제 발생!

        @Bean
        public DIscountPolicy fixDiscountPolicy() {
            return new FixedDiscountPolicy();
        }

        @Bean
        public DIscountPolicy rateDiscountPolicy() {
            return new RateDiscountPolicy();
        }

        //위 둘다 DIscountPolicy 인터페이스 타입이다. 이런거 어떻게? 1) 타입별로 지정   2)특정 타입 모두 조회
    }



    @Test
    @DisplayName("동일 타입 둘 이상 스프링 빈을 타입으로 조회할 빈 이름 지정.")
    void findBeanByName(){

        DIscountPolicy dIscountPolicy =ac.getBean("fixDiscountPolicy",DIscountPolicy.class);
        assertThat(dIscountPolicy).isInstanceOf(FixedDiscountPolicy.class); //두 타입이 같은 타입인가?
    }


    @Test
    @DisplayName("특정 타입을 모두 조회")
    void findAllBeanType(){
        Map<String,DIscountPolicy> beansOfType=ac.getBeansOfType(DIscountPolicy.class);
        //DiscountPolicy를 모두 map 에 넣는다.
        for(String key : beansOfType.keySet()){ // 모든 DiscountPolicy 에 대해
            System.out.println("key= "+key+"value= "+beansOfType.get(key));//key, value 출력
            //fixed~   rate~~ 둘다 조회된다! ( 둘다 DIscountPolicy 인터페이스 상속.
        }

    }






}

