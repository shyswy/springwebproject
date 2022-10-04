package hello;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;



//자동 빈 등록을 위한 컴포넌트 스캔!
@Configuration
// 아래 컴포넌트 스캔으로 @component, @Service 등을 탐색하여 자동으로 빈을 스프링 컨테이너에 등록
//basePackageClasses 속성 사용시 지정한 클래스의 패키지를 탐색위치로 지정
// , 를 통해 시작위치를 여러곳으로 지정가능. 이런식으로 속성값을 부여하여 컴포넌트 스캔에서 탐색할 패키지의 시작 경로를 지정할 수 있다. (
//(해당 패키지와 모든 하위 패키지 까지. 위 코드는 hello 패키지 하위 모든 놈들 컴포넌트 스캔!
@ComponentScan(basePackages = "hello",excludeFilters = @Filter(type = FilterType.ANNOTATION,
        classes = Configuration.class))   // @Configuration 으로 수동 빈등록 했던 파일 무시하게한다!




//NoUniqueBeanDefinitionException  에러 발생하게 된다!
//filter 로 컴포넌트 스캔 대상 필터 가능. excludeFilters> 제외할 대상, includeFilters >> 추가할 대상.
//@Qualify 사용 >> @Requiredargconstruct 와 연동 x 됨. 이거론 어노테이션 못읽어 오기에..
//@MaindiscountPolicy 도 읽으려면 해야한다...
//   패키지 상단에 file 타입으로 lombok.config 파일 생성 후 아래 설정 추가!
// lombok.copyableAnnotations += org.springframework.beans.factory.annotation.Qualifier

public class AutoAppConfig {
}
