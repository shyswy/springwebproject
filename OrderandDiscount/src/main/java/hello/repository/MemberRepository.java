package hello.repository;

import hello.member.Member;

public interface MemberRepository {

    Member findById(Long id);

    void save(Member member);
}
