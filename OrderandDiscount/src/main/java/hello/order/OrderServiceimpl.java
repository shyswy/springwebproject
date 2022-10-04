package hello.order;

import hello.annotation.MainDiscountPolicy;
import hello.discount.DIscountPolicy;
import hello.discount.FixedDiscountPolicy;
import hello.discount.RateDiscountPolicy;
import hello.member.Member;
import hello.repository.MemberRepository;
import hello.repository.MemoryMemberRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;




@Component
@RequiredArgsConstructor
@Getter
public class OrderServiceimpl implements OrderService{

    private final MemberRepository memberRepository;

    // private final MemberRepository memberRepository= new MemoryMemberRepository();
    @MainDiscountPolicy

    //    아래는 rate,fixed discount 정책중 1개 골라야할 경우. >> 둘다 필요한경우( 동적 할인 정책) 은 map 써서!
     //@Qualifier("policy") //@RequiredargConstruct 사용시 @Qualify 인식 못함..! autowired 쓰거나
    // lombok.config 상단에 생성 후 lombok.copyableAnnotations += org.springframework.beans.factory.annotation.Qualifier

    private final DIscountPolicy discountPolicy;


   //private final DIscountPolicy discountPolicy= new RateDiscountPolicy(); new FixedDiscountPolicy();
    //private final DIscountPolicy discountPolicy=  new FixedDiscountPolicy();

    //위와 같이 단순히 변경해주는 것 만으로 할인 정책을 변경할 수 도 있지만, OCP, DIP 둘다 만족 x
    //OCP: 개방에 열려있고 변경에는 닫혀있어야>> 객체가 바뀐다. 따라서 만족x
    //DIP: 추상화(인터페이스) 의존, 구체화( 구현 클래스) 의존x
    //인터페이스에 의존하며, 실제 구현체를 외부에서 주입 받기에, 실제 구현체를 사용 클래스는 모른다.

    //>> 구현체 생성 및 연결 담당하는 설정 클래스인 AppConfig 생성.  >> 생성자를 통해 외부 주입해주자!
    // 이처럼 외부에서 제어 흐름 관리하는 것 = IOC(제어의 역전)
    //DI: 런타임 시점, 외부에서 실제 구현체 생성해서 주입해주므로써 실제 의존관계가 연결되는 것.
    //Appconfig 같은 객체 생성, 관리, 의존 관계 연결을 담당하는 놈을 IOC 컨테이너(DI 컨테이너) 라고 한다.



    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {

        Member member = memberRepository.findById(memberId);   //여기서 오류!


        int discount = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discount);
    }

    //for Test
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
