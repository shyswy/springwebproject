package com.example.board.repository;

import com.example.board.entitiy.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,String> {//객체, PK 타입
}
