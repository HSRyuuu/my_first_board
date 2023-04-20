package hello.board.service.member;

import hello.board.domain.member.Member;
import hello.board.domain.member.MemberRepository;
import hello.board.web.member.form.AddMemberForm;
import hello.board.web.member.form.EditMemberForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberManager {
    private final MemberRepository memberRepository;

    public boolean isDuplicate(String loginId, BindingResult bindingResult){
        Optional<Member> duplicate = memberRepository.findByLoginId(loginId);
        if(duplicate.isPresent()){
            bindingResult.reject("duplicateLoginId","이미 있는 아이디 입니다.");
            return true;
        }
        return false;
    }

    public Member addMember(AddMemberForm form){
        Member member = new Member(form.getLoginId(),form.getPassword());
        member.setName(form.getName());
        memberRepository.save(member);
        return member;
    }

    public void editMember(Member loginMember,EditMemberForm form){
        Member member = new Member(form.getLoginId(),form.getPassword());
        member.setName(form.getName());
        memberRepository.updateMember(loginMember.getId(),member);

    }


}
