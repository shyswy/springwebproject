package hello.repository;

import hello.member.Member;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Component
@Data // data에 Requiredargconstructor>> 생성자 주입해준다! ( autowired 필요x)
public class MemoryMemberRepository implements MemberRepository{
    private static Map<Long, Member> store = new HashMap<>();

    @Override
    public void save(Member member) {
        store.put(member.getId(), member);
    }

    @Override
    public Member findById(Long memberId) {

        return store.get(memberId);
    }
}
