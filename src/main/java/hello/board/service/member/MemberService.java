package hello.board.service.member;

import hello.board.domain.member.Member;
import hello.board.repository.member.MemberRepository;
import hello.board.web.form.member.AddMemberForm;
import hello.board.web.form.member.EditMemberForm;
import hello.board.web.form.member.PasswordEditForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Optional<Member> findById(Long id){
        return memberRepository.findById(id);
    }

    public boolean isDuplicate(String loginId){
        Optional<Member> findMember = memberRepository.findByLoginId(loginId);
        if(findMember.isPresent()){
            return true;
        }
        return false;
    }
    public boolean isPasswordCheckCoincide(String password, String passwordCheck){
        if (password.equals(passwordCheck)){
            return true;
        }
        return false;
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
        memberRepository.updateMember(loginMember.getId(),member);
    }
    public void editPassword(Member loginMember, PasswordEditForm form){
        memberRepository.editPassword(loginMember.getId(), form.getEditPassword());
    }

    public EditMemberForm getEditMemberForm(Member loginMember){
        return new EditMemberForm(loginMember.getLoginId(),
                loginMember.getName(),
                loginMember.getNickname(),
                loginMember.getEmail());
    }

}
