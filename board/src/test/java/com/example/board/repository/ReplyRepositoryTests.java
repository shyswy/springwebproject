package com.example.board.repository;

import com.example.board.entitiy.Board;
import com.example.board.entitiy.Reply;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class ReplyRepositoryTests {
    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void insertTest(){
        IntStream.rangeClosed(1,100).forEach(i->{
            Long bno=(long) (Math.random()*100)+1;
            Board board=Board.builder()
                    .bno(bno)
                    .build();
            Reply reply=Reply.builder()
                    .board(board)
                    .replyer("guest"+i)
                    .text("text contents")
                    .build();
            replyRepository.save(reply);
        });
    }
}
