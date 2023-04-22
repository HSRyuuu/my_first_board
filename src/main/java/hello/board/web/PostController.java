package hello.board.web;

import hello.board.domain.member.Member;
import hello.board.domain.post.Post;
import hello.board.domain.post.PostRepository;
import hello.board.service.post.PostManager;
import hello.board.web.form.post.PostDeleteMemberForm;
import hello.board.web.form.post.PostEditForm;
import hello.board.web.form.post.PostHtmlForm;
import hello.board.web.session.SessionConst;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/board/post")
@RequiredArgsConstructor
public class PostController {
    private final PostRepository postRepository;
    private final PostManager postManager;

    @GetMapping("/{postId}")
    public String showPost(@PathVariable long postId, Model model,
                           @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember){
        Post post = postRepository.findById(postId);

        if(postManager.isAccessable(postId, loginMember)){
            model.addAttribute("access",true);
        }else{
            postRepository.addView(postId); // 조회수
        }

        //화면에 띄우기 위해 html형식의 content로 변환
        PostHtmlForm form = postManager.getHtmlPostForm(post);
        model.addAttribute("post", form);
        return "post/post";
    }
    @GetMapping("/{postId}/edit")
    public String editPostForm(@PathVariable Long postId, Model model){
        Post post = postRepository.findById(postId);

        model.addAttribute("post",post);
        model.addAttribute("form",postManager.getEditForm(post));
        return "post/postEditForm";
    }

    @PostMapping("/{postId}/edit")
    public String editPost(@PathVariable long postId,
                           @Validated @ModelAttribute("form")PostEditForm form, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("post",postRepository.findById(postId));
            model.addAttribute("form",form);
            return "post/postEditForm";
        }

        postManager.updatePost(postId, form);
        return "redirect:/board/post/{postId}";

    }

    @GetMapping("/{postId}/delete")
    public String deleteForm(@PathVariable long postId, Model model){
        Post post = postRepository.findById(postId);

        model.addAttribute("post", post);
        model.addAttribute("member",new Member());
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
