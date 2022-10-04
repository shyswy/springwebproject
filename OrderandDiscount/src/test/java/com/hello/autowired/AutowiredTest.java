package com.hello.autowired;

import hello.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;

import java.util.Optional;

public class AutowiredTest {

    @Test
    void autowiredOption() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);

    }


    static class TestConfig {
        //주입 할 빈이 없어도 넘어가야할 때 아래 3가지 방법으로 처리 ( null)
        @Autowired(required = false)
        public void setBean1(Member member) {
            System.out.println("setBean1 = " + member);
        }

        @Autowired
        public void setBean2(@Nullable Member member) {
            System.out.println("setBean2 = " + member);
        }

        @Autowired
        public void setBean3(Optional<Member> member) {
            System.out.println("setBean3 = " + member);
        }
    }
}
