package hello.board.repository.mybatis;

import hello.board.domain.member.Member;
import hello.board.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

public class MyBatisMemberRepository implements MemberRepository {
    @Override
    public Member save(Member member) {
        return null;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Member> findByLoginId(String loginId) {
        return Optional.empty();
    }

    @Override
    public List<Member> findAll() {
        return null;
    }

    @Override
    public void updateMember(Long id, Member updateParam) {

    }

    @Override
    public void editPassword(Long id, String password) {

    }
}
