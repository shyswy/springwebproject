package sanghyun.projectname1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sanghyun.projectname1.domain.Member;

import java.util.Optional;

//SpringDataJpa   >> 대부분의 공통 쿼리 구현되어있음. 인터페이스만으로 ㅇㅋ


public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>,MemberRepository {
    //extends JpaRepository 의  JpaRepository 를 ctrl + 클릭으로 들어가서 살펴보자!
    //계속 타고 올라가면  구현했던 findAll,  save 등이 이미 구현되어있다!
    //( 이전에 구현했던 findAll 등등이 딱 여기 구현된거에 맞춰서 구현된것 >> 사용가능!!

//스프링 데이터 jpa가  jpa 리포지토리를 받고있으면
// 빈을 탐색해서(SpringDataJpaMemberRepository) 구현체를 자동으로 만들어줌.


    //interface만 만들어 놓고 extends JpaRepository 이거만 해주면 스프링 데이터 jpa가 인터페이스에 대한 구현체를 알아서 만들어준 뒤
    //spring bean 에 등록하여   injection(SpringConfig 에서) 받을 수 있게 해준다
    @Override
    Optional<Member> findByName(String name); //
    // '이름 으로 찾기' 같은건 상황따라 달라서 공통화 못해놓음.
    //식벽자가 name? id? 번호? 다다르다.

    //하지만    findBy~~~~    >> JPQL로    select m form Member m where m.name = ~~~    가 됨
    //따라서 findBy~~~~ 에서 ~~~ 만 알려주면 자동 생성해주는 것.
    //대부분의 단순한 쿼리는 인터페이스만으로 가능하다. + JPA로 조금씩 맞지 않는거 처리
    //복잡한 동적 쿼리는 Querydsl   라이브러리로 편리하게 가능.
    //위의 조합으로도 힘든건 JPA가 제공하는 네이티브 쿼리(썡 SQL), 혹은 JdbcTemplate 사용해서 해결해야



}
