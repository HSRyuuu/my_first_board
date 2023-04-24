package hello.board.repository.member;

import hello.board.domain.member.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryMemberRepository implements MemberRepository{
    private static final Map<Long, Member> store = new HashMap<>();
    private static Long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Member findById(Long id) {
        return store.get(id);
    }

    @Override
    public Optional<Member> findByLoginId(String loginId) {
        return findAll().stream()
                .filter(m -> m.getLoginId().equals(loginId))
                .findFirst();
    }
    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void editPassword(Long id, String password) {
        Member member = findById(id);
        member.setPassword(password);
    }

    @Override
    public void updateMember(Long id,Member updateParam) {
        Member member = findByLoginId(updateParam.getLoginId()).get();
        member.setPassword(updateParam.getPassword());
        member.setName(updateParam.getName());
        member.setNickname(updateParam.getNickname());
        member.setEmail(updateParam.getEmail());
    }



    @Override
    public void clearStore() {
        store.clear();
    }

}
