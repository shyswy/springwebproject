package com.example.board.Service;

import com.example.board.DTO.BoardDTO;
import com.example.board.DTO.PageRequestDTO;
import com.example.board.DTO.PageResultDTO;
import com.example.board.entitiy.Board;
import com.example.board.entitiy.Member;

public interface BoardService {

    Long register(BoardDTO dto);

    void modify(BoardDTO dto);
    default Board DtoToEntity(BoardDTO dto){
        Member member=Member.builder()
                .email(dto.getWriterEmail())
                .name(dto.getWriterName())
                .build();
        Board board=Board.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(member)
                .build();
        return board;

    }
    default BoardDTO EntityToDTO(Board board,Member writer, Long replyCount){
        BoardDTO boardDTO=BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .writerName(writer.getName())
                .writerEmail(writer.getEmail())
                .replyCount(replyCount)
                .build();
        return boardDTO;
    }
    PageResultDTO<BoardDTO,Object[]> getList(PageRequestDTO pageRequestDTO);

    BoardDTO get(Long bno);

    void deleteBoardWithReply(Long bno);
}
