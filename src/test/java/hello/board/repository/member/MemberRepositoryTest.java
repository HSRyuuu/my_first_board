package hello.board.repository.member;

import hello.board.domain.member.Member;
import hello.board.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@Transactional
@SpringBootTest
class MemberRepositoryTest {
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
        assertThat(allMember).contains(savedMember1,savedMember2);
    }

    @Test
    void updateMember() {
        //given
        Member member = new Member("name","nickname","loginId","pwTest","test@test.com");
        Member savedMember = memberRepository.save(member);

        //when
        Member memberParam = new Member();
        memberParam.setName("updateName");
        memberParam.setNickname("updateNickName");
        memberParam.setEmail("update@test.com");
        memberRepository.updateMember(savedMember.getId(), memberParam);
        Member findMember = memberRepository.findById(savedMember.getId()).get();

        //then
        assertThat(findMember.getName()).isEqualTo(memberParam.getName());
        assertThat(findMember.getNickname()).isEqualTo(memberParam.getNickname());
        assertThat(findMember.getEmail()).isEqualTo(memberParam.getEmail());

    }

    @Test
    void editPassword() {
        //given
        Member member = new Member("name","nickname","loginId","pwTest","test@test.com");
        Member savedMember = memberRepository.save(member);

        //when
        memberRepository.editPassword(savedMember.getId(), "pwTestEdit");
        Member findMember = memberRepository.findById(savedMember.getId()).get();
        //then
        assertThat(findMember.getPassword()).isEqualTo("pwTestEdit");

    }
}