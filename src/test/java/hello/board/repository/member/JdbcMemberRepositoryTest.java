package hello.board.repository.member;

import hello.board.domain.member.Member;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
@Slf4j
@Transactional
@SpringBootTest
class JdbcMemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;
    @Test
    @DisplayName("save and findById")
    void save() {
        //given
        Member member = new Member("name","nickname","loginId","pwTest","test@test.com");

        //when
        Member savedMember = memberRepository.save(member);

        //then
        assertThat(memberRepository.findById(savedMember.getId()).get()).isEqualTo(member);
    }
    @Test
    void findByLoginId() {
        //given
        Member member = new Member("name","nickname","loginId","pwTest","test@test.com");
        Member savedMember = memberRepository.save(member);

        //when
        Member findMember = memberRepository.findByLoginId("loginId").get();

        //then
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    void findAll() {
        //given
        Member member1 = new Member("name1","nickname1","loginId1","pwTest1","test1@test.com");
        Member member2 = new Member("name2","nickname2","loginId2","pwTest2","test2@test.com");
        Member savedMember1 = memberRepository.save(member1);
        Member savedMember2 = memberRepository.save(member2);

        //when
        List<Member> allMember = memberRepository.findAll();

        //then
        assertThat(allMember).containsExactly(savedMember1,savedMember2);
    }

    @Test
    void updateMember() {
        //given

        //when

        //then

    }

    @Test
    void editPassword() {
        //given

        //when

        //then

    }
}