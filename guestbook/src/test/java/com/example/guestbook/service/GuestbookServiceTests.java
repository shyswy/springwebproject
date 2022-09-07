package com.example.guestbook.service;

import com.example.guestbook.dto.GuestbookDTO;
import com.example.guestbook.dto.PageRequestDTO;
import com.example.guestbook.dto.PageResultDTO;
import com.example.guestbook.entity.Guestbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest //필수!!
public class GuestbookServiceTests {
    @Autowired
    private GuestbookService service;

    @Test
    public void testRegister() {  //dto를 엔티티로 변환 후 실제 db에 추가. (repository 의존성 주입, repository에 save 하는 것으로)
        GuestbookDTO guestbookDTO = GuestbookDTO.builder()
                .title("Sample Title...")
                .content("Sample Content...")
                .writer("Sample Writer...")
                .build();
        System.out.println(service.register(guestbookDTO));
    }

    @Test
    public void testList(){ // 엔티티 객체 > DTO 객체 변환 성공했는가? check
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(10).build();
        //PageRequstDTO 를 생성해준다.

        PageResultDTO<GuestbookDTO, Guestbook> resultDTO = service.getList(pageRequestDTO);
        //service에 pageRequestDTO 를 전달하여 service> serviceimpl 에 가서 해당 DTO로 적절한 엔티티 리스트
        // 찾고, 해당 page 리스트를 다시 pageResultDTO로 변환하여 준다.
        // service 에서 담당(getList 메소드로)!
        // 자료구조에서 DTO(pageRequestDTO) 타입 뺴서 page<entity> 타입으로 변경
        //  다시 DTO(pageresultDTO) 타입으로 변환한 뒤 이를 이용해 화면에서 페이지 처리.


        System.out.println("PREV: "+resultDTO.isPrev());
        System.out.println("NEXT: "+resultDTO.isNext());
        System.out.println("TOTAL: "+resultDTO.getTotalPage());
        System.out.println("------------------------------------------");




        for(GuestbookDTO  guestbookDTO : resultDTO.getDtoList()){ //page<entity>로 정보 뽑은 뒤 다시 DTO 만든 결과 출력!
            System.out.println(guestbookDTO);
        }

        System.out.println("================================================");

        resultDTO.getPageList().forEach(i->System.out.println(i));

    }

    @Test
    public void testSearch(){ // 엔티티 객체 > DTO 객체 변환 성공했는가? check
        PageRequestDTO pageRequestDTO=PageRequestDTO.builder()
                .page(1)
                .size(10)
                .type("tc")
                .keyword("한글")
                .build();



        PageResultDTO<GuestbookDTO, Guestbook> resultDTO = service.getList(pageRequestDTO);
        //service에 pageRequestDTO 를 전달하여 service> serviceimpl 에 가서 해당 DTO로 적절한 엔티티 리스트
        // 찾고, 해당 page 리스트를 다시 pageResultDTO로 변환하여 준다.
        // service 에서 담당(getList 메소드로)!
        // 자료구조에서 DTO(pageRequestDTO) 타입 뺴서 page<entity> 타입으로 변경
        //  다시 DTO(pageresultDTO) 타입으로 변환한 뒤 이를 이용해 화면에서 페이지 처리.


        System.out.println("PREV: "+resultDTO.isPrev());
        System.out.println("NEXT: "+resultDTO.isNext());
        System.out.println("TOTAL: "+resultDTO.getTotalPage());
        System.out.println("------------------------------------------");




        for(GuestbookDTO  guestbookDTO : resultDTO.getDtoList()){ //page<entity>로 정보 뽑은 뒤 다시 DTO 만든 결과 출력!
            System.out.println(guestbookDTO);
        }

        System.out.println("================================================");

        resultDTO.getPageList().forEach(i->System.out.println(i));

    }
}
