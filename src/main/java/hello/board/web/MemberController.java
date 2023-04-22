package hello.board.web;

import hello.board.domain.member.Member;
import hello.board.domain.member.MemberRepository;
import hello.board.service.member.MemberManager;
import hello.board.web.form.member.AddMemberForm;
import hello.board.web.form.member.EditMemberForm;
import hello.board.web.form.member.PasswordEditForm;
import hello.board.web.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;
    private final MemberManager memberManager;
    @GetMapping("/add")
    public String addMemberForm(@ModelAttribute("form") AddMemberForm form){
        return "member/addMemberForm";
    }
    @PostMapping("/add")
    public String addMember(@Validated @ModelAttribute("form")AddMemberForm form, BindingResult bindingResult,
                            RedirectAttributes redirectAttributes){
        //loginId 중복 체크
        memberManager.isDuplicate(form.getLoginId(),bindingResult);
        //비밀번호 일치 확인
        memberManager.isPasswordCheckCoincide(form,bindingResult);

        if(bindingResult.hasErrors()){
            return "member/addMemberForm";
        }

        Member addMember = memberManager.addMember(form);

        redirectAttributes.addAttribute("name",addMember.getName());
        return "redirect:/member/add/welcome";
    }

    @GetMapping("/add/welcome")
    public String addComplete(@RequestParam("name")String name, Model model){
        model.addAttribute("name",name);
        return "member/addWelcome";
    }

    @GetMapping("/info")
    public String memberPageForm(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
                             Model model){
        model.addAttribute("member", loginMember);
        return "member/memberPage";
    }

    @GetMapping("/info/{loginId}")
    public String memberInfoForm(@PathVariable String loginId,
                             @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
                             Model model){
        model.addAttribute("member", loginMember);
        return "member/memberInfo";
    }

    @GetMapping("/info/{loginId}/edit")
    public String memberEditForm(@PathVariable String loginId,
                           @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
                           Model model){
        model.addAttribute("form", memberManager.getEditMemberForm(loginMember));
        return "member/editForm";
    }
    @PostMapping("/info/{loginId}/edit")
    public String editMember(@PathVariable String loginId,
                             @Validated @ModelAttribute("form") EditMemberForm form, BindingResult bindingResult,
                             @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
                             RedirectAttributes redirectAttributes, Model model){
        if(bindingResult.hasErrors()){
            return "member/editForm";
        }
        memberManager.editMember(loginMember, form);
        redirectAttributes.addAttribute("status",true);
        return "redirect:/member/info/{loginId}";
    }
    @GetMapping("/info/{loginId}/editpassword")
    public String editPasswordForm(@PathVariable String loginId, Model model){
        model.addAttribute("form", new PasswordEditForm());
        return "member/editPasswordForm";
    }
    @PostMapping("/info/{loginId}/editpassword")
    public String editPassword(@PathVariable String loginId,
                               @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
                               @Validated @ModelAttribute("form") PasswordEditForm form, BindingResult bindingResult){
        log.info("currentPW=[{}] | edit : [{}] [{}]", form.getCurrentPassword(),form.getEditPassword(),form.getEditPasswordCheck());
        if(!loginMember.getPassword().equals(form.getCurrentPassword())){
            bindingResult.reject("wrongPassword");
        }
        memberManager.isPasswordCheckCoincide(form,bindingResult);
        if(bindingResult.hasErrors()){
            return "member/editPasswordForm";
        }

        memberManager.editPassword(loginMember, form);

        return "redirect:/member/info";
    }
}
