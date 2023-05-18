package hello.board.service.post;

import hello.board.domain.member.Member;
import hello.board.domain.post.Post;
import hello.board.domain.post.PostSearchCode;
import hello.board.repository.PostRepository;
import hello.board.web.form.board.Searchform;
import hello.board.web.form.post.PostEditForm;
import hello.board.web.form.post.PostHtmlForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public Post save(Post post) {
        return postRepository.save(post);
    }
    public Post findById(Long id) {
        return postRepository.findById(id).get();
    }
    public List<Post> findPosts(Searchform form) {
        return sortByLatest(postRepository.findAll(form));
    }
    public void addView(Long postId) {
        postRepository.addView(postId);
    }
    public void deletePost(Long postId) {
        postRepository.deletePost(postId);
    }


    public List<PostSearchCode> getSearchCodes(){
        List<PostSearchCode> searchCodes = new ArrayList<>();
        searchCodes.add(new PostSearchCode("title", "제목"));
        searchCodes.add(new PostSearchCode("content", "내용"));
        searchCodes.add(new PostSearchCode("writerId", "작성자"));
        searchCodes.add(new PostSearchCode("titleAndContent","제목+내용"));
        return searchCodes;
    }

    /**
     * 최신순 정렬
     */
    public List<Post> sortByLatest(List<Post> list){
        Collections.sort(list, new Comparator<Post>() {
            @Override
            public int compare(Post o1, Post o2) {
                return (int)(o2.getId()-o1.getId());
            }
        });
        return list;
    }

    public boolean isAccessable(Long postId, Member loginMember){
        Post post = postRepository.findById(postId).get();
        if(post.getWriterId().equals(loginMember.getLoginId())){
            return true;
        }
        return false;
    }

    /**
     * String을 받아서 html에서 줄바꿈이 적용되도록 바꿔준다.
     * String에 포함된 "\n" 을 "<br>"로 변경한다.
     */
    public PostHtmlForm getHtmlPostForm(Post post) {
        PostHtmlForm form = new PostHtmlForm();
        form.setId(post.getId());
        form.setWriterId(post.getWriterId());
        form.setTitle(post.getTitle());
        form.setCreateDate(post.getCreateDate());
        form.setModifiedDate(post.getModifiedDate());
        form.setViews(post.getViews());

        String htmlContent = getHtmlContent(post.getContent());
        form.setContent(htmlContent);

        return form;
    }

    public PostEditForm getEditForm(Post post){
        PostEditForm form = new PostEditForm();
        form.setTitle(post.getTitle());
        form.setContent(getStringContent(post.getContent()));
        return form;
    }
    public Post updatePost(Long postId, PostEditForm form){
        Post post = new Post(form.getTitle(),form.getContent());
        Post updatedPost = postRepository.updatePost(postId,post);
        return updatedPost;
    }

    public String getStringContent(String content) {
        String stringContent = content.replaceAll("<br>", "\n");
        return stringContent;
    }
    public String getHtmlContent(String content) {
        String htmlContent = content.replaceAll("\n", "<br>");
        return htmlContent;
    }

}
