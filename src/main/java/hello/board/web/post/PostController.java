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
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/board/post")
@RequiredArgsConstructor
public class PostController {
    private final PostRepository postRepository;

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

        post.setContent(stringToHtml(post.getContent()));
        model.addAttribute("post", post);
        return "post/post";
    }
    @GetMapping("/{postId}/edit")
    public String editPostForm(@PathVariable Long postId, Model model){
        Post post = postRepository.findById(postId);

        // view에 넘겨줄 form
        PostEditForm form = new PostEditForm();
        form.setTitle(post.getTitle());
        form.setContent(htmlToString(post.getContent()));//글을 html에서 줄바꿈이 적용되도록 변경
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
    public String deleteForm(@PathVariable long postId){
        return "post/postDeleteForm";
    }

    //String <-> Html type 변환 메소드
    private static String htmlToString(String content){
        HtmlFormatter htmlFormatter = new HtmlFormatter();
        String stringContent = htmlFormatter.getStringContent(content);
        return stringContent;
    }
    private static String stringToHtml(String content){
        HtmlFormatter htmlFormatter = new HtmlFormatter();
        String htmlContent = htmlFormatter.getHtmlContent(content);
        return htmlContent;
    }

}
