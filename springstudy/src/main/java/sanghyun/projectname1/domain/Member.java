package sanghyun.projectname1.domain;

import javax.persistence.*;

@Entity //jpa가 관리하는 entity 표시.
public class Member {  //h2 database 에 member db 생성
    @Id @GeneratedValue (strategy = GenerationType.IDENTITY) //db가 알아서 생성해주는 identity 타입이다.
    private Long id;  // 식별자  id는 시스템이 임의로 할당한다.

    private String name; //name 값

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
