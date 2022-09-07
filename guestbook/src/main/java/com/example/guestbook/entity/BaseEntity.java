package com.example.guestbook.entity;



//데이터 등록, 수정시간은 자동으로 변경되야함. 어노테이션을 통해 자동 처리되게한다.


import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass  //해당 클래스의 테이블을 생성x 해당 클래스 상속하는 엔티티의 class로 db 테이블 생성.
@EntityListeners(value = {AuditingEntityListener.class})  //엔티티 객체의 변화를 감지. ( 변화 감지시 영속성 컨텍스트 > db에 업데이트 )
//위 AuditingEntityListener 활성화 위해서는 프로젝트에 @EnableJpaAuditing 설정을   프로젝트명Application 파일에 추가해야.

//여기서 @CreatedDate  @LastModifiedDate 를 통해 변화 감지되면 업데이트 해준다.

@Getter
abstract class BaseEntity { //추상 클래스.
    @CreatedDate //생성일자
    @Column(name = "regdate.", updatable = false)
    private LocalDateTime regDate;

    @LastModifiedDate //마지막 수정일.
    @Column(name = "moddate")
    private LocalDateTime modDate;
}
