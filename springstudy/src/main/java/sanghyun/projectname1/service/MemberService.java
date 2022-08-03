package sanghyun.projectname1.service;


import sanghyun.projectname1.domain.Member;
import sanghyun.projectname1.repository.MemberRepository;


import java.util.List;
import java.util.Optional;
//repository 에 저장된 값들을 '간접적'   접근으로 뽑아온다!


//memberService 를 통해 회원가입, 조회가 가능해야한다. ( 고객에게 서비스 되는 영역)
//controller가 memberServicedp 의존관게 가지게!!

//@Service              서비스네? spring container bin 에 등록   자동 등록 방식.
public class MemberService {  //ctrl + shift + t 로 바로 Tc 생성 가능!
    //private final MemoryMemberRepository memberRepository = new MemoryMemberRepository();
    //위 코드는 Tc와 서로 다른 instance로 되어버린다, 아래처럼 변경
    private final MemberRepository memberRepository;
    //@Autowired     memberRepository 가 사용됨을 감지, bin에 올라와있는 instance를 넣어준다. 자동등록 방식

    public MemberService(MemberRepository memberRepository) { //좌클릭 > constructor로 생성
        this.memberRepository = memberRepository; //외부에서 member repository를 넣어준다. (dependancy ijection)
    }
    //constructor 로 생성.


    public Long join(Member member){

        //중복 이름 제거
        //Optional<Member> result = memberRepository.findByName(member.getName());
        //ctrl + alt + v 로 바로 타입에 맞는 변수 할당

//메소드 호출시간??  변경점 있으면 모두 일일이 찾아가서 바꿔줘야한다.
        /*
        long start = System.currentTimeMillis();
        try {
            validate(member);    //함수 내의 특정 부분을 함수로 추출할시 crtl+shift+alt+ t >> extract method로!!!

            memberRepository.save(member);

            return member.getId();

        }finally {//위의 동작 수행 후 끝난 시간 체크
            long finish = System.currentTimeMillis();
            long timeMs=finish-start;
            System.out.println("join= " + timeMs+ "ms");
        }

         */



        validate(member);    //함수 내의 특정 부분을 함수로 추출할시 crtl+shift+alt+ t >> extract method로!!!

        memberRepository.save(member);

        return member.getId();


        // Aop: 공통 과 핵심 관심 사항을 구분한다.
        // 필요한 상황: 모든 메소드의 호출 시간 측정,   공통관심 사항 vs 핵심관심 사항 , 회원 가입 시간, 회원 조회시간 측정시
        // ex) 시간측정 로직은 공통관심사항. 이것을 원하는 곳에 적용시키는 것.



    }

    private void validate(Member member) {    //중복회원 검사.

        memberRepository.findByName(member.getName())   //어차피 값이 리턴, 바로 ifpresent 문
        .ifPresent(m -> {   //null 아닌 값일시 ( opitional 이라서 가능)
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        } );
    }


    //전체 회원 조회
    public List<Member> findMember(){
        /*
        long start = System.currentTimeMillis();
        try {
            return memberRepository.findAll();
        } finally{
            long finish = System.currentTimeMillis();
            long timeMs=finish-start;
            System.out.println("findMembers "+timeMs+"ms");
        }

         */

        //Aop로 적용!
        return memberRepository.findAll();
    }
    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);

    }



}
