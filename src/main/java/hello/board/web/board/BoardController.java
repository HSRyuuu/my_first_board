package hello.board.web.board;

import hello.board.domain.member.MemberRepository;
import hello.board.domain.post.Post;
import hello.board.domain.post.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    @GetMapping
    public String boardHome(Model model){
        List<Post> posts = postRepository.findAll();
        model.addAttribute("posts",posts);
        return "board/board";
    }

}
