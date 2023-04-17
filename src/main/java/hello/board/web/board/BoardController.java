package hello.board.web.board;

import hello.board.domain.member.Member;
import hello.board.domain.member.MemberRepository;
import hello.board.domain.post.Post;
import hello.board.domain.post.PostRepository;
import hello.board.web.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
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
        model.addAttribute("form",new SearchForm());

        return "board/board";
    }
    //TODO 종류에따라 다른 list 전달
    @PostMapping
    public String searchResult(@Validated @ModelAttribute("form") SearchForm form, BindingResult bindingResult,
                               @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
                               RedirectAttributes redirectAttributes,
                               Model model){
        //빈칸일때는 다시 메인화면으로
        if(bindingResult.hasErrors()){
            log.info("error={}",bindingResult);
            return "redirect:/board";
        }
        Optional<Member> member = memberRepository.findByLoginId(form.getWriterId());

        if(member.isEmpty()){ //Id로 찾은 결과가 없을 때
            log.info("검색결과없음");
            if(loginMember == null){
                bindingResult.reject("notFoundByWriterId");
                return "board/board";
            }else{
                bindingResult.reject("notFoundByWriterId");
                model.addAttribute("show",true);
                model.addAttribute("member",loginMember);
                return "board/board";
            }
        }
        //성공 로직
        redirectAttributes.addAttribute("writerId",form.getWriterId());
        return "redirect:/board/find/{writerId}";
    }
    //TODO 입력받은 값에 따라 경로 PathVariable 바꿔보기
    @GetMapping("/find/{writerId}")
    public String findByWriterId(@PathVariable String writerId, Model model){
        List<Post> findPosts = postRepository.findByWriterId(writerId);
        if(findPosts.isEmpty()){
            return "redirect:/board";
        }
        model.addAttribute("findPosts",findPosts);
        model.addAttribute("post1",findPosts.get(0));
        return "board/findPosts";
    }
    

    @GetMapping("/write-form")
    public String writeForm(Model model){
        model.addAttribute("form", new WritingForm());
        return "post/writeForm";
    }
    @PostMapping("/write-form")
    public String addWriting(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
                             @Validated @ModelAttribute("form")WritingForm form, BindingResult bindingResult){

        // 검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            return "post/writeForm";
        }

        //작성자를 세션에서 꺼내와서 등록
        Post post = new Post(loginMember.getLoginId(), form.getTitle(),form.getContent());
        postRepository.save(post);

        return "redirect:/board";
    }

    @ModelAttribute("searchCodes")
    public List<PostSearchCode> getSearchList(){
        List<PostSearchCode> searchCodes = new ArrayList<>();
        searchCodes.add(new PostSearchCode("findByTitle", "제목"));
        searchCodes.add(new PostSearchCode("findByContent", "내용"));
        searchCodes.add(new PostSearchCode("findByWriterId", "작성자"));
        return searchCodes;
    }

    @ModelAttribute("posts")
    public List<Post> posts(){
        return postRepository.findAll();
    }
}