package hello.web;

import hello.common.MyLogger;
import hello.logdemo.LogDemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class LogDemoController { //logger가 잘 동작하는지 확인할 테스트용 컨트롤러


    //스프링 애플리케이션을 실행 하는 시점에서 싱글톤 빈은 생서애서 주입 가느아지만, request 스코프 빈은 아직 생성x
    //>> 실제 http 요청이 와야 생성됨!
    //방법 1) provider ( 빈을 컨테이너에서 찾아주는 DL 기능 수행)
    //방법 2) 는 프록시 활용 >> myLogger에서

    //프록시든, Provider 이든, 핵심은 실제 객체를 "실제로 필요한 시점" 까지 지연 처리! ( 실사용 될때만 있으면 된다)
// "처리 다한 척" 하고 필요한 시점에서 처리 >> 멀티 스레드와 연관??

    private final MyLogger myLogger; // 방법 1>> 이렇게 di 받는게 아니라
    // OnjectProvider<MyLogger> myLoggerProvider   -1    자동으로 MyLogger 타입의 빈을 찾아옴.

    private final LogDemoService logDemoService;



    @RequestMapping("log-demo")  // https://localhost:8080/log-demo 매핑  이거 쳐보고 어떤 과정으로 되는지 보자!
    @ResponseBody
    public String logDemo(HttpServletRequest request) { //httpservletrequest를 통해 요청 URL을 받는다.
        //요청받은 url:  https://localhost:8080/log-demo
        System.out.println("myLogger: "+myLogger.getClass());
        String requestURL = request.getRequestURL().toString();

        //MyLogger myLogger = myLoggerProvider.getObject();    -1   이렇게 컨테이너에서 빈을 찾아온다!




        //실제 구현에서는 URL을 mylogger에 저장하는 과정은 컨트롤러 보다는 공통처리가 가능한 스프링 인터셉터 or 서블릿 필터에서 하는게좋다.
        myLogger.setRequestURL(requestURL);   //이렇게 받은 URL을 mylogger 빈에 setter로 저자애줌
        // 리퀘스트 요청마다 구분되기에 섞이지 않는다! 사용자는 uuid로 구별 가능.

        myLogger.log("controller test");
        logDemoService.logic("testId");
        return "OK";
    }
}
