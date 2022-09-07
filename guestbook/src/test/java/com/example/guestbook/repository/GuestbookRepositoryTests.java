package com.example.guestbook.repository;

import com.example.guestbook.entity.Guestbook;
import com.example.guestbook.entity.QGuestbook;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class GuestbookRepositoryTests {
    @Autowired
    private GuestbookRepository guestbookRepository;


    //아래 테스트들은 엔티티 객체를 영속성 객체 바깥에다가 사용해서 테스트 했지만 실제 사용은 DTO를 통해 영속계층 안에서 사용하는 것 권장.
    @Test
    public void insertDummies(){

        IntStream.rangeClosed(1,300).forEach(i -> {

            Guestbook guestbook = Guestbook.builder()
                    .title("Title...." + i)
                    .content("Content..." +i)
                    .writer("user" + (i % 10))
                    .build();
            System.out.println(guestbookRepository.save(guestbook));
        });
    }




    @Test
    public void updateTest() {

        Optional<Guestbook> result = guestbookRepository.findById(200L); //존재하는 번호로 테스트

        if(result.isPresent()){

            Guestbook guestbook = result.get();

            guestbook.changeTitle("Changed Title....");
            guestbook.changeContent("Changed Content...");

            guestbookRepository.save(guestbook);//setter로 값을 수정 후 save 했을 떄 modDate가 변하는지 체크//
             }
    }
    @Test
    public void testQuery1(){
        //title에 1 (keyword) 들어간 엔티티 검색

        //아래코드로 페이지 처리와 동시에 검색 처리.

        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());
        //pagealbe 에 10개의 페이지를 넣는데, gno(key번호)를 내림차순으로 정렬해서 넣는다.

        QGuestbook qGuestbook=QGuestbook.guestbook;  //동적처리를 위해 Q도메인 클래스를 얻어옴
        // (Q도메인 클래스로 엔티티 클래스에 선언된 title,content 등의 필드를 변수로 활용가능)


        String keyword="1";
        BooleanBuilder builder=new BooleanBuilder(); //Boolean Builder는 where문에 들어가는 조건을 넣어주는 컨테이너


        BooleanExpression expression=qGuestbook.title.contains(keyword);//원하는 조건을 필드값과 결합해 생성
        // titile에 keyword를 지니고있는(contain) 놈들 선별
        //builder안의 값은(expression) querydsl~~~~.Predicate 타입 ( java의 Predicate 아님)

        builder.and(expression);//만든 조건(expression)을 where문의 and나 or 같은 키워드로 결합.

        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable);
        //pagable 인스턴스 안의 builder의 조건(where절에 들어감) 가진 엔티티 뽑아낸다(findAll).

        //BooleanBuiler 은 GUestbookRepository에 추가된 QuerydslPredicateExcutor 인터페이스의 findAll() 사용가능.

        result.stream().forEach(guestbook -> {
            System.out.println(guestbook);
        });

    }
    @Test
    public void testQuery2(){
        //title 혹은 content에 1 이 들어가 있고 gno가 0보다 큰 엔티티 출력
        //만약 제목 혹은 내용 혹은 작성자  로 찾고 싶다면 똑같이 or 연산으로 붙이면 된다.

        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());
        QGuestbook qGuestbook=QGuestbook.guestbook;
        String keyword="1";
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression exTitle = qGuestbook.title.contains(keyword); //title 에 1 포함
        BooleanExpression exContent = qGuestbook.content.contains(keyword);//content에 1 포함
        BooleanExpression exAll = exTitle.or(exContent); // content에 1포함 or title에 1포함
        builder.and(exAll); // 비어있는 객체 and exALL >> exAll 수용하는 조건 생성.
        builder.and(qGuestbook.gno.gt(0L));   //gno > 0 조건 추가 ( gt >> greater then)

        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable);
        result.stream().forEach(guestbook -> {
            System.out.println(guestbook);
        });


    }

}
