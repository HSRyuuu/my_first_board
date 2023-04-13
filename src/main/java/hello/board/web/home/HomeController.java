package hello.board.web.home;

import hello.board.domain.member.Member;
import hello.board.web.session.SessionConst;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequiredArgsConstructor
public class HomeController {

    /**
     * Spring이 제공하는 @SessionAttribute 사용
     * 이미 생성된 session이 있는지 확인하고, 있으면 loginMember에 담아준다.
     */
    @GetMapping("/")
    public String home(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember, Model model){
        if(loginMember == null){
            return "home";
        }
        model.addAttribute("member", loginMember);
        return "redirect:/board";
    }
}
