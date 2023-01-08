package com.example.board.repository;

import com.example.board.entitiy.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class MemberRepositoryTests {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void insertMembers(){
        IntStream.rangeClosed(1,100).forEach(i->{
            Member member= Member.builder()
                    .email("user"+i+"@naver.com") //email is PK
                    .name("user"+i)
                    .password("user"+i)
                    .build();
            memberRepository.save(member);
        });

    }
}
