package sanghyun.projectname1.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sanghyun.projectname1.domain.Member;
import sanghyun.projectname1.repository.MemberRepository;
import sanghyun.projectname1.repository.MemoryMemberRepository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {
    /*
     MemberService memberService=new MemberService();
    MemoryMemberRepository memberRepository= new MemoryMemberRepository();
    */
    //여기서 만든   memberRepository 와 memberService의 memberRepository는 서로 '다른' 객체이다.
    // 따라서 아주 혹시나, 다른 결과가 나올 수 있다. (static 선언이 되어 class에 붙어서 이 경우는 문제 x 지만
    //만약 static 안붙어있으면 서로 다른 DB가 되어 올바르지 않게 됨)   서로 같은 instance 로 안전하게 만들어주자!
//이런 최소단위 테스트가 좋은 테스트이다!

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach(){
        memberRepository= new MemoryMemberRepository();  //ctrl 클릭으로 정의 확인. memberService 에서 생성된 객체 인자로 받아 넣어줌
        memberService=new MemberService(memberRepository);
        //같은 객체 두번 생성>> 객체 1개 생성후 넣어준다.
    }



    @AfterEach
    public void afterEach(){
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() {  //주어진 것 ,이걸 실행했을떄, 이 결과가 나와야한다.
        //given
        Member member =new Member();
        member.setName("hello");


        //when

        long saveId = memberService.join(member); //join은 추가후 id 리턴.

        //then       저장한게 repository에 있는게 맞는지?
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
        // name으로 올바른 find가 수행됬나 check

    }

    @Test
    public void 중복_회원_예외(){
        //given
        Member member1 = new Member();
        member1.setName("sanghyun");

        Member member2 = new Member();
        member2.setName("sanghyun");

        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        // 람다 함수 ( 함수를 인자로!)   member2를 join으로 추가하면 IllegalStateException 에러가 터져야 성공.
         assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");//해당 오류에서 올바른 메세지 반환하나?
        //메세지 검증



        /*
        memberService.join(member1);
        try {
            memberService.join(member2);
            fail();
        }catch(IllegalStateException e){
            Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }
        */
        //then

    }
    @Test
    void 회원찾기() {
    }

    @Test
    void id로찾기() {
    }
}