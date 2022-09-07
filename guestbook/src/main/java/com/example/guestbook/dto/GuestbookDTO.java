package com.example.guestbook.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data

//위의 어노테이션 으로 각각의 getter setter 생성함
public class GuestbookDTO {  //데이터 전달위한 DTO 생성.
    //서비스 계층에서 GuestbookDTO 이용해서 필요한 내용을 전달 받고, 반환하도록 처리하도록
    // service 패키지의 GuestbookService 인터페이스와 GuestbookServiceImpl 클래스 추가
    private Long gno;
    private String title;
    private String content;
    private String writer;
    private LocalDateTime regDate,modDate;
}
