package com.example.guestbook.controller;

import com.example.guestbook.dto.GuestbookDTO;
import com.example.guestbook.dto.PageRequestDTO;
import com.example.guestbook.service.GuestbookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/guestbook")
@Log4j2
@RequiredArgsConstructor
public class GuestbookController {

    private final GuestbookService service;// 생성자 주입

    @GetMapping({"/"})   // /list 혹은 / 만 들어오면 log 출력후 /guestbook/list로 리다이렉팅
    public String list(){
        log.info("list......");
        return "/guestbook/list";
    }

    @GetMapping("/list")
        public void list(PageRequestDTO pageRequestDTO, Model model){
        log.info("list............................"+pageRequestDTO);
        //getlist에  pageRequestDTO 를 인자로 전달하면 매칭되는 Page<Entity> 를 찾고 다시 PageResultDTO로 변환 후 리턴한다.
        model.addAttribute("result",service.getList(pageRequestDTO));
       //model은 결과 데이터 DTO리스트 (PageResultDTO) 를 result 라는 이름으로 전달한다. >> list.html에 전달됨.



        }
        // get 방식으로 화면을 보여주고
        @GetMapping("/register")
         public void register(){
            log.info("register get....");
         }
         //Post 방식에선 처리 후에 목록 페이지로 이동하도록 설계
    //prg  방식 post redirect get 방식 post로 받아 get 으로 리다이렉트 후 매핑하는 방식.

         @PostMapping("/register")
    public String registerPost (GuestbookDTO dto, RedirectAttributes redirectAttributes){
        log.info("dto......"+dto);
        Long gno= service.register(dto);//dto를 엔티티 변환 후 repository에 save 후 gno 리턴
        redirectAttributes.addFlashAttribute("msg",gno);
        return "redirect:/guestbook/list";
         }

    @GetMapping({"/read", "/modify"})// 조회, 수정&삭제 페이지 둘다 동일한 진입 방식.
    //수정& 삭제페이지 화면 기능
    public void read(long gno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO , Model model){
        // 조회 페이지 구현시, 새로운 창을 띄우는 방식을 쓰는 경우도 존재.
        // 하지만 이 경우는 다시 목록 페이지로 돌아오게 작성. 따라서 PageRequestDTO 파라미터에 목록 페이지 정보를 받아 놓는다.
        //@ModelAttribute 없어도 되지만, 명시적으로 requestDTO라는 이름으로 처리하기 위해 사용.
        log.info("gno: "+ gno);

        GuestbookDTO dto=service.read(gno);//read:  service에서 repository에 findbyID로 객체 찾고, dto로 변환하여 리턴해줌

        model.addAttribute("dto", dto); //dto 라는 이름으로 attribute 추가
    }

    @PostMapping("/remove")

    //

    public String remove(Long gno, RedirectAttributes redirectAttributes){ //삭제 처리
        //삭제 방식은 get으로 수정 페이지에 들어가 "삭제" 버튼을 클릭하는 방식.

        log.info("gno: "+gno);
        service.remove(gno);
        redirectAttributes.addFlashAttribute("msg",gno);// gno를 msg라고 일시적으로 명시해줌

        return "redirect:/guestbook/list";


    }

    @PostMapping("/modify")   //modify 의 post 방식 처리   수정 처리

    //기존 페이지에서 수정 완료 후에는, 조회 페이지로 리다이렉트.
    // 이때 기존 페이지 정보를 유지하여  조회페이지에서 다시 목록페이지로 이동할 수 있게한다.
    public String modify (GuestbookDTO dto, @ModelAttribute("requestDTO") PageRequestDTO requestDTO,
                          RedirectAttributes redirectAttributes ){
        //Guestbook dto: 수정해야하는 글의 정보를 가져옴
        //PageRequestDTO requestDTO: 기존 페이지의 정보 유지.
        //Redir~~~   : 리다이렉트로 이동하기 위함

        //@ModelAttribute >> 파라미터로 받은 값을 오브젝트(객체) 형태로 주입받기 위함.
        log.info("post modify.............!");
        log.info("dto: "+ dto);

        service.modify(dto);   //기존의 엔티티에서 제목, 내용 만 업데이트 해준다. (새로 받은 dto의 내용으로)

        redirectAttributes.addAttribute("page",requestDTO.getPage());

        redirectAttributes.addAttribute("type",requestDTO.getType());

        redirectAttributes.addAttribute("keyword",requestDTO.getKeyword());

        //addFlashAttribute는 리다이렉트 전에 정보를 세션에 저장해둔다( 리다이렉트 시 기존 정데이터는 사라지기에)
        //따라서 조회 페이지 > 수정 페이지 이동 후 다시 돌아올 때 와 같이 "돌아와야하는 경우" 정보를 유지하는데에 유용
        redirectAttributes.addAttribute("gno",dto.getGno());

        return "redirect:/guestbook/read";

    }

}
