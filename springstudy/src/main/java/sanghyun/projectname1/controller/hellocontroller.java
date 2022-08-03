package sanghyun.projectname1.controller;

import org.apache.tomcat.util.net.TLSClientHelloExtractor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class hellocontroller {//sanghyun
    @GetMapping("hello")     // ~~/hello 로 mapping된다.
    //viewResolver 동작
    public String hello(Model model){
        model.addAttribute("data", "spring!!");   //data 의 value   setting
        return "hello";     //data 설정 후 template내의  hello.html 로 이동
        //vieq resolver 가  resouce: templates/   + name   + .html 로 찾아서 mapping
    }
    @GetMapping("hello-mvc")   //http://localhost:8080/hello-mvc?name=givename >> name 에 val 할당
    public String helloMvc(@RequestParam("name") String name, Model model){  //name값 입력해줘! html을 동적으로 변경
        model.addAttribute("name",name);
        return "hello-template";
    }
    @GetMapping("hello-string")
    @ResponseBody    // api 방식> 데이터 그대로 보낸다  보통 객체반환해서 jason 방식으로 변환된다.
    //html reponse body 부분에 내가 직접 넣어주겠다는 표시  알아서 converter로 적절히 변환
    //만약 클라이언트 HTTP Accept에서 특정 타입을 요구시, 해당 converter로 동작
    // viewResolver 대신
    // HttpMessageConverter  (문자)  or MappingJackson2HttpMessageConverter (객체)   가 동작하게된다!! jackson은 jason ver2
    public String helloString (@RequestParam("name") String name){
        return "hello " +name; //이 문자가 그대로! 요청한 클라이언트에 내려감. view 등 x
        //html 코드 없이   hello name     그대로  올라감.
    }

    @GetMapping("hello-api")   //jason 방식 <중요>   {"key: value"} 로 이루어진 구조
    @ResponseBody //  converter가 객체는 디폴트로 jason 방식으로 convert
    public Hello helloApi(@RequestParam("name") String name){
        Hello hello=new Hello();   //객체 생성
        hello.setName(name);
        return hello;
    }
    //xml방식은 열고 닫고, 2번. jason 은 key에 맞는 value 바로 get simple!
    static class Hello{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
