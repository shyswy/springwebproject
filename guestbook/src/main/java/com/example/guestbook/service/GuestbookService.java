package com.example.guestbook.service;

import com.example.guestbook.dto.GuestbookDTO;
import com.example.guestbook.dto.PageRequestDTO;
import com.example.guestbook.dto.PageResultDTO;
import com.example.guestbook.entity.Guestbook;

public interface GuestbookService {
    Long register(GuestbookDTO dto); // register에서 dto를 파라미터로 받는다.
    GuestbookDTO read(Long gno);

    void remove(Long gno);
    void modify(GuestbookDTO dto);


    //원래 인터페이스는 추상메소드만 가능. 하지만 java8부터 default를 통해 실제 내용을 가지는 메소드를 인터페이스에 작성가능해짐.
    //기존 인터페이스 > 추상메소드 > 구현 클래스  과정에서 추상 클래스 생략가능.

    default Guestbook dtoToEntity(GuestbookDTO dto){ //파라미터로 전달되는 DTO 를 엔티티 객체로 (JPA로 처리하기 위해서)
        Guestbook entity=Guestbook.builder()
                .gno(dto.getGno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();
        return entity;
    }
    default GuestbookDTO entityToDto(Guestbook entity){
        GuestbookDTO dto=GuestbookDTO.builder()
                .gno(entity.getGno())
                .title(entity.getTitle())
                .content(entity.getContent())
                .writer(entity.getWriter())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .build();

        return dto;
    }

    PageResultDTO<GuestbookDTO,Guestbook> getList(PageRequestDTO requestDTO);

    //방명록 조회 구현

}
