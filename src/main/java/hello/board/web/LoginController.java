package hello.board.web;

import hello.board.domain.login.LoginService;
import hello.board.domain.member.Member;
import hello.board.web.form.login.LoginForm;
import hello.board.web.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm form,
                            @RequestParam(required = false)boolean loginMsg,
                            Model model){
        //"로그인 시 이용할 수 있는 서비스입니다!" 출력 여부
        if(loginMsg){
            model.addAttribute("loginMsg",true);
        }
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute("loginForm") LoginForm form, BindingResult bindingResult,
                        @RequestParam(defaultValue = "/")String redirectURL,
                        HttpServletRequest request){

        if(bindingResult.hasErrors()){
            return "login/loginForm";
        }
        //Id,pw 입력받아서 loginService.login 로직 실행 -> 성공 : member 반환 , 실패 : null 반환
        Member loginMember = loginService.login(form.getLoginId(),form.getPassword());
        //null 반환시 없는 id입니다 오류 반환
        if(loginMember == null){
            bindingResult.reject("loginFail");
            return "login/loginForm";
        }

        //로그인 성공 로직
        HttpSession session = request.getSession();//세션이 있으면 있는 세션 반환, 없으면 신규세션 생성
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

        //redirectURL에 값이 들어있으면 해당 URL로 redirect 한다.
        if (redirectURL != null) {
            return "redirect:"+redirectURL;
        }

        return "redirect:/board";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request){
        //세션이 있으면 있는 세션 반환, 없으면 신규세션 생성 -> false : 없어도 신규 세션 생성 x , default = true
        HttpSession session = request.getSession(false);//
        if(session != null){
            session.invalidate();
        }
        return "redirect:/board";
    }

}
