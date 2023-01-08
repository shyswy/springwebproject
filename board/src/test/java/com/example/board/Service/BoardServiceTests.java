package com.example.board.Service;

import com.example.board.DTO.BoardDTO;
import com.example.board.DTO.PageRequestDTO;
import com.example.board.DTO.PageResultDTO;
import com.example.board.repository.BoardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

@SpringBootTest
public class BoardServiceTests {
    @Autowired
    private  BoardService boardService;

    @Test
    void BoardRegisterTest(){
        BoardDTO dto=BoardDTO.builder()
                .title("EX title")
                .content("EX content")
                .writerEmail("user1@naver.com") //Member객체에 존재하는 이메일 써야한다.
                // ( 해당하는 Entity 생성 시, writer_email을 통해 join해서 해당하는 member객체 가져옴.
              //  .writerName("sanghyun")
                .build();
       Long bno= boardService.register(dto);
    }

    @Test
    public void testList(){
        PageRequestDTO pageRequestDTO= new PageRequestDTO();
        //페이지 요청 객체 생성: 생성자로 디폴트 페이지 사이즈 10, 시작 페이지 1로 설정해둠.
        PageResultDTO<BoardDTO,Object[]> pageResultDTO=boardService.getList(pageRequestDTO);
        //getList: JPQL을 사용하고 결과값을 Page<Object[]> 에 저장.
        //적절한 fn을 사용해서 dto로 변환하고,PageResultDTO 의 dtoList 에 저장.
        for(BoardDTO boardDTO: pageResultDTO.getDtoList()){
            System.out.println(boardDTO);//dtoList안 모든 객체 출력
        }
    }

    @Test
    public void testGet(){
        Long bno=100L;
        BoardDTO boardDTO = boardService.get(bno);
        System.out.println(boardDTO);
    }

    @Test
    public void testDeleteBoardWithReply(){
        Long bno= 54L;
        boardService.deleteBoardWithReply(bno);
    }

    @Test
    public void testModifyBoard(){

        BoardDTO boardDTO= BoardDTO.builder()
                .bno(1L)
                .content("Modified content2")
                .title("Modified title2")
                .build();
        boardService.modify(boardDTO);//수정된 BoardDTO를 보냄 > bno로 적절한 Board 인스턴스 찾고,
        // DTO의 content, title만 DTO내용으로 수정

    }

}
