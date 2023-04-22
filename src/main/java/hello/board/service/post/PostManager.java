package hello.board.service.post;

import hello.board.domain.member.Member;
import hello.board.domain.post.Post;
import hello.board.domain.post.PostRepository;
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
public class PostManager {
    private final PostRepository postRepository;

    public List<Post> getSearchedList(String searchCode, String searchWord){
        List<Post> searchList;
        switch(searchCode){
            case "find-by-title" : {
                searchList = postRepository.findByTitle(searchWord);
            }break;
            case "find-by-content" :{
                searchList = postRepository.findByContents(searchWord);
            }break;
            case "find-by-writer-id" :{
                searchList = postRepository.findByWriterId(searchWord);
            }break;
            case "find-by" :{
                searchList = postRepository.findByTitleAndContent(searchWord);
            }break;
            default: searchList = new ArrayList<>();
        }
        return searchList;
    }

    public List<PostSearchCode> getSearchCodes(){
        List<PostSearchCode> searchCodes = new ArrayList<>();
        searchCodes.add(new PostSearchCode("find-by-title", "제목"));
        searchCodes.add(new PostSearchCode("find-by-content", "내용"));
        searchCodes.add(new PostSearchCode("find-by-writer-id", "작성자"));
        searchCodes.add(new PostSearchCode("find-by","제목+내용"));
        return searchCodes;
    }

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
        Post post = postRepository.findById(postId);
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
        form.setCreate_date(post.getCreate_date());
        form.setModified_date(post.getModified_date());
        form.setViews(post.getViews());

        String content = post.getContent();
        String htmlContent = content.replaceAll("\n", "<br>");
        form.setContent(htmlContent);

        return form;
    }
    public PostEditForm getEditForm(Post post){
        PostEditForm form = new PostEditForm();
        form.setTitle(post.getTitle());
        form.setContent(getStringContent(post));
        return form;
    }

    /**
     * Html format을 받아서 String으로 줄바꿈이 적용되도록 바꿔준다.
     * Html format에 포함된 "<br>"을 "\n"로 변경한다.
     */
    public String getStringContent(Post post) {
        String content = post.getContent();
        String stringContent = content.replaceAll("<br>", "\n");
        return stringContent;
    }

    public Post updatePost(Long postId, PostEditForm form){
        Post post = new Post(form.getTitle(),form.getContent());
        Post updatedPost = postRepository.updatePost(postId,post);
        return updatedPost;
    }

}
