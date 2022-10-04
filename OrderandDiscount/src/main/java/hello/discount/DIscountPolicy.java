package hello.discount;

import hello.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component

public interface DIscountPolicy {
     //int discountPolicy(Member member,int price);
     int discount(Member member,int price);
}
