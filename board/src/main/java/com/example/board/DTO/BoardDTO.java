package com.example.board.DTO;

import com.example.board.entitiy.Member;
import lombok.*;

import java.time.LocalDateTime;


@Data
@Builder
@ToString
public class BoardDTO {

    private  Long bno;

    private  String title;

    private String content;

    private String writerEmail;

    private String writerName;

    private LocalDateTime regDate;

    private LocalDateTime modDate;

    private Long replyCount;

}
