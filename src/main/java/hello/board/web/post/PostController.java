package hello.board.web.post;

import hello.board.domain.member.Member;
import hello.board.domain.member.MemberRepository;
import hello.board.domain.post.Post;
import hello.board.domain.post.PostRepository;
import hello.board.web.format.HtmlFormatter;
import hello.board.web.session.SessionConst;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/board/post")
@RequiredArgsConstructor
public class PostController {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    static HtmlFormatter htmlFormatter = new HtmlFormatter();

    @GetMapping("/{postId}")
    public String showPost(@PathVariable long postId, Model model,
                           @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember){
        Post post = postRepository.findById(postId);
        if (loginMember != null) {
            if(post.getWriterId().equals(loginMember.getLoginId())){
                model.addAttribute("access",true);
            }
        }
        //조회수 로직
        Long view = post.getViews();
        post.setViews(++view);
        post.setContent(htmlFormatter.getHtmlContent(post.getContent()));
        model.addAttribute("post", post);
        return "post/post";
    }
    @GetMapping("/{postId}/edit")
    public String editPostForm(@PathVariable Long postId, Model model){
        Post post = postRepository.findById(postId);

        // view에 넘겨줄 form
        PostEditForm form = new PostEditForm();
        form.setTitle(post.getTitle());
        form.setContent(htmlFormatter.getStringContent(post.getContent()));//글을 html에서 줄바꿈이 적용되도록 변경
        model.addAttribute("post",post);
        model.addAttribute("form",form);
        return "post/postEditForm";
    }

    @PostMapping("/{postId}/edit")
    public String editPost(@PathVariable long postId, @ModelAttribute("post")PostEditForm form){
        Post updatePost = new Post(form.getTitle(),form.getContent());
        postRepository.updatePost(postId,updatePost);

        return "redirect:/board/post/{postId}";

    }

    /**
     * TODO 삭제기능 만들기
     */
    @GetMapping("/{postId}/delete")
    public String deleteForm(@PathVariable long postId, Model model){
        Post post = postRepository.findById(postId);

        Member member = new Member();
        model.addAttribute("post", post);
        model.addAttribute("member",member);
        return "post/postDeleteForm";
    }

    @PostMapping("/{postId}/delete")
    public String deletePost(@PathVariable long postId,
                             @Validated @ModelAttribute("member") PostDeleteMemberForm form, BindingResult bindingResult,
                             @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
                             Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("post",postRepository.findById(postId));
            return "post/postDeleteForm";
        }
        if (!loginMember.getPassword().equals(form.getPassword())){
            model.addAttribute("post",postRepository.findById(postId));
            bindingResult.reject("wrongPassword","비밀번호가 맞지 않습니다.");
            return "post/postDeleteForm";
        }
        postRepository.deletePost(postId);

        return "redirect:/board";
    }


}
