package com.example.board.DTO;


//페이지 목록처리:
// 1) 화면 목록 데이터에 대한 DTO 생성
// 2) DTo를 Pageable 타입으로
// 3)Page<entity> 를 화면에서 사용하기 쉬운 DTO 리스트 등으로 변환
// 4)화면에 필요한 페이지 번호 처리
//페이지 목록 처리는 재사용가능성이 높은 코드!!
//목록화면에서 페이지 번호, 페이지내 목록의 수, 검색 조건 등으로 페이지를 요청받는다. 이것들을 DTO 타입 파라미터 선언하고
//나중에 재사용가능!


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


@Builder
@AllArgsConstructor
@Data
public class PageRequestDTO { //화면에 전달되는 목록데이터 처리.
    //화면에서 전달디는 page 와 size 정보를 파라미터로 받아 수집한다. 목적: JPA쪽에서 사용하는 Pageable타입의 객체 생성.
    private int size;
    private  int page;
    private String type; //검색조건
    private String keyword;//검색 키워드
    public PageRequestDTO(){
        this.page=1;
        this.size=10;
    }

    public Pageable getPageable(Sort sort){ //파라미터로 들어온 Sort 방식으로 Page request 를 받는다.
        return PageRequest.of(page -1,size,sort); //정렬은 다양한 상황에서 쓸 수 있게 파라미터로 받아 가변적으로 정렬.
        //JPA 이용시 page 번호는 기본 1부터. 0부터 시작할 수 있게 -1 해준것.
    }


}
