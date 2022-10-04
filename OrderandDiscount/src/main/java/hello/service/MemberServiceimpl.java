package hello.service;

import hello.member.Member;
import hello.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;



@Service  //@Service >> component scan + service라는 카테고리화
@RequiredArgsConstructor // 생성자 주입
public class MemberServiceimpl implements MemberService{



        private final MemberRepository memberRepository;

        @Override
        public void join(Member member) {
            memberRepository.save(member);
        }

        @Override
        public Member GetMemberbyId(Long memberId) {
            return memberRepository.findById(memberId);
        }



        //for Test
        public MemberRepository getMemberRepository() {
            return memberRepository;
        }
    }

