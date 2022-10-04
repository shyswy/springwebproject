package hello.discount;

import hello.annotation.MainDiscountPolicy;
import hello.member.Grade;
import hello.member.Member;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@MainDiscountPolicy
@Component
//@Qualifier("policy")

public class RateDiscountPolicy implements DIscountPolicy{
    //  fixeddiscountpolicy, RateDiscountPolicy 모두 DIscountPolicy 를 가져온다. 따라서 만약 fixed, Rate둘다 컴포넌트 스캔시
//같은 타입의 빈을 2개 가져옴으로 중복에러 발생!


    private int discountPercent = 10;
    @Override
    public int discount(Member member, int price) {
        System.out.println("ccc: "+member.getGrade());

        if (member.getGrade() == Grade.VIP) {
            return price * discountPercent / 100;
        }
        return 0;
    }


}
