package sanghyun.projectname1.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import sanghyun.projectname1.domain.Member;
import sanghyun.projectname1.service.MemberService;

import java.util.List;


//spring container에 controller 객체 생성 후 저장, 관리  (bin 이 관리 된다고 표현)
// 모든건 spring container 에서 받아서 쓸 수 있게 해야
//new로 객체 생성시, memberController 뿐 아닌 다른 모든 controller들이 객체 가져다 쓸 수 있다.

@Controller
public class MemberController {

   /*private final MemberService memberService= new MemberService(); //해당 객체는 여러 instance 생성할 필요 없이
    1개 instance를 공용으로 쓰면 된다.*/

    private final MemberService memberService;//instance 를 container에 집어 넣는 방법!

    /* controller   -   Service   -repository  패턴이 매우 흔하다.
    controller로 외부 요청 받고,  service에서 비즈니스 로직 만들고 repository에서 저장.

    @Component : 스프링 컨테이너에 bin( class 객체) 자동 등록한다.   @Repository,  @Service, @Controller 로구체화가능.
    @Autowired : 컨테이너에 등록되있는 bin들을 가져온다. ( 1개로 공유해서 사용)  dependacny injection

    이와 같은 bin 등록 방식을   '컴포넌트 스캔'+ 자동의존관계 설정 방법    이라고함.
    디폴트로 싱글톤 ( 1개 instance만 등록 후 공유한다)  따라서 같은 스프링 빈이면 같은 instance다. (아주아주 특수한경우만 싱글톤 별로..)


    자바 코드로 직접 스프링 빈 코드 등록해야 좋은 경우도 존재!

    1:정형화된 컨트롤러 서비스, 리포지토리 >> autowired 통한 컴포넌트 스캔
    2: 상황따라 변경해야하는 클래스 >> @Bean 으로 컨테이너에 등록  (SpringConfig 파일 참고)
    2>> ex) db 변경할떄 service 코드등 하나도 손대지 않고 Db만 갈아 끼우는 등 상황에 따른 변경에 용이하다.
    */


    //controller 영역은 보통 어쩔 수 없이 @Container,  @Autowired 사용

    //이미 컨테이너에 올라가 있는 것들만 autowired 동작.
    @Autowired  //아래에서 memberService 객체 사용함을 인지. spring container에 이미 있는 memberService instance 를 찾아 연결해줌
    public MemberController(MemberService memberService) {  //생성자를 통한 dependancy injection ( 가장 자주 쓰임)
        this.memberService = memberService;      //setter 로  injection시 누구나 접근가능 (  개발할 떄 쓸모없는 메소드는 안보이는게 베스트)
    }                                         //조립할 시점에 딱 1번 생성자로 추가해놓고 그 뒤로는 막아버릴 수 있어서 생성자가 good
    //의존관계가 동적으로 변화하지 않는다!

    @GetMapping("/members/new")     //html에서 회원가입 버튼 클릭시
    //GetMapping:  데이터 조회 시
    public String createForm(){
        return "members/createMemberForm"; //해당 위치로 이동 ( createMemberForm.html   로)
    }
    //위에서 createMemberForm.html 호출 >> 거기서 html상에 입력한 이름 가지고 아래 에 전달.

    @PostMapping("/members/new")    //post 타입 mapping 받음  postmapping은 데이터를 어떤 form에 넣어 전달할 때 주로 사용
    //(@PostMapping 데이트 등록시 )   위랑 같은 mapping이지만 pose, get 차이!
    public String createForm(MemberForm form){
        Member member= new Member();
        member.setName(form.getName());     //이름을 받아서 member class로 생성
        memberService.join(member);//join 으로 추가
        return "redirect:/";     // home 화면 ( 디폴트) 로 redirect한다! ( 회원 가입> 등록 후 다시 홈화면으로 )

    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMember();
        model.addAttribute("members", members);    //value 객체를 name 이름으로 추가한다.
        //memberList에서     members 값을 뽑아온다!

        /*
        *   Controller는 Model을 이용해서 데이터 생성 후, view에 데이터를 넘겨 적절한 View 를 생성한다.
        *   addAttribute 로 전달시 해당 컨테이너안의 모든 url에 해당 정보가 반영된다!
        * */

        return "members/memberList";   //해당 위치로 이동!ㅃ


    }


}



