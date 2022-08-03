package com.example.ex2.entitiy;
//spring data JPA의 entity class

import lombok.*;

import javax.persistence.*;

@Entity  //SPing data JPA 는 해당 어노테이션 필수
@Table(name = "tb1_memo") //엔티티 어노테이션과 같이 사용. 엔티티 클래스를 어떤 테이블로 사용할 것인가
@ToString
@Getter //Getter 자동 생성
@Builder  //객체 생성 할 수 있게 처리 ( 아래 2개 ArgsConstructor 같이 써야 에러 x
@AllArgsConstructor
@NoArgsConstructor
public class Memo {
    //엔티티 클래스는 primary key에 해당하는 필드로 @id 지정해야함
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //만약 id가 사용자 입력값 아니면 자동생성 번호 사용
    //GenerationType. ~  >>   AUTO: JPA 구현채(Hibernate등) 이 생성
    //IDENTITY: 사용하는 DB가 키생성 결정  ex) auto increment
    //SEQUENCE: DB의 sequence 사용해서  키 생성.  @SEQUENCE 와 함꼐 사용.
    //TABLE: 키생성 전용 테이블 생성하여 사용  @TableGenerator 과 함께 사용
    //만약 Db 가 오라클: 별도의 번호를 위한 테이블 지정
    //만약 Db가 MySQL  or   MariaDb   : auto increment로 새로운 레코드 기록시마다 '다른 번호 가질 수 있게' 한다!
    private Long mno;// primary key 값 설정
    @Column(length = 200,nullable = false)// 추가적인 Column으로 새로운 속성 지정. (nullable, length, name 등)
    private String memoText; //위의 Column 키값 설정





}
