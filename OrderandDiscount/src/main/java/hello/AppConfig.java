package hello;



import hello.discount.DIscountPolicy;
import hello.discount.RateDiscountPolicy;
import hello.order.OrderService;
import hello.order.OrderServiceimpl;
import hello.repository.MemberRepository;
import hello.repository.MemoryMemberRepository;
import hello.service.MemberService;
import hello.service.MemberServiceimpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//기본 Autowired.

//어노테이션 기반의 자바 설정 클래스로 스프링 컨테이너 만들기 (xml로도 가능)



@Configuration  //여기서 설정을 구성한다는 의미 ( IOC 컨테이너!) >> 싱글톤 컨테이너!!!!1

//memberservice()와 orderService() 는 둘다 memberrepository 객체 호출. 스프링은 '싱글톤' 컨테이너 가지기에
//원래대로면 MemberService() , orderService(), memberRepository() 에서 총 3번  memberRepository() 생성자를 호출하여
//3개의 인스턴스가 생성되는 것이 아래 코드의 내용. 하지만 @Configuration 어노테이션을 통해 "바이트 코드 조작" 을 하고
// 이를 통해 스프링 빈 존재 x 시 기존 생성 로직, 존재하면 존재하는 값을 뽑아오게 해준다.
// 따라서 memberRepository() 생성자는 3번 호출되어야 하는데도 싱글톤을 유지한 채 1번만 호출된 뒤 부터 생성된 것을 가져온다.
// singletone >> ConfigurationSingletonTest 확인.

public class AppConfig {

    //다른 클래스는 인터페이스만 받아 놓고, Appconfig에서 구현체를 외부 주입 (DI) 해준다.
    //OCP, DIP 만족. 각자 자신의 역할만 수행할 수 있게 = 관심사 분리
    //이를 통해 각 구현체는 다른 역할의 구현체에 의존하지 않는다.
    //다른 class들은 외부(Appconfig)에 맡기고 각각 자신의 로직에만 집중 가능하다.

    //MemberServiceimpl,OrderServiceimpl, OrderServiceImplTest 에서 이걸 통해 DI 받는다.

    @Bean    //해당 메서드를 스프링 컨테이너 (IOC 컨테이너) 에 bean으로 등록한다.
    public MemberRepository memberRepository() { // MemberRepository 타입 인터페이스는 아래 방식으로 DI.
        System.out.println("AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }

    @Bean
    public MemberService memberService() {
        System.out.println("AppConfig.memberService");
        return new MemberServiceimpl(memberRepository());
    }


    @Bean
    public DIscountPolicy discountPolicy() {
        return new RateDiscountPolicy();
    }


    @Bean
    public OrderService orderService() {
        System.out.println("AppConfig.orderService");
        return new OrderServiceimpl(memberRepository(),discountPolicy());
//        return null;
    }


}
