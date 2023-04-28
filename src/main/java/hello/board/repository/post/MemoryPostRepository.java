package hello.board.repository.post;

import hello.board.domain.post.Post;
import hello.board.web.form.board.Searchform;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

//@Repository
public class MemoryPostRepository implements PostRepository{
    private static final Map<Long, Post> store = new HashMap<>();
    private static long sequence = 0L; //키값을 생성해줌

    @Override
    public Post save(Post post) {
        post.setCreate_date(LocalDateTime.now());
        post.setModified_date(LocalDateTime.now());
        post.setId(++sequence);
        store.put(post.getId(),post);
        return post;
    }

    @Override
    public Optional<Post> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Post> findAll(Searchform form) {
        String searchCode = form.getSearchCode();
        String searchWord = form.getSearchWord();

        if(searchWord.isEmpty()){
            return new ArrayList<>(store.values());
        }
        List<Post> findList = new ArrayList<>();
        switch(searchCode){
            case "find-by-title" : {
                for (Post post : new ArrayList<>(store.values())) {
                    if(post.getTitle().toLowerCase().contains(searchWord.toLowerCase())){
                        findList.add(post);
                    }
                }
            }break;
            case "find-by-content" :{
                for (Post post : new ArrayList<>(store.values())) {
                    if(post.getContent().toLowerCase().contains(searchWord.toLowerCase())){
                        findList.add(post);
                    }
                }

            }break;
            case "find-by-writer-id" :{
                for (Post post : new ArrayList<>(store.values())) {
                    if (post.getWriterId().equals(searchWord)) {
                        findList.add(post);
                    }
                }
            }break;
            case "find-by-title-and-content" :{
                for(Post post : new ArrayList<>(store.values())){
                    if(post.getTitle().toLowerCase().contains(searchWord.toLowerCase()) || post.getContent().toLowerCase().contains(searchWord.toLowerCase())){
                        findList.add(post);
                    }
                }
            }break;

        }
        return findList;
    }

    @Override
    public Post updatePost(Long id, Post updateParam) {
        Post post = store.get(id);
        post.setTitle(updateParam.getTitle());
        post.setContent(updateParam.getContent());
        post.setModified_date(LocalDateTime.now());
        return post;
    }

    @Override
    public void addView(Long postId) {
        Post post = store.get(postId);
        post.setViews(post.getViews()+1);
    }

    @Override
    public void deletePost(Long id) {
        store.remove(id);
    }



    /**
     * 테스트용
     */
    public void clearStore(){
        store.clear();
    }
}
