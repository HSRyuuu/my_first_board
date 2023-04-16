package hello.board.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemoryMemberRepositoryTest {

    MemberRepository memberRepository = new MemoryMemberRepository();

    @AfterEach
    void afterEach(){
        memberRepository.clearStore();
    }
    @Test
    @DisplayName("save(), findById()")
    void save() {
        //given
        Member member = new Member("test1","test1!");
        //when
        Member savedMember = memberRepository.save(member);
        //then
        Member findMember = memberRepository.findById(savedMember.getMemberId());
        assertThat(savedMember).isEqualTo(findMember);

    }
    @Test
    void findByLoginId() {
        //given
        Member member = new Member("test1","test1!");
        //when
        Member savedMember = memberRepository.save(member);
        //then
        Member findMember = memberRepository.findByLoginId("test1").get();
        assertThat(savedMember).isEqualTo(findMember);
    }

    @Test
    void findAll() {
        //given
        Member member1 = new Member("test1","test1!");
        Member member2 = new Member("test2","test2!");
        //when
        Member savedMember1 = memberRepository.save(member1);
        Member savedMember2 = memberRepository.save(member2);
        //then
        List<Member> list = memberRepository.findAll();
        assertThat(list).containsExactly(member1,member2);
    }
    @Test
    void clearStore() {
    }
}