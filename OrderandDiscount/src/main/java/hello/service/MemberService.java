package hello.service;


import hello.member.Member;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


public interface MemberService {
    void join(Member member);

    Member GetMemberbyId(Long id);


}
