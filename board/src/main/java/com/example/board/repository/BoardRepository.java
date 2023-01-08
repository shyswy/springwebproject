package com.example.board.repository;

import com.example.board.entitiy.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board,Long> { //엔티티, PK 타입> (findbyID의 파라미터 타입을 정의한다)

    @Query("select b,w from Board b left join b.writer w where b.bno=:bno")
    Object getBoardWithWriter(@Param("bno") Long bno); //해당 함수로 위의 쿼리문 트리거.
    // Board   :   Member(Writer)
    //  다      대    1

    @Query("select b,r from Board b left join Reply r on r.board=b where b.bno=:bno")
    List<Object[]> getBoardWithReply(@Param("bno") Long bno);
    // Board   :    Reply
    //   1     대    다     >>on





    @Query(value ="SELECT b, w,count(r) "+
            " FROM Board b" +
            " LEFT JOIN b.writer w " +
            " LEFT JOIN Reply r ON r.board = b "+
            "GROUP BY b",
            countQuery ="SELECT count(b) FROM Board b")
    //리턴 타입이 Page<E> 타입인 경우 페이지의 번호나 다음페이지, 이전 페이지의 유무를 처리
    // >> countQUery를 명시적으로 만들어줘야한다.
    Page<Object[]> getBoardWithReplyCount(Pageable pageable);
    //목록 페이지 하나당 pageable 기반 size,sort order로 board객체, 해당 board의 작성자(writer), 해당 board에 대한 reply 수
    //를 되돌려준다. ( 목록 페이지 처리용)
    // Page의 각 요소 Object[] 안에는   slect에 위치한 Board,writer(Member) count(r) [Long]  을 각각 저장

    @Query(value="SELECT b,w, count(r) " +
            " FROM Board b LEFT JOIN b.writer w " +
            " LEFT OUTER JOIN Reply r ON r.board = b" +
            " Group by b" +
            " having b.bno = :bno",
            countQuery ="SELECT count(b) FROM Board b")
    Object getBoardByBno(@Param("bno") Long bno);
    //특정 게시무에 대한 writer, reply 수 정보.



}
