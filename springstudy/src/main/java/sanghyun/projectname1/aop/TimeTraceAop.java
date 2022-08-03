package sanghyun.projectname1.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect// Aop 표시
@Component //컴포넌트 스캔

//Aop를 통해서 공통 관심사항에 변경점이 있으면 아래만 변경해주면 모두에게 반영된다!
//핵심관심사항을 깔끔하게 유지가능:  MemberService의 함수들 내부에 timeTrace 로직을 구현하지 않아도됨
public class TimeTraceAop {
    //공통 관심사항을 어디에다가 적용할 것인가?  (TimeTraceAop를)
    @Around("execution(* sanghyun.projectname1.service..*(..))")//해당 패키지 하위 모든 놈들에게 작용


    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable{
        long start = System.currentTimeMillis();
        System.out.println("START: "+ joinPoint.toString());
        try {
            return joinPoint.proceed();//proceed는 다음 메소드로 진행한다는 뜻.
            //다음 적용대상으로!
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs=finish-start;
            System.out.println("END: "+joinPoint.toString()+" "+timeMs+"ms" );

        }
        }

}
