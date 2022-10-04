package com.hello.order;


import hello.AppConfig;
import hello.member.Grade;
import hello.member.Member;
import hello.order.Order;
import hello.order.OrderService;
import hello.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("OderService 클래스의")
class OrderServiceTest {
    private Member basicMember;
    private Member vipMember;

    private OrderService orderService;
    private MemberService memberService;

    @BeforeEach
    void setup() {
        /*AppConfig appConfig = new AppConfig(); 그냥 클래슬 구현했을때

        orderService = appConfig.orderService();
        memberService = appConfig.memberService();*/

        //@bean, @configuration 어노테이션을 붙여 스프링 컨테이너에 등록한 뒤. 다른 패키지껄 가져오기.
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        orderService = applicationContext.getBean("orderService", OrderService.class);
        memberService = applicationContext.getBean("memberService", MemberService.class);

        basicMember = new Member(1L, "BASIC", Grade.BASIC);
        vipMember = new Member(2L, "VIP", Grade.VIP);

        memberService.join(basicMember);
        memberService.join(vipMember);



    }

    @Nested
    @DisplayName("createOrder 메소드는")
    class Describe_createOrder {
        @Nested
        @DisplayName("일반 등급의 회원이 10000원짜리 물건의 주문을 생성할 경우 가격은")
        class Context_with_create_order_from_basic_member {
            @DisplayName("제품의 물건 가격 그대로 생성된다.")
            @Test
            void it_is_original_price() {
                //when

               //에러!!! repository 작동 x
                Order order = orderService.createOrder(basicMember.getId(), "item", 10000);

                //then
                assertThat(order.calculatePrice()).isEqualTo(10000);
            }
        }

        @Nested
        @DisplayName("VIP등급의 회원이 10000원짜리 물건의 주문을 생성할 경우 가격은")
        class Context_with_create_order_from_vip_member {
            @DisplayName("제품의 물건 가격 그대로 생성된다.")
            @Test
            void it_is_discounted_price() {
                //when
                Order order = orderService.createOrder(vipMember.getId(), "item", 10000);

                //then
                assertThat(order.calculatePrice()).isEqualTo(9000);
            }
        }
    }



}
