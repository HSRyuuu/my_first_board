package hello.board.web.member;

import hello.board.domain.member.Member;
import hello.board.domain.member.MemberRepository;
import hello.board.web.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Slf4j
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
        //loginId 중복 체크
        Optional<Member> duplicate = memberRepository.findByLoginId(form.getLoginId());
        if(duplicate.isPresent()){
            bindingResult.reject("duplicateLoginId","이미 있는 아이디 입니다.");
            return "member/addMemberForm";
        }

        Member member = new Member(form.getLoginId(),form.getPassword());
        member.setName(form.getName());
        memberRepository.save(member);
        return "redirect:/login";
    }

    @GetMapping("/info")
    public String memberInfo(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
                             Model model){

        if(loginMember == null){
            model.addAttribute("msg",true);
            return "login/loginForm";
        }

        model.addAttribute("member",loginMember);
        return "member/memberPage";
    }
    @GetMapping("/info/{loginId}")
    public String memberPersonalInfo(@PathVariable String loginId,
                             @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
                             Model model){
        if(loginMember == null){
            model.addAttribute("msg",true);
            return "login/loginForm";
        }

        model.addAttribute("member",loginMember);

        return "member/memberInfo";
    }

    @GetMapping("/info/{loginId}/edit")
    public String memberEditForm(@PathVariable String loginId,
                           @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
                           Model model){
        if(loginMember == null){
            model.addAttribute("msg",true);
            return "login/loginForm";
        }
        Optional<Member> member = memberRepository.findByLoginId(loginMember.getLoginId());
        model.addAttribute("member",member.get());
        return "member/editForm";
    }
    @PostMapping("/info/{loginId}/edit")
    public String editMember(@PathVariable String loginId,
                           @Validated @ModelAttribute("member")EditMemberForm form, BindingResult bindingResult,
                           @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
                             RedirectAttributes redirectAttributes, Model model){
        if(bindingResult.hasErrors()){
            return "member/editForm";
        }
        if(loginMember == null){
            model.addAttribute("msg",true);
            return "login/loginForm";
        }
        Member member = new Member(form.getLoginId(),form.getPassword());
        member.setName(form.getName());
        memberRepository.updateMember(loginMember.getId(),member);
        redirectAttributes.addAttribute("status",true);
        return "redirect:/member/info/{loginId}";
    }
}
