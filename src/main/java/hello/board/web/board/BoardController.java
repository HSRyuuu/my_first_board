package hello.board.web.board;

import hello.board.domain.member.Member;
import hello.board.domain.member.MemberRepository;
import hello.board.domain.post.Post;
import hello.board.domain.post.PostRepository;
import hello.board.web.format.HtmlFormatter;
import hello.board.web.session.SessionConst;
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
    public String boardHome(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
                            Model model){

        boolean show = true;
        if(loginMember == null){
            show = false;
        }else {
            model.addAttribute("member", loginMember);
        }
        List<Post> posts = postRepository.findAll();
        model.addAttribute("show",show);
        model.addAttribute("posts",posts);
        model.addAttribute("form",new FindByWriterIdForm());
        return "board/board";
    }
    @PostMapping
    public String findByWriterId(@Validated @ModelAttribute("form") FindByWriterIdForm form, BindingResult bindingResult,
                                 @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
                                 RedirectAttributes redirectAttributes,
                                 Model model){

        Optional<Member> member = memberRepository.findByLoginId(form.getWriterId());
        //빈칸일때는 다시 메인화면으로
        if(bindingResult.hasErrors()){
            log.info("error={}",bindingResult);
            model.addAttribute("posts",postRepository.findAll());
            return "redirect:/board";
        }else if(member.isEmpty()){ //Id로 찾은 결과가 없을 때
            log.info("검색결과없음");
            if(loginMember == null){
                bindingResult.reject("notFoundByWriterId");
                model.addAttribute("posts",postRepository.findAll());
                return "board/board";
            }else{
                bindingResult.reject("notFoundByWriterId");
                model.addAttribute("posts",postRepository.findAll());
                model.addAttribute("show",true);
                model.addAttribute("member",loginMember);
                return "board/board";
            }
        }

        List<Post> listByWriterId = postRepository.findByWriterId(form.getWriterId());

        //성공 로직
        model.addAttribute("posts",listByWriterId);
        redirectAttributes.addAttribute("writerId",form.getWriterId());

        return "redirect:/board/find/{writerId}";
    }

    @GetMapping("/{postId}")
    public String showPost(@PathVariable long postId, Model model){
        Post post = postRepository.findById(postId);
        Long view = post.getViews();
        post.setViews(++view);
        model.addAttribute("post", post);
        return "board/post";
    }

    @GetMapping("/find/{writerId}")
    public String findPost(@PathVariable String writerId, Model model){
        List<Post> posts = postRepository.findByWriterId(writerId);
        if(posts.isEmpty()){
            return "redirect:/board";
        }
        model.addAttribute("posts", posts);
        model.addAttribute("post1",posts.get(0));
        return "board/findPosts";
    }

    @GetMapping("/write-form")
    public String writeForm(Model model){
        model.addAttribute("form", new WritingForm());
        return "board/writeForm";
    }
    @PostMapping("/write-form")
    public String addWriting(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
                             @Validated @ModelAttribute("form")WritingForm form, BindingResult bindingResult){

        // 검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            return "board/writeForm";
        }
        //글을 html에서 줄바꿈이 적용되도록 변경
        HtmlFormatter htmlFormatter = new HtmlFormatter();
        String formattedContent = htmlFormatter.getFormattedContent(form.getContent());

        //작성자를 세션에서 꺼내와서 등록
        Post post = new Post(loginMember.getLoginId(), form.getTitle(),formattedContent);
        postRepository.save(post);

        return "redirect:/board";
    }

}
