package com.example.board.entitiy;


import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude="writer") //해당 클래스의 모든 멤버 변수를 출력한다. >> Member객체인 Writer에 Lazy적용했기에 제외한다.

public class Board extends BaseEntity{  //Member의 Email값 (P.K) 를 F.K 로 참조한다.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 고유한 키 자동 생성
    private  Long bno;

    private  String title;

    private String content;

    @ManyToOne(fetch=FetchType.LAZY)//Lazy"
    private Member writer; // join 에서 사용

    public void changeTitle(String title){
        this.title=title;

    }

    public void changeContent(String content){
        this.content=content;
    }

}
