package com.example.guestbook.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString

//BaseEntity 클래스 상속한다.
public class Guestbook extends BaseEntity{ //테이블 SQL 작성
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gno;
    @Column(length = 100,nullable = false)
    private String title;
    @Column(length= 1500,nullable = false)
    private String content;
    @Column(length = 50,nullable = false)
    private String writer;



    //원래 엔티티 클래스는 setter(수정기능) 최소한으로 사용해야한다. ( JPA 관리가 복잡해짐)
    //아래는 modify 를 통해 변경되는 것들 처리( 제목, 내용만 변경)  .
    public void changeTitle(String title){
        this.title=title;
    }
    public void changeContent(String content){
        this.content=content;
    }

}
