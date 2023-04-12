package hello.board.web.board;

import hello.board.domain.member.Member;
import hello.board.domain.member.MemberRepository;
import hello.board.domain.post.Post;
import hello.board.domain.post.PostRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

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
        model.addAttribute("form",new FindByWriterIdForm());
        return "board/board";
    }
    @PostMapping()
    public String findByWriterId(@Validated @ModelAttribute("form") FindByWriterIdForm form,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 Model model){

        Optional<Member> member = memberRepository.findByLoginId(form.getWriterId());

        //빈칸일때는 다시 메인화면으로
        if(bindingResult.hasErrors()){
            log.info("error={}",bindingResult);
            model.addAttribute("posts",postRepository.findAll());
            return "board/board";
        }else if(member.isEmpty()){ //Id로 찾은 결과가 없을 때
            log.info("검색결과없음");
            bindingResult.reject("notFoundByWriterId","검색 결과 없음");
            model.addAttribute("posts",postRepository.findAll());
            return "board/board";
        }

        List<Post> listByWriterId = postRepository.findByWriterId(form.getWriterId());

        //성공 로직
        model.addAttribute("posts",listByWriterId);
        redirectAttributes.addAttribute("writerId",form.getWriterId());

        return "redirect:/board/find/{writerId}";
    }
    @GetMapping("/{postId}")
    public String post(@PathVariable long postId, Model model){
        Post post = postRepository.findById(postId);
        model.addAttribute("post", post);
        return "board/post";
    }

    @GetMapping("/find/{writerId}")
    public String post(@PathVariable String writerId, Model model){
        List<Post> posts = postRepository.findByWriterId(writerId);
        if(posts.isEmpty()){
            return "redirect:/board";
        }
        model.addAttribute("posts", posts);
        model.addAttribute("post1",posts.get(0));
        return "board/findPosts";
    }





}
