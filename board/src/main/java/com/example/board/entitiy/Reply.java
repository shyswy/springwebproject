package com.example.board.entitiy;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString//해당 클래스의 모든 멤버 변수를 출력한다.

public class Reply extends BaseEntity{  //회원이 아닌 사람도 답글을 남길 수 있다고 가정.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    private String text;

    private String replyer;

    @ManyToOne   //board에 oneToMany( F.K 어쩌구) 이렇게 설정?
    private Board board;// join 에서 사용
}
