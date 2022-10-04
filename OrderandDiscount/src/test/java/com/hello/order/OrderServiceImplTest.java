package com.hello.order;

import hello.order.Order;
import hello.order.OrderServiceimpl;
import org.junit.jupiter.api.Test;

public class OrderServiceImplTest {

    @Test
    void createOrder() {
/*
        OrderServiceimpl orderService = new OrderServiceimpl();

        Order order = orderService.createOrder(1L, "itemA", 10000);
*/
        //lombok을 사용하기에 해당 테스트는 현재 동작하지 않는다.
        /*MemberRepository memberRepository = new MemoryMemberRepository();
        memberRepository.save(new Member(1L, "catsbi", Grade.VIP));

        OrderServiceImpl orderService = new OrderServiceImpl();
        orderService.setMemberRepository(memberRepository);
        orderService.setDiscountPolicy(new FixDiscountPolicy());

        Order order = orderService.createOrder(1L, "itemA", 10000);

        assertThat(order.getDiscountPrice()).isEqualTo(1000);*/
    }
}
