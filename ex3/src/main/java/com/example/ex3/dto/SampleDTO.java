package com.example.ex3.dto;


//SampleController에서 DTO 클래스를 통해 데이터 전송.
//DTO = data transfer object  데이터 전송용 객체
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data  //Getter,Setter. toString(), equals(), hashCode() 를 자동 생성해주는 코드
@Builder(toBuilder = true)
public class SampleDTO {
    private Long sno;
    private  String first;
    private  String last;
    private LocalDateTime regTime;
}
