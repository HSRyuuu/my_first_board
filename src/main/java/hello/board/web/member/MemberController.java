package hello.board.web.member;

import hello.board.domain.member.Member;
import hello.board.domain.member.MemberRepository;
import hello.board.web.form.AddMemberForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")

@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;
    @GetMapping("/add")
    public String addMemberForm(@ModelAttribute("form")AddMemberForm form){
        return "member/addMemberForm";
    }
    @PostMapping("/add")
    public String addMember(@Validated @ModelAttribute("form")AddMemberForm form, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "member/addMemberForm";
        }
        Member member = new Member(form.getLoginId(),form.getPassword());
        member.setName(form.getName());
        memberRepository.save(member);
        return "redirect:/";
    }


}
