package sanghyun.projectname1.repository;

import org.springframework.stereotype.Repository;
import sanghyun.projectname1.domain.Member;

import java.util.*;





//모든 회원 정보는 memory 상에 위치하기 때문에, 서버를 내렸다가 다시 올리면 모든 회원정보는 초기화 된다 ( Volatile )
// >> DB 의 필요성!!!!
//@Repository     repository임을 spring에게 알려준다 자동등록방식
public class MemoryMemberRepository implements MemberRepository { //MemberRepository 대고 fn +enter로 Mem repos안 모든
    //객체 get 가능.

    private static Map<Long,Member> store= new HashMap<>(); //저장소 hashmap
    private static long sequence = 0L; // sequence: 키값을 생성해준다



    //아래 save는   insert into member(name) values('sanghyun')  h2 db 에서 이런식으로 추가.
    @Override
    public Member save(Member member) { //mem에서 name은 이미 세팅된채로 오는 것으로 가정
        member.setId(++sequence); //member에 id 값 세팅
        store.put(member.getId(),member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));// null반환될 수 도 있기에 Optional로 감싼다.
    }

    @Override
    public Optional<Member> findByName(String name) { //반환은 optional로..
       return store.values().stream()   //stream 훑으며 filter한다.
                .filter(member -> member.getName().equals(name)) //param으로 넘어온 name과 같은 name값일시만 filtering
                .findAny(); //하나라도 찾으면(filter되면) 반환

    }
    public void clearStore() {
        store.clear();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());   // store에 들어있는 모든 값들을 array로 반환
    }
}
