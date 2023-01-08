package com.example.board.Controller;

import com.example.board.DTO.BoardDTO;
import com.example.board.DTO.PageRequestDTO;
import com.example.board.Service.BoardService;
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
@RequestMapping("/board/")
@RequiredArgsConstructor
@Log4j2
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){
        //스프링 MVC 는 파라미터를 자동으로 수집한다. 화면쪽에서 page, size 정보를 url에 담아 보내면
        //적절한 pageRequestDTO 객체를 찾아 수집된다.

        //void>> 자동으로 매핑된 url 리턴.
        // 따라서 /board/list 가 리턴된다!
       log.info("list.........................."+pageRequestDTO);
       //get으로 list 가기전 적절한 페이지 정보 로드후 list.html의 result에에 PageRsultDTO<BoardDTO,Object[]> 타입을 전달
        model.addAttribute("result",boardService.getList(pageRequestDTO));

    }

    @GetMapping("/register")
    public void register(){
        log.info("register get....");
    }
    @PostMapping("/register")
    public String registerPost(BoardDTO boardDTO, RedirectAttributes redirectAttributes){
        log.info("dto..."+boardDTO);
        Long bno=boardService.register(boardDTO);
        redirectAttributes.addFlashAttribute("msg",bno);
        //addFlashAttribute는 리다이렉트 전에 정보를 세션에 저장해둔다( 리다이렉트 시 기존 정데이터는 사라지기에)
        //따라서 조회 페이지 > 수정 페이지 이동 후 다시 돌아올 때 와 같이 "돌아와야하는 경우" 정보를 유지하는데에 유용
        return "redirect:/board/list";
    }

    @GetMapping({"/read","/modify"}) //조회화면과 수정화면은 동일>> 조회화면에서 내용바꾸고 수정버튼 누르면 된다.
    public void read(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO,Long bno, Model model){
        //@ModelAttribute 어노테이션으로, 요청되는 pageRequestDTO 에 적절한 객체를 찾아서,
        // setter( chagneTitle 등등)을 호출하여 변화를 반영한다.

        //Get 방식으로, gno 값을 전달 받고, Model에 받은 bno와 매칭되는 BoardDTO 객체를 담아서 전달.
        //나중에 다시 list 페이지로 돌아가는 데이터를 저장하기위해 PageRequestDTO를 파라미터로 같이 사용.
        log.info("Get Mapping!!  bno: "+ bno);   //게시물 클릭하여 read 진입시
        //modify 클릭시 다시 진입


        BoardDTO boardDTO=boardService.get(bno);
        model.addAttribute("dto",boardDTO);
    }

    @PostMapping("/remove")
    public String remove(Long bno,RedirectAttributes redirectAttributes){
        log.info("Post Mapping! bno: "+bno);
        boardService.deleteBoardWithReply(bno);
        redirectAttributes.addFlashAttribute("msg",bno);
        //bno를 msg라고 명한다.
        //addFlashAttribute는 리다이렉트 전에 정보를 세션에 저장해둔다( 리다이렉트 시 기존 정데이터는 사라지기에)
        //따라서 조회 페이지 > 수정 페이지 이동 후 다시 돌아올 때 와 같이 "돌아와야하는 경우" 정보를 유지하는데에 유용

        //list로 다시 돌아온다>> default인 1페이지로
        return "redirect:/board/list";
    }

    @PostMapping("/modify")
    public String modify(BoardDTO dto,@ModelAttribute("requestDTO") PageRequestDTO requestDTO,RedirectAttributes redirectAttributes){
        log.info("post modify----------------------------"); // 수정화면에서 수정하시겠습니까? ok 로 수정완료시 이동.
        log.info(" dto: "+dto);
        boardService.modify(dto);
        //addFlashAttribute시, http body에 정보들을 숨긴다. >> read에서는 해당 값들을 url에서 받아온다 따라서
        //addFlashAttribute 사용시, read할 게시물을 불러오지 못하게된다!!

        //addAttribute로 url에 page, type, keyword, bno 정보 reveal!
        redirectAttributes.addAttribute("page",requestDTO.getPage());
        redirectAttributes.addAttribute("type",requestDTO.getType());
        redirectAttributes.addAttribute("keyword",requestDTO.getKeyword());

        redirectAttributes.addAttribute("bno",dto.getBno());

        return "redirect:/board/read";

    }


}
