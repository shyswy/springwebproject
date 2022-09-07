package com.example.guestbook.dto;


//JPA를 이용하는 Repository에서는 페이지 처리 결과를 Page<entity> 타입으로 변환하게 된다.
//따라서 서비스 계층에서 이를 사용하기 위해선 별도의 클래스를 만들어서 처리해야한다.
//이 클래스에서 하는 일
// 1)Page<entity> 의 엔티티 객체들을 DTO 객체로 변환하여 자료구조에 담는다
// 2) 화면 출력에 필요한 페이지 정보들을 구성

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data //이걸 통해서 모든 필드 대상 생성, 접근자 만들어줌
public class PageResultDTO <DTO,EN> {  //다양한 곳에 사용하기위해 제네릭 타입으로 DTO와 EN(Entity) 라는 타입을 지정

    private int totalPage;// 총 페이지 수

    private int page;// 현재 페이지 번호

    private int size; // 목록 사이즈

    private int start,end; //시작페이지 번호, 끝 페이지 번호

    private boolean prev,next; // prev, next 키 유무

    private List<Integer> pageList; //페이지 번호 목록





    private List<DTO> dtoList;  //List<dto> 타입으로 DTO 객체들을 보관 따라서 엔티티 객체> DTO로 변환하여 저장하는 기술구현해야!

    //가장 일반적인 엔티티 객체 > DTO 변환은 추상 클래스 활용이지만, 매번 새로운 클래스가 필요하다는 단점.
    //이 프로젝트에선 서비스 인터페이스에 정의한 entitiyTODto() 와 별개의 Function 객체 만들어서 처리!

    //result에 들어온 Page 정보( PagerequestDTO 로 repository에서 찾은 page엔티티 객체) 를 fn (엔티티> DTO 변환 ) 함수 적용하여
    // dtoList로 리턴해준다.
    public PageResultDTO(Page<EN> result, Function<EN,DTO> fn) {  //fn을 result에 적용 ( fn에는 entity> DTO 변환 함수
                                                                    //파라미터로 들어옴
        dtoList=result.stream().map(fn).collect(Collectors.toList());//map 함수로 fn 함수를 리스트 모든 요소에 적용.
        //                 각 요소에 map 적용 후   collect 통해 다시 리스트로.

        totalPage=result.getTotalPages();
        makePageList(result.getPageable());


    }

    private void makePageList(Pageable pageable){
        this.page=pageable.getPageNumber()+1; // 0부터 시작. 따라서 1추가.
        this.size=pageable.getPageSize();

        int tempEnd=(int) (Math.ceil(page/10.0))*10; // Math.ceil :  0.1 >>1  , 1>> 1  , 1.1 >>2 로 ㅇㅋ
        //0.0......1 ~ 1  == 1 로  따라서, 위처럼 해주면  1~ 10번 페이지  >> 10
        //11~ 20번 페이지 > 20 로 웹페이지에 한번에 보이는 10개 페이지 중 끝 번호 계산 가능.

        start=tempEnd-9; // 시작페이지 번호는  끝페이지 -9  ( 한화면에 페이지 10개씩)
        prev=start>1; // prev는 시작 페이지가 1보다 클때 ( 이전페이지로 돌아가기 버튼)
        end=totalPage > tempEnd ? tempEnd: totalPage;// ex) 총 13페이지시, 1~10 > 10  10~13 > 13으로 끝페이지 구해야한다.
        next = totalPage > tempEnd;//현재 목록중 끝 페이지가 총페이지 보다 작으면 여분의 페이지 존재, next키 활성화화
        pageList= IntStream.rangeClosed(start,end).boxed()
                .collect(Collectors.toList());
        /*
        boxed() 메소드는 IntStream 같이 원시 타입에 대한 스트림 지원을 클래스 타입(예: IntStream -> Stream<Integer>)으로
        전환하여 전용으로 실행 가능한 (예를 들어 int 자체로는 Collection에 못 담기 때문에
        Integer 클래스로 변환하여 List<Integer> 로 담기 위해 등) 기능을 수행하기 위해 존재합니다.
        *
        * */


    }




}
