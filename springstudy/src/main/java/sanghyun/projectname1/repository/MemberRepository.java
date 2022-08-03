package sanghyun.projectname1.repository;


import sanghyun.projectname1.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {//null 대신 optinal로 감싼다
    Member save(Member member); //정보를 저장소에 저장
    Optional<Member> findById(Long id);   //id나
    Optional <Member> findByName(String name);//name 으로 정보 get
    List<Member> findAll();//회원 리스트.

}
