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
            bindingResult.rejectValue("loginId","duplicateLoginId","이미 있는 아이디 입니다.");
            return true;
        }
        return false;
    }
    public boolean isPasswordCorrect(AddMemberForm form, BindingResult bindingResult){
        if (!form.getPassword().equals(form.getPasswordCheck())){
            bindingResult.rejectValue("passwordCheck","incorrectPassword","두 비밀번호가 일치하지 않습니다.");
            return false;
        }
        return true;
    }

    public Member addMember(AddMemberForm form){
        Member member = new Member(form.getName(),
                form.getNickname(),
                form.getLoginId(),
                form.getPassword(),
                form.getEmail());
        memberRepository.save(member);
        return member;
    }

    // 수정 관련

    public void editMember(Member loginMember,EditMemberForm form){
        Member member = new Member();
        member.setName(form.getName());
        member.setNickname(form.getNickname());
        member.setEmail(form.getEmail());
        member.setLoginId(form.getLoginId());
        member.setPassword(loginMember.getPassword());
        memberRepository.updateMember(loginMember.getId(),member);
    }

    public EditMemberForm getEditMemberForm(Member loginMember){
        EditMemberForm form = new EditMemberForm(loginMember.getLoginId(), loginMember.getName(), loginMember.getNickname(), loginMember.getEmail());
        return form;
    }

}
