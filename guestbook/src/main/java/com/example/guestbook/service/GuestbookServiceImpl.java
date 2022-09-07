package com.example.guestbook.service;

import com.example.guestbook.dto.GuestbookDTO;
import com.example.guestbook.dto.PageRequestDTO;
import com.example.guestbook.dto.PageResultDTO;
import com.example.guestbook.entity.Guestbook;
import com.example.guestbook.entity.QGuestbook;
import com.example.guestbook.repository.GuestbookRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.Optional;
import java.util.function.Function;

@Service  //스프링에서 빈으로 처리되도록 Service 어노테이션 추가
@Log4j2

// (수정1) 을 통해 실제로 데이터 베이스에 처리가 완료되도록 수정 했다.
//( repository에 save 되야 실제 db에 처리가 되는것)

@RequiredArgsConstructor   //의존성 자동 주입  (수정1)
public class GuestbookServiceImpl implements GuestbookService {

    private final GuestbookRepository repository; //반드시 final로 해야함. (수정1)
    //JPA 처리를 위해 GuestbookRepository 를 주입. (위의 @Required~~ 로 자동 주입) ( autowired(필드주입) 과 다르게 생성자주입)


    @Override   // service 인터페이스 와 service impl 클래스.
    //serviceImpl 클래스에서 service 인터페이스에서 정의한 메소드 등을 overrid 한다!
    public Long register(GuestbookDTO dto){ //dto 수신.
        log.info("DTO------------------");
        log.info(dto);

        Guestbook entity=dtoToEntity(dto);  //dto를 엔티티 객체로 변환.
        log.info(entity);  //Guestbook 타입 반환 (엔티티 객체)

        repository.save(entity); //(수정1)


        return entity.getGno(); //(수정1)   dto를 엔티티 변환 후 repository에 save 후 gno 리턴
    }


    //이 상황에서 entitiy== Guestbook

    //정렬 기준 정하고, repository에서 RequestDTO에 해당하는 Page리스트 찾은 뒤,
    // ResultDTO에 Page리스트와 entity> DTO 변환 함수 전달.
    @Override
    public PageResultDTO<GuestbookDTO,Guestbook> getList(PageRequestDTO requestDTO){
        Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());
        //requestDTO의 getPageable 메소드를 통해 PageRequest를 통해 받은 page정보(Page<Guestbook>> 를 가변적인 정렬로 받는다
        // (Sort 객체를 전달하여 상황에 맞는 정렬가능)


        BooleanBuilder booleanBuilder=getSearch(requestDTO);// requestDTO를 Querydsl로 동적인 퀴리처리
        //( 동적으로 바뀌는 검색조건에 따른 퀴리 를 BooleanBuilder에

        //만든 BooleanBuilder로 findall 하여 검색조건에 따른 필터링
        Page<Guestbook> result=repository.findAll(booleanBuilder,pageable);//JPA 처리 결과인 Page<Entity> (엔티티리스트) 타입 결과 저장 .


        Function<Guestbook,GuestbookDTO> fn=(entity -> entityToDto(entity)); //Guestbook > Guestbookdto로
        return new PageResultDTO<>(result,fn); //엔티티 리스트, EN > DTO 변환함수를 인자로 전달.
    }

    @Override
    public GuestbookDTO read(Long gno){
        Optional<Guestbook> result = repository.findById(gno);
        return result.isPresent()? entityToDto(result.get()):null; //result 존재시( 해당 gno가진 객체가 repository에 존재)
        //해당 콘텐츠 get 해주고 dto 로 변환,  없으면 null 리턴
    }

    @Override
    public void remove(Long gno){
        repository.deleteById(gno);
    }

    @Override
    public void modify(GuestbookDTO dto){
        //title, content 항목을 업데이트 한다
        //기존의 엔티티에서 제목, 내용만 수정 후 다시 저장 하는 방식.
        Optional<Guestbook> result = repository.findById(dto.getGno()); //없으면 null 이므로 Optional 처리
        if(result.isPresent()){
            Guestbook entity=result.get();

            entity.changeTitle(dto.getTitle());


            entity.changeContent(dto.getContent());

            repository.save(entity);

        }
    }
    //원래 따로 BooleanBuilder 클래스 작성하지만 편의 상 이곳에  메소드로 정의
    private BooleanBuilder getSearch(PageRequestDTO requestDTO){ //동적 검색 조건을 위한 Querydsl 처리
        String type=requestDTO.getType();
        BooleanBuilder booleanBuilder=new BooleanBuilder();
        QGuestbook qGuestbook = QGuestbook.guestbook;// QGuestbook is a Querydsl query type for Guestbook
        String keyword=requestDTO.getKeyword();
        BooleanExpression expression = qGuestbook.gno.gt(0L);// gno>0 인 조건만 생성.
        booleanBuilder.and(expression); //BooleanBuilder에 위의 조건 추가.

        if(type==null||type.trim().length()==0){//검색조건이 없는 경우 gno>0 의 조건으로만 findall 하여 검색한다.
            return booleanBuilder;
        }

        //검색조건 작성

        BooleanBuilder conditionBuilder = new BooleanBuilder();

        if(type.contains("t")){
            conditionBuilder.or(qGuestbook.title.contains(keyword));
        }
        if(type.contains("c")){
            conditionBuilder.or(qGuestbook.content.contains(keyword));
        }
        if(type.contains("w")){
            conditionBuilder.or(qGuestbook.writer.contains(keyword));
        }

        //모든 조건 통합
        booleanBuilder.and(conditionBuilder); //t,c,w 키워드로 정해진 검색조건들을 추가한 BooleanBuilder를 통합시킴.

        return booleanBuilder;




    }



}
