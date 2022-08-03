package com.example.ex2.repository;

import com.example.ex2.entitiy.Memo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// 기본적인 것 외에는 @Query 와 퀴리 메소드 사용

//인터페이스 선언 만으로 스프링 빈에 자동 등록 됨.  ( 빈이 내부적으로 인터페이스 타입에 맞는 객체 생성 후 빈에 등록)
public interface MemoRepository  extends JpaRepository<Memo,Long> {// 인터페이스에서 JPARepository로 상속구조 스타트
                                              //엔티티의 타입 정보, @Id의 타입     지정.

    List<Memo> findByMnoBetweenOrderByMnoDesc(Long from, Long to); //퀴리메소드. 메소드 명으로 퀴리매칭해서 날림
    //  findBy ~~  Between OrderBy~~~~~Desc >>   ~~ 를 해당 퀴리 수행.
    //위 메소드의 의미는   mno값이 between(from,to)  사이의 객체를 구하고 mno의 역순으로 정렬
    //Between은 2개의 파라미터 요구.

    Page<Memo> findByMnoBetween(Long from, Long to, Pageable pageable);

    void deleteMemoByMnoLessThan(Long num);

    /*
    @Transactional
    @Modifying
    @Query("update Memo m set m.memoText = :memoText where m.mno = :mno ")
    int updateMemoText(@Param("mno") Long mno, @Param("memoText") String memoText );

    :#{} 꼴 예시
    @Transactional
    @Modifying
    @Query("update Memo m set m.memoText = :#{#param.memoText } where m.mno = :#{#param.mno }")
    int updateMemoText(@Param("mno") Long mno, @Param("memoText") Memo memo );
*/



    //@Query 역시 페이징 처리와 정렬 부분을 작성하지 않을 수 있다.
    //별도의 countQuery 속성을 적용해주고 Pageable 타입의 파라미터를 전달.
    @Query(value = "select m from Memo m where m.mno > :mno " ,
            countQuery = "select count(m) from Memo m where m.mno > :mno" )
    Page<Memo> getListWithQuery(Long mno, Pageable pageable);


    //@Query 장점.  현재 필요한 데이터만  Object[] 의 형태로 선별적으로 추출 가능.
    //Memo 클래스에는 시간 관련 선언 없기에 JPQL에서 제공하는 CURRENT_DATE 로 DB 시간 구한다.
    @Query(value = "select m.mno, m.memoText, CURRENT_DATE from Memo m where m.mno > :mno" ,
            countQuery = "select count(m) from Memo m where m.mno > :mno" )
   Page<Object[]> getListWithQueryObject(Long mno, Pageable pageable); // 현재 필요한거만 추출한다!

    //아래와 같이 nativeQuery 통해 SQL 그대로 활용가능.
    // ( JPA 자체가 DB에 독립적으로 구현 가능하다는 장점 사라지지만 복잡한 JOIN 구문 등을 처리하기 위해 이렇게 해야하는 경우 존재.
    @Query(value = "select * from memo where mno > 0",nativeQuery = true)
    List<Object[]> getNativeResult();



}
