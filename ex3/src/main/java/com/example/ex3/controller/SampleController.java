package com.example.ex3.controller;


import com.example.ex3.dto.SampleDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


//작성된 SampleDTO의 객체를 Model 에 추가해서 전달한다.

@Controller
@RequestMapping("/sample")
@Log4j2  //lombok 기능. 스프링 부트가 로그라이브러리들 중 기본으로 설정하는 것
public class SampleController {
    @GetMapping("/ex1")
    public void ex1(){
        log.info("ex1...");
    }

    @GetMapping({"/ex2","/exLink"})  // value 속성값을 {} 로 처리시 1개의상의 URL 지정 가능.
    //이를 통해  /ex2  와 /exLink 모두 매핑하게 설정.
    public void exModel(Model model){//해당 파라미터 넘겨주며 ex2.html로
        List<SampleDTO> list = IntStream.rangeClosed(1, 20).asLongStream() //20개의
                .mapToObj(i -> {
                    SampleDTO dto = SampleDTO.builder() //SampleDTO 타입 객체 추가.
                            .sno(i)
                            .first("First.." + i)
                            .last("Last.." + i)
                            .regTime(LocalDateTime.now())
                            .build();
                    return dto;
                }).collect(Collectors.toList()); //20개의 객체 담긴 구조를 list로 변환

        model.addAttribute("list",list); //위의 list 객체를  Model에 담아 전송


    }

    @GetMapping({"/exInline"})

    //exInline()은 내부적으로 RedirectAttributes 를 이용해서 '/ex3' 으로 result와 dto 라는 이름의 데이터를 심어서 전달한다.
    //  /sample/exInline으로 호출하고, 해당 위치에서 /sample/ex3로 리다이렉트 된다!!

    //result는 단순 문자열, dto는 SampleDTO 객체.
    public String exInline(RedirectAttributes redirectAttributes){
        log.info("exInline...");
        SampleDTO dto=SampleDTO.builder()
                .sno(100L)
                .first("First...100")
                .last("Last...100")
                .regTime(LocalDateTime.now())
                .build();
        redirectAttributes.addFlashAttribute("result","success");
        redirectAttributes.addFlashAttribute("dto", dto);

        return "redirect:/sample/ex3";  //해당 페이지로 리다이렉트.
    }
    @GetMapping("/ex3")
    public void ex3(){
        log.info("ex3");
    } //로그 를 남긴다. ( ex3 라는 걸 로그에 남겨줌)


    @GetMapping({"/exLayout1","/exLayout2","/useTemplate","/exSidebar"}) //여기서 명시한 template의 html 파일로 자동 매핑한다!
    public void exLayout1(){
        log.info("exLayout..."); //매핑할때 해당 메소드 수행해서 로그 출력
    }

}
