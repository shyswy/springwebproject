package sanghyun.projectname1.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import sanghyun.projectname1.domain.Member;

import java.util.List;


//스프링이 아닌 순수 자바 코드를 테스트 하는것
//h2 db를 사용하면 자바코드만으로 테스트 안됨 (db커넥션 정보도  스프링 부트 가 지니고 있고 등등....

class MemoryMemberRepositoryTest { //여기서 디버그시 내부 전체 테스트 가능.


    //TDD: 테스트 먼저 만들고 거기 끼워서 개발 하는 방식 .

    MemoryMemberRepository repository = new MemoryMemberRepository();
    //주의! 각 테스트 순서는 랜덤. 다른 곳에서 객체 꺼내 올 수 있기 때문에 1개 테스트
    //끝 날 때마다 clear 필요
    @AfterEach    // 각 메소드가 끝날때마다 아래 동작 수행  (콜백 메서드)
    public void afterClear(){  //각 테스트는 서로 의존관계 없어야하기에 테스트 끝날때마다 clear 필수
        repository.clearStore();

    }


    @Test
    public void save(){   //assert가 correct시 녹색불.
        Member member =new Member();
        member.setName("sanghyun");

        repository.save(member);
        Member result= repository.findById(member.getId()).get();//좋은 방법은 x 테케에서 바로 optional의 값 꺼내서 확인해볼 수 있다.
       // Assertions.assertEquals(member, null);//  memver가 result로 올바르게 나오나 테스트
        Assertions.assertThat(member).isEqualTo(result); //assetj로 ( junit말고)
        //assertions 대고  alt+ enter 시   static impoty 뜨는거: static import 통해
        //Assertions.asserThat >> asserThat() 만 가지고 사용 가능.


    }

    @Test
    public void findByName(){
        Member member1 = new Member();
        member1.setName("sanghyun");
        repository.save(member1);

        Member member2 = new Member();  //shift+ f6시 같은 변수명 가진거 조정 가능.
        member2.setName("yejin");
        repository.save(member2);

        Member result= repository.findByName("yejin").get(); //optinal 값 get

        Assertions.assertThat(result).isEqualTo(member2);


    }

    @Test
    public void findAll(){
        Member member1= new Member();
        member1.setName("sanghyun");
        repository.save(member1);

        Member member2= new Member();
        member2.setName("yejin");
        repository.save(member2);

        List<Member> result = repository.findAll();
        //repository.findAll()  쓰고 alt + shift 하면 자동으로 딱 맞는 타입 변수 생성가능.

        Assertions.assertThat(result.size()).isEqualTo(2); //2명만 추가 따라서 2명 추가되었나 확인.

    }

}
