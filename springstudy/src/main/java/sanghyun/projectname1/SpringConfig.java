package sanghyun.projectname1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sanghyun.projectname1.aop.TimeTraceAop;
import sanghyun.projectname1.repository.*;
import sanghyun.projectname1.service.MemberService;

//change
@Configuration
public class SpringConfig {


   // @Autowired  DataSource dataSource;  //spring에서 제공해주는 data source  아래방식처럼 해도 ok
    //위는 Jpa 이전까지 사용


    /*
   // JpaMemberRepository와 연결
    private EntityManager em;//jpa는 EntityManager 받아줘야한다!

    @Autowired
    public SpringConfig(EntityManager em) {
        this.em = em;
    }
    */

    private final MemberRepository memberRepository;



    //스프링 데이터 jpa
    @Autowired // 스프링 컨테이너에서 MemberRepository를 찾는다. >> SpringDataJpaMemberRepository로
    public SpringConfig(MemberRepository memberRepository) {//injection 받기 ( 생성자로)
        this.memberRepository = memberRepository;
    }



    /*
        private DataSource dataSource;
        @Autowired
        public SpringConfig(DataSource dataSource) {
            this.dataSource = dataSource;
        }
    */
    @Bean    //@Bean 보고 spring에 올림
    public MemberService memberService(){// 자바 코드로 직접 MemberService instance를 컨테이너에 등록
       // return new MemberService(memberRepository());   //ctrl p로 오류난 이유 check 가능
        return new MemberService(memberRepository);//
    }

   /* @Bean      스프링 데이터 Jpa 사용시 필요 없어진다.
    public MemberRepository memberRepository(){   //위의 메서드에서 사용시 호출된다.
       // return new MemoryMemberRepository();
        //return new JdbcMemberRepository(dataSource);  //이렇게만 해주면 db 갈아 끼는 것이 가능해진다!!
        //다형성을 활용한다 ( 구현체를 바꿔끼기 가능) 객체지향언어( 스프링) 의 장점!!
        //dependency injection으로 기존 코드를 유지하고 바꿀것만 갈아끼우면 된다!
        //( jdbc >> 반복적 코드 많다)

       // return new JdbcTemplateMemberRepository(dataSource);// jdbc 코드로 jdbc에서 반복적인 코드를 줄인다.
        //return new JpaMemberRepository(em);  //jpa로 몇몇 쿼리들 자동으로 날린다!


    }*/

    /*
    @Bean
    public TimeTraceAop timeTraceAop() {// Aop를 빈에 등록. spring이 이런 aop 있구나~ 인지시킨다.
        return new TimeTraceAop();
    }
    but 이번 예제는 TimeTraceAop에서 컴포넌트 스캔으로 해준다
     */











}
