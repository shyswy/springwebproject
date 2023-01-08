package com.example.board.entitiy;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter //entity는 setter 없는게 이상적
@ToString//해당 클래스의 모든 멤버 변수를 출력한다.
public class Member extends BaseEntity{  //BaseEntity -> email을 아이디 대신 사용 가능하게 만든다.
    @Id
    private String email;  //email 주소를 PK로

    private String password;

    private String name;





}
