package hello.board.repository.post;

import hello.board.domain.post.Post;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

@Repository
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
    public Post findById(Long id) {
        return store.get(id);
    }

    @Override
    public List<Post> findByWriterId(String writerId) {
        List<Post> findByWriterIdList = new ArrayList<>();

        for (Post post : findAll()) {
            if (post.getWriterId().equals(writerId)) {
                findByWriterIdList.add(post);
            }
        }
            return findByWriterIdList;
    }

    @Override
    public List<Post> findByTitle(String title) {
            List<Post> findByTitleList = new ArrayList<>();
            for (Post post : findAll()) {
                if(post.getTitle().toLowerCase().contains(title.toLowerCase())){
                    findByTitleList.add(post);
                }
            }
            return findByTitleList;
        }

    @Override
    public List<Post> findByContents(String content) {
        List<Post> findByContentsList = new ArrayList<>();
        for (Post post : findAll()) {
            if(post.getContent().toLowerCase().contains(content.toLowerCase())){
                findByContentsList.add(post);
            }
        }
        return findByContentsList;
    }

    @Override
    public List<Post> findByTitleAndContent(String word) {
        List<Post> findByTitleAndContentList = new ArrayList<>();
        word = word.toLowerCase();
        for(Post post : findAll()){
            if(post.getTitle().toLowerCase().contains(word) || post.getContent().toLowerCase().contains(word)){
                findByTitleAndContentList.add(post);
            }
        }
        return findByTitleAndContentList;
    }

    @Override
    public List<Post> findAll() {
        return new ArrayList<>(store.values());
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
