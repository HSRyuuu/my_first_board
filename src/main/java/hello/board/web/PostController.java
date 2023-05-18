package hello.board.web;

import hello.board.domain.comment.Comment;
import hello.board.domain.member.Member;
import hello.board.domain.post.Post;
import hello.board.service.member.MemberService;
import hello.board.service.post.CommentService;
import hello.board.service.post.PostService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/board/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final MemberService memberService;
    private final CommentService commentService;

    @GetMapping("/{postId}")
    public String showPost(@PathVariable long postId, Model model,
                           @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember){
        Post post = postService.findById(postId);

        if(postService.isAccessable(postId, loginMember)){
            model.addAttribute("access",true);
        }else{
            postService.addView(postId); // 조회수
        }

        List<Comment> commentList = commentService.findByPostId(postId);
        model.addAttribute("comments", commentList);
        //화면에 띄우기 위해 html형식의 content로 변환
        PostHtmlForm form = postService.getHtmlPostForm(post);
        model.addAttribute("post", form);
        model.addAttribute("commentForm", new Comment());
        return "post/post";
    }

    @PostMapping("/{postId}")
    public String writeComment(@PathVariable long postId,
                               @Validated @ModelAttribute("commentForm")Comment comment,BindingResult bindingResult,
                               @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember){
        if(bindingResult.hasErrors()) return "redirect:/board/post/{postId}";
        commentService.save(comment, loginMember.getLoginId(),postId );
        return "redirect:/board/post/{postId}";
    }

    @GetMapping("/{postId}/edit")
    public String editPostForm(@PathVariable Long postId, Model model){
        Post post = postService.findById(postId);

        model.addAttribute("post",post);
        model.addAttribute("form", postService.getEditForm(post));
        return "post/postEditForm";
    }

    @PostMapping("/{postId}/edit")
    public String editPost(@PathVariable long postId,
                           @Validated @ModelAttribute("form")PostEditForm form, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("post", postService.findById(postId));
            model.addAttribute("form",form);
            return "post/postEditForm";
        }

        postService.updatePost(postId, form);
        return "redirect:/board/post/{postId}";

    }

    @GetMapping("/{postId}/delete")
    public String deleteForm(@PathVariable long postId, Model model){
        Post post = postService.findById(postId);

        model.addAttribute("post", post);
        model.addAttribute("member",new Member());
        return "post/postDeleteForm";
    }

    @PostMapping("/{postId}/delete")
    public String deletePost(@PathVariable long postId,
                             @Validated @ModelAttribute("member") PostDeleteMemberForm form, BindingResult bindingResult,
                             @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
                             Model model){
        loginMember = memberService.findById(loginMember.getId()).get();
        if(bindingResult.hasErrors()){
            model.addAttribute("post", postService.findById(postId));
            return "post/postDeleteForm";
        }
        if (!loginMember.getPassword().equals(form.getPassword())){
            model.addAttribute("post", postService.findById(postId));
            bindingResult.reject("wrongPassword","비밀번호가 맞지 않습니다.");
            return "post/postDeleteForm";
        }
        postService.deletePost(postId);

        return "redirect:/board";
    }


}
