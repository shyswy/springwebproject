package sanghyun.projectname1.service;


import org.springframework.test.annotation.Commit;
import sanghyun.projectname1.domain.Member;
import sanghyun.projectname1.repository.MemberRepository;
import sanghyun.projectname1.repository.MemoryMemberRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

//spring 까지 포함하는 통합 테스트.
//실제 spring을 돌리는 거라 시간이 오래걸리므로 가급적이면 순수한 단위 테스트( 순수 자바 코드만 테스트) 가 중요
//

@SpringBootTest
@Transactional   //db에 쿼리 넣고 커밋을 해야 실제로 올라간다 디폴트는 auto commit으로 자동으로 커밋해줌
    //테스트 다돌리고 데이터를 롤백하게 해준다.
        // 이거 없이회원가입 TC에서  hello의 이름 가진 멤버 등록하고 다시 테스트 돌리면 중복에러가 뜬다.
    //따라서 테스트 종료후 hello가 실제 db에 남지 않는다.
class MemberServiceIntegrationTest {
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Test

    //@Commit  // 하면 실제 db에 들어간다 ( transactional 로 복구하지 않고 commit
    public void 회원가입() throws Exception {
        //Given
        Member member = new Member();
        member.setName("hello");
        //When
        Long saveId = memberService.join(member);
        //Then
        Member findMember = memberRepository.findById(saveId).get();
        assertEquals(member.getName(), findMember.getName());
    }
    @Test
    public void 중복_회원_예외() throws Exception {
        //Given
        Member member1 = new Member();
        member1.setName("spring");
        Member member2 = new Member();
        member2.setName("spring");
        //When
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.join(member2));//예외가 발생해야 한다.
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }
}


