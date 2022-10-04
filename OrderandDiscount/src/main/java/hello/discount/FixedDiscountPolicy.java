package hello.discount;

import hello.member.Grade;
import hello.member.Member;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;


@Component    // NoUniqueBeanDefinitionException  에러 발생하게 된다!
//  fixeddiscountpolicy, RateDiscountPolicy 모두 DIscountPolicy 를 가져온다. 따라서 만약 fixed, Rate둘다 컴포넌트 스캔시
//같은 타입의 빈을 2개 가져옴으로 중복에러 발생!
@Qualifier("policy")
public class FixedDiscountPolicy implements DIscountPolicy{


    private int discountFIxAmount = 1000; // 1000원 할인

    @Override
    public int discount(Member member,int price) {
        if(member.getGrade()==Grade.VIP)
            return discountFIxAmount;
        return 0;
    }
}
