package hello.common;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;


//myLogger 빈은 '요청' 마다 1개씩 생성
@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS) //request scope로 지정 ( http 리퀘스트 생성과 종료 동안)
// proxymode=~    인터페이스면 INTERFACE , class 면 TARGET_CLASS
//이를 통해 Mylogger를 상속받는   가짜 클래스, 프록시 클래스가 생성되고 이 가짜 클래스가 주입된다.
//ac.getBean("myLogger" MyLogger.class) 로 조회해도 프록시 객체가 조회됨.
// 가짜 프록시 객체는 요청이 오면 그때 내부에서 진짜 빈을 요청하는 위임 로직 실행.
//가짜 프록시는 진짜 객체 (myLogger 클래스) 를 찾는 방법을 알고 있다.
// 예를 들어 클라이언트가  프록시 클래스를 받아오고, 메서드를 실행하면, 프록시에서 진짜 myLogger을 찾아 해당 메소드를 실행.
//사용자는 그것이 원본인지도 모르게 사용가능 (다형성)

//프록시는 단순한 "위임 로직" 만 있는 "싱글톤 빈" 처럼 동작한다.
//이를 통해 클라이언트는 마치 싱글톤 빈을 사용하듯이 편리하게 request scope 사용가능!
// request scope 사용시 >> 싱글톤 빈(스프링 기본) 과 싱글톤x 빈 같이 사용 하게된다.
// 되도록 사용하지 않는 것이 좋지만,     비즈니스 로직에 파라미터를 계속 들고 다닐 필요 없이 공통 정보를 처리할 때는 효과적이다.

// ex) 요청 서버 > 현재 서버 > 대상 서버  구조에서
//요청 서버가 요청id 생성 후 현재 서버에 넘겼을 떄 현재 서버는 단순 이 요청 id가 비즈니스 로직과는 전혀 상관 없고, 로그용으로만 쓰고
// 다시 대상 서버로 넘길 때 필요하다면
// >>  파라미터로 받아서 id 들고 다닐 필요없다 >> 이럴 떄 request scope 사용




//프록시든, Provider 이든, 핵심은 실제 객체를 "실제로 필요한 시점" 까지 지연 처리! ( 실사용 될때만 있으면 된다)
// "처리 다한 척" 하고 필요한 시점에서 처리 >> 멀티 스레드와 연관??
public class MyLogger {
    private String uuid;
    private String requestURL;

    //requestURL 은 이 빈이 생성되는 시점에는 알 수 없으므로, 외부에서 setter로 입력 받는다.
    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }


    public void log(String message) {
        String format = String.format("[%s][%s]%s", uuid, requestURL, message);
        System.out.println(format);
    }

    @PostConstruct    //미리 빈을 생성 해놓는다    초기화 메서드를 통해 uuid를 생성해서 저장해둔다.
    public void init() {
        uuid = UUID.randomUUID().toString();//uuid로  다른 http 요청과 구분한다!
        System.out.println(String.format("[%s]request scope bean create:", uuid) + this);
    }

    @PreDestroy  //미리 소멸 , 종료 직전에 모든 빈 종료     빈이 소멸하는 시점에서 종료 메세지 남긴다.
    public void destroy() {
        System.out.println(String.format("[%s]request scope bean close:", uuid) + this);
    }
}
