package com.example.springproject01.Controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //별도의 화면 없이 데이터 전송  >> 별도 설정 없이도 json형태로 데이터 전송.
public class SampleController {

    @GetMapping("/hello")  //해당 url로 매핑 받음

    public String[] hello(){
        return new  String[] {"Hello", "World"}; //배열 객체 생성
    }


}
