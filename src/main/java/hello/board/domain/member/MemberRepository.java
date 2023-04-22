package hello.board.domain.member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member); // 저장
    Member findById(Long id); //시스템 id로 찾기
    Optional<Member> findByLoginId(String loginId); //로그인 id로 찾기
    List<Member> findAll(); // 모든 멤버 list로 반환
    void updateMember(Long id,Member updateParam);//멤버 수정
    void editPassword(Long id, String password);

    void clearStore();
}
