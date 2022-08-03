package com.example.ex2.repository;

import com.example.ex2.entitiy.Memo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class MemoryRepositoryTest {
    @Autowired
    MemoRepository memoRepository;



    @Test
    public void testClass(){
        System.out.println(memoRepository.getClass().getName());
        //getClass는 실제 객체를 반환.
    }

    @Test
    public void testInsertDummies(){
        IntStream.rangeClosed(1,100).forEach(i -> { //100개의 새로운 Memo 객체 생성 후 insert하는 것.
            Memo memo = Memo.builder().memoText("Sample..."+i).build();
            memoRepository.save(memo);// 새로운 객체 > insert로 진행됨.
        });
    }


    @Test
    public void testSelect(){
        Long mno=100L;
        Optional<Memo> result = memoRepository.findById(mno); //findById는 SQL 미리 해놓는다.
        System.out.println("==============================="); //해당 줄이 마지막에 위치
        if(result.isPresent()){ //optional 이므로 결과가 존재할때만 출력
            Memo memo = result.get();
            System.out.println(memo);

        }
    }

    @Transactional
    @Test
    public void testSelect2(){
        Long mno=100L;
        Memo memo = memoRepository.getOne(mno); //getOne은 객체가 실제 필요한 순간에 SQL 실행
        System.out.println("===============================");
        System.out.println(memo); //따라서 ====== 가 먼저 출력 후 println에서 실제 객체를 사용하는 여기서 SQL 동작

    }

    @Test
    public void testUpdate(){ //save 함수는 select SQL로 @Id 가진 엔티티 객체가
        // 객체 없으면 insert, 있으면 최신 값으로 update 자동 진행!
        Memo memo = Memo.builder().mno(100L).memoText("Update Text").build();  //100번의 Memo 객체 호출
        System.out.println(memoRepository.save(memo)); //save() 호출
    }

    @Test
    public void testDelete(){
        Long mno=100L;
        memoRepository.deleteById(mno); //해당 데이터가 존재x 시 예외 발생시킴
    }

    @Test
    public void testPageDefault() {
        //page 타입은 단순 해당 목록 뿐 아닌 실제 페이지 처리에 필요한 전체 데이터 개수를 가져오는 쿼리(count) 도 진행.

        //1페이지의 데이터 10개
        Pageable pageable = PageRequest.of(0, 10);

        Page<Memo> result = memoRepository.findAll(pageable);
        //findAll에 pagable 타입 파라미터 전달 시 페이징 처리 관련  쿼리 실행하고 Page<엔티티 타입> 객체로 저장.
        System.out.println(result);

        System.out.println("---------------------------------------");

        System.out.println("Total Pages: "+result.getTotalPages());//총 몇페이지?

        System.out.println("Total Count: "+result.getTotalElements());//전체 개수

        System.out.println("Page Number: "+result.getNumber());//현재 페이지 번호 ( 0부터 시작)

        System.out.println("Page Size: "+result.getSize());//페이지당 데이터 수

        System.out.println("has next page?: "+result.hasNext());//다음 페이지 존재하는가?

        System.out.println("first page?: "+result.isFirst()); //시작페이지(0) 인가?

        System.out.println("===============================");
       /*
        for(Memo memo: result.getContent()){ // 실제 페이지 데이터 처리는 getContent() 통한 LIst<엔티티타입> 이나
            //get() 통한 Stream<엔티티타입>
            System.out.println(memo);
        } 이는 단순 순서를 통한 출력. 아래를 통해 다양한 순서로 가능 
        
        */
    }
    
    @Test
    public void testSort(){ //특정 범위의 Memo객체 검색하거나 like 처리 필요시, 여러 검색 조건이 필요한 단점 존재
        //페이지 처리 담당하는 PageRequest에 정렬 관련 Sort타입 전달 가능. 
        Sort sort1=Sort.by("mno").descending(); // sort를 다양한 방식으로 지정가능.
        Sort sort2 = Sort.by("memoText").ascending();
        Sort sortAll = sort1.and(sort2); // 서로 다른 정렬 조건을 합치기 가능.

        Pageable pageable = PageRequest.of(0, 10, sortAll); //정렬한 페이지를 객체에 저장
        Page<Memo> result = memoRepository.findAll(pageable);//페이지 처리
        result.get().forEach(memo -> {     //foreach는 알아서 사이즈만큼 반복.
            System.out.println(memo);
        });

    }
    //퀴리 메소드,@Query 로 위 testSort의 단점 해결 가능.  >> memoRepository 인터페이스 참고!
    //퀴리 메소드: 메서드의 이름 자체가 퀴리. 키워드 따라 파라미터의 개수 결정.
    // 리턴타입이 자유롭다.
    // Ex) select 작업시 List 타입이나 배열 이용 가능, 파라미터에 Pageable 타입 들어가면 무조건 Page<엔티티타입> 꼴
    //인터페이스에 미리 정해진 이름에 맞게 선언만 해주면 자동으로 퀴리 날려줌!
    //퀴리 메소드는 복잡한 조건에서 불편해짐. 따라서 간단한거만 퀴리메소드, 나머진 보통 @Query 로 한다.
    @Test
    public void testQueryMethod(){//
        List<Memo> list = memoRepository
                .findByMnoBetweenOrderByMnoDesc(70L, 80L); //between은 파라미터 2개 (from, to)
        //Mno 기준으로 between(70~80) 의 객체를 OrderBy으로 역순 정렬
        for (Memo memo: list){
            System.out.println(memo);
        }
    }

    @Commit  //최종 결과를 커밋하기 위해. ( 아니면 transactional로 롤백되어 결과 반영 x 됨)
    @Transactional  // deleteBy는  select( 엔티티 객체 가져오기) 와 엔티티 삭제가 동시에.
    //db에 쿼리 넣고 커밋을 해야 실제로 올라간다 디폴트는 auto commit으로 자동으로 커밋해줌
    //이걸 통해서 한번 돌면 롤백하게 설정해줘야함.
    @Test
    public void testDeleteQueryMethod(){
        memoRepository.deleteMemoByMnoLessThan(10L); //10번째 이하의 데이터 제거   를 퀴리 메소드 통해서
    }

    //@Query 는 메소드 이름과 상관 없이 메서드에 추가한 어노테이션을 통해 원하는 처리 가능.
    //@Query의 value는 JPQL(객체 지향 쿼리)   SQL과 상당히 유사하지만 다르다.
    //JPQL 은   DB의 데이터 테이블 대신   엔티티 클래스와 멤버 변수를 이용해서 SQL과 비슷하게 작성함! 그외에는 상당히 유사하다.

    // 이것을 통해서
    // 1) 필요한 데이터만 선별 추출 가능
    // 2) DB에 맞는 순수한 SQL 을 사용하는 기능
    //3) insert,update,delete 와 같은 select 가 아닌 DML 등을 처리하는 기능 (@Modifying과 함꼐 사용가능)






    /*
    @Query("select m from Memo m order by m.mno desc ") Memo 객체 를 선택해서  해당 객체의 mno 기준 역순 정렬하라
    List<Memo> getListDesc();
    */


    // 파라미터 처리
    //1)  ?1 , ?2 같이 1부터 시작하는 파라미터 순서로
    //2) :xxx  처럼 :파라미터이름   으로
    //3) "#{ }   같이 자바 빈 스타일로   ( 여러개의 파라미터 전달할 때 복잡해질 경우가 있을시 이렇게)

    /*:xxx 꼴
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
    /*
    @Query 역시 페이징 처리와 정렬 부분을 작성하지 않을 수 있다.
    별도의 countQuery 속성을 적용해주고 Pageable 타입의 파라미터를 전달.
    @Query(value = "select m from Memo m where m.mno > :mno " ,
            countQuery = "select count(m) from Memo m where m.mno > :mno" )
    Page<Memo> getListWithQuery(Long mno, Pageable pageable);

     */




}
