package hello.logdemo;

import hello.common.MyLogger;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogDemoService { //비즈니스 로직이 있는 서비스 계층

    //requestScope를 사요아지 않고 파라미터로 모든 정보를 서비스 계층에 넘기면 지저분 해진다.
    //myLogger 덕분에 서비스계층가 관련 없는 정보들 (requestURL 등등) 을 넘기지 않아도 됨.
    //웹과 관련된 부분은 가급적 컨트롤러 까지만 처리.
    private final MyLogger myLogger;

    public void logic(String testId) {
        myLogger.log("service id=" + testId);
    }
}
