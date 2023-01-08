package com.example.board.repository;

import com.example.board.entitiy.Board;
import com.example.board.entitiy.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class BoardRepositoryTests {
    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void insertTest(){
        IntStream.rangeClosed(1,100).forEach(i->{
            Member member=Member.builder()
                    .email("user"+i+"@naver.com")
                    .build();
            Board board=Board.builder()
                    .title("title"+i)
                    .content("hello i am content " )
                    .writer(member)
                    .build();
            boardRepository.save(board);

        });

    }

    @Test
    @Transactional// Board가 relate된 Member에 대해 lazy fetch 로 설정했다. 따라서 그냥하면 아직 로딩 안되어있을 수 도.
    //could not initialize proxy 에러 발생! >> transactional로 한 덩어리로 만든다.
    public void SeeBoardOfMember(){
       Optional <Board> result=boardRepository.findById(100L); //bno가 Board의 PK. 임의로 생성된 bno 중 아무거나 1개 골라서
        Board board=result.get();
        System.out.println(board); //ManyToOne의 관계>자동으로 join처리가 수행된다.
        System.out.println(board.getWriter());
    }
    @Test
    public void ReadBoardWithWriter(){
       Object result= boardRepository.getBoardWithWriter(100L);
       Object[] arr=(Object[]) result;
       System.out.println("------------------");
       System.out.println(Arrays.toString(arr));
    }
    @Test
    public void ReadBoardWithReply(){
        List<Object[]> result= boardRepository.getBoardWithReply(100L);
        for(Object[] arr: result) {
            System.out.println("------------------");
            System.out.println(Arrays.toString(arr));
        }
    }

    @Test
    public void testwithReplyCount(){
        Pageable pageable= PageRequest.of(0,10, Sort.by("bno").descending());
        //0페이지 부터 10개의 페이지를 "bno" 라는 property의 descedning order로
        Page<Object[]> result= boardRepository.getBoardWithReplyCount(pageable);
        result.get().forEach(row -> {
            Object[] arr=(Object[]) row;
            System.out.println(Arrays.toString(arr));
        });
    }

    @Test void testRead3(){
        Object result= boardRepository.getBoardByBno(100L);
        Object[] arr=(Object[]) result;
        System.out.println(Arrays.toString(arr));
    }
}
