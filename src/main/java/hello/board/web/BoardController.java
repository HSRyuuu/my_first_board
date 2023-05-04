package hello.board.web;

import hello.board.domain.member.Member;
import hello.board.domain.post.Post;
import hello.board.service.member.MemberService;
import hello.board.service.post.PostService;
import hello.board.domain.post.PostSearchCode;
import hello.board.web.form.board.Searchform;
import hello.board.web.form.board.WritingForm;
import hello.board.web.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Slf4j
@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final PostService postService;
    private final MemberService memberService;

    /**
     * 게시판 메인화면 GET
     * show = true : 로그인 o - 멤버정보 메뉴, 로그아웃버튼 출력
     * show = false : 로그인 x - 로그인, 회원가입 버튼 출력
     */
    @GetMapping
    public String boardHomeForm(@ModelAttribute("form") Searchform form, BindingResult bindingResult,
                                @RequestParam(required = false) String searchCode, @RequestParam(required = false) String searchWord,
                                @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
                            Model model){
        model.addAttribute("show",isLoggedin(loginMember, model));

        List<Post> posts;
        if(searchCode!=null && searchWord!=null){
            posts = postService.findPosts(new Searchform(searchCode,searchWord));
        }else posts = postService.findPosts(form);

        if(posts.size()==0){
            bindingResult.reject("notFoundResult");
            model.addAttribute("posts",postService.findPosts(new Searchform()));
            return "board/board";
        }
        model.addAttribute("posts",posts);
        return "board/board";
    }

    /**
     * 글쓰기 폼 GET
     */
    @GetMapping("/write-form")
    public String writeForm(Model model){
        model.addAttribute("form", new WritingForm());
        return "post/writeForm";
    }

    /**
     * 세션에 존재하는 loginMember를 작성자로 등록
     */
    @PostMapping("/write-form")
    public String addWriting(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
                             @Validated @ModelAttribute("form")WritingForm form, BindingResult bindingResult,
                             RedirectAttributes redirectAttributes){

        // 검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            return "post/writeForm";
        }

        //작성자를 세션에서 꺼내와서 등록
        Post post = new Post(loginMember.getLoginId(), form.getTitle(),form.getContent());
        Post savedPost = postService.save(post);

        redirectAttributes.addAttribute("postId",savedPost.getId());
        return "redirect:/board/post/{postId}";
    }

    /**
     * 로그인 체크 메소드
     * loginMember o : model에 loginMember와 show=true를 담음
     * loginMember x : show=false를 담음
     * @return Boolean show
     */
    private boolean isLoggedin(Member loginMember, Model model) {
        if(loginMember == null){
            return false;
        }
        else{
            model.addAttribute("member", memberService.findById(loginMember.getId()).get());
            return true;
        }
    }

    @ModelAttribute("searchCodes")
    public List<PostSearchCode> searchCodes(){
        return postService.getSearchCodes();
    }

}