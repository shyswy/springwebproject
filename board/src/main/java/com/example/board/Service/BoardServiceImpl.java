package com.example.board.Service;

import com.example.board.DTO.BoardDTO;
import com.example.board.DTO.PageRequestDTO;
import com.example.board.DTO.PageResultDTO;
import com.example.board.entitiy.Board;
import com.example.board.entitiy.Member;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.ReplyRepository;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.Function;

@Service
@Data
@Log4j2
public class BoardServiceImpl implements BoardService{


    //@Data 안의 requiredArgConstruct로 Autowired를 일일이 해줄 필요 없이 생성자 주입 가능
    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;

    @Override
    public Long register(BoardDTO dto) {
        log.info(dto);
        Board board=DtoToEntity(dto);
        boardRepository.save(board);
        return board.getBno();
    }

    @Override
    public void modify(BoardDTO dto) { //해당 DTO와 매칭되는 Board 인스턴스를 repository에서 찾고, DTO의 수정사항 반영.

        Board board = boardRepository.findById(dto.getBno())
                .orElseThrow(() ->new IllegalArgumentException("no such BoardDTO"));
        board.changeContent(dto.getContent());
        board.changeTitle(dto.getTitle());
        boardRepository.save(board);
    }


    @Override
    public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {
        log.info(pageRequestDTO);
        //PageResultDTO의 생성자: JPQL의 결과인 Page<Object[]>, 해당 Page<Object[]>를 적절한 DTO로 변환할 Function으로 구성된다.

        //Function<T,R>   :  객체 T를 R 로 매핑
        // Page의 각 요소 Object[] 안에는  JPQL에서 select한 Board, writer(Member) count(r) [Long] 가 저장되있다.
        //                                             Object[0]  Object[1]   Object[2]
        Function<Object[],BoardDTO> fn=(en ->EntityToDTO(  (Board) en[0], (Member) en[1], (Long) en[2]) );
        //각각 올바르게 타입 변환을 해준 뒤,

        Page<Object[]> result= boardRepository.getBoardWithReplyCount(
                pageRequestDTO.getPageable(
                        Sort.by("bno").descending()
                ));
        return new PageResultDTO<>(result,fn);
    }

    @Override
    public BoardDTO get(Long bno) {
        Object[] boardByBno = (Object[]) boardRepository.getBoardByBno(bno);
        return EntityToDTO((Board) boardByBno[0],(Member) boardByBno[1],(Long) boardByBno[2] );

    }

    @Transactional
    @Override
    public void deleteBoardWithReply(Long bno) {
        replyRepository.deleteByBno(bno);
        boardRepository.deleteById(bno);
    }


}
