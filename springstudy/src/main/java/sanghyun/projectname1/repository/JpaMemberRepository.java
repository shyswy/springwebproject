package sanghyun.projectname1.repository;

import org.springframework.transaction.annotation.Transactional;
import sanghyun.projectname1.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

//jpa!!!   쿼리도  기본적인거 제공해줌

//Spring data jpa는  SpringDataJpaMemberRepository 가서 확인하자! 


@Transactional  //jpa에서 데이터 저장, 변경시 항상 transactional 필요하다!  (ex: 회원가입)
public class JpaMemberRepository implements MemberRepository{
    private  final EntityManager em;// jpa를 쓰려면 entityManager을 주입 받아야한다. 

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    //Jdbc~~~ repository들과 비교해보자.
    @Override
    public Member save(Member member) { //저장 ,조회, 업데이트는 sql 짤 필요 없다!!
        em.persist(member);//영속 저장 
        return member;  //이렇게만 해주면 알아서 쿼리 날려줌! ( set id 등등....)
    }

    @Override
    public Optional<Member> findById(Long id) {//저장 ,조회, 업데이트는 sql 짤 필요 없다!!
        Member member =em.find(Member.class,id);
        return Optional.ofNullable(member);
    }


    //아래 두개 같은 경우는 jpql( 쿼리) 짜줘야한다!
    //jpa 자체를 스프링으로 한번 감싸는 spring data jpa 사용시 아래 두개 같은거도 jpql 안짜도된다!
    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select  m from Member m where m.name= :name", Member.class)
                .setParameter("name", name)
                .getResultList();
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {// jdbc:  id,name 찾고, 또 거기 맞는 객체 찾고... >>jpa:  entity(객체) 자체를 select한다.
        return em.createQuery("select m from Member m",Member.class).getResultList();
    }
}
