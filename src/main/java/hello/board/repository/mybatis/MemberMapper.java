package hello.board.repository.mybatis;

import hello.board.domain.member.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.List;
import java.util.Optional;

@Mapper
public interface MemberMapper {
    void save(Member member);
    Optional<Member> findById(Long id);
    Optional<Member> findByLoginId(String loginId);
    List<Member> findAll();
    void updateMember(@Param("id") Long id, @Param("updateParam") Member updateParam);
    void editPassword(@Param("id") Long id, @Param("password") String password);


}
