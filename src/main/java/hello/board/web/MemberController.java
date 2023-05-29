package hello.board.web;

import hello.board.domain.member.Member;
import hello.board.service.member.MemberService;
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

    private final MemberService memberService;
    @GetMapping("/add")
    public String addMemberForm(@ModelAttribute("form") AddMemberForm form){
        return "member/addMemberForm";
    }
    @PostMapping("/add")
    public String addMember(@Validated @ModelAttribute("form")AddMemberForm form, BindingResult bindingResult,
                            RedirectAttributes redirectAttributes){
        log.info("AddMember [{}]",form);
        //loginId 중복 체크
        if(memberService.isDuplicate(form.getLoginId())){
            bindingResult.rejectValue("loginId","duplicateLoginId","이미 있는 아이디 입니다.");
        }

        //비밀번호 일치 확인
        if(!memberService.isPasswordCheckCoincide(form.getPassword(), form.getPasswordCheck())){
            bindingResult.rejectValue("passwordCheck", "notCoincidePassword","두 비밀번호가 일치하지 않습니다.");
        }


        if(bindingResult.hasErrors()){
            return "member/addMemberForm";
        }

        Member addMember = memberService.addMember(form);

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
        model.addAttribute("member", memberService.findById(loginMember.getId()).get());
        return "member/memberPage";
    }

    @GetMapping("/info/{loginId}")
    public String memberInfoForm(@PathVariable String loginId,
                             @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
                             Model model){
        model.addAttribute("member", memberService.findById(loginMember.getId()).get());
        return "member/memberInfo";
    }

    @GetMapping("/info/{loginId}/edit")
    public String memberEditForm(@PathVariable String loginId,
                           @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
                           Model model){
        Member member = memberService.findById(loginMember.getId()).get();
        model.addAttribute("form", memberService.getEditMemberForm(member));
        return "member/editForm";
    }
    @PostMapping("/info/{loginId}/edit")
    public String editMember(@PathVariable String loginId,
                             @Validated @ModelAttribute("form") EditMemberForm form, BindingResult bindingResult,
                             @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
                             RedirectAttributes redirectAttributes, Model model){
        log.info("Edit Member Info : {}", form);
        if(bindingResult.hasErrors()){
            return "member/editForm";
        }
        memberService.editMember(loginMember, form);
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
        log.info("currentPW=[{}] , edit : [{}]", form.getCurrentPassword(),form);
        loginMember = memberService.findById(loginMember.getId()).get();
        if(!loginMember.getPassword().equals(form.getCurrentPassword())){
            log.info("현재 비밀번호 오류" );

            bindingResult.reject("wrongPassword");
        }

        if(!memberService.isPasswordCheckCoincide(form.getEditPassword(),form.getEditPasswordCheck())){
            log.info("변경 비밀번호 일치 오류");
            bindingResult.reject( "notCoincidePassword","두 비밀번호가 일치하지 않습니다.");
        }

        if(bindingResult.hasErrors()){
            log.info("필드 에러 발생 ");
            return "member/editPasswordForm";
        }

        memberService.editPassword(loginMember, form);

        return "redirect:/member/info";
    }
}
