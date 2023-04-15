package hello.board.domain.post;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryPostRepository implements PostRepository{
    private static final Map<Long,Post> store = new HashMap<>();
    private static long sequence = 0L; //키값을 생성해줌
    @Override
    public Post save(Post post) {
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
                if(post.getTitle().contains(title)){
                    findByTitleList.add(post);
                }
            }
            return findByTitleList;
        }
    @Override
    public List<Post> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void updatePost(Long id, Post updateParam) {
        Post post = findById(id);
        post.setTitle(updateParam.getTitle());
        post.setContent(updateParam.getContent());
    }

    /**
     * 테스트용
     */
    public void clearStore(){
        store.clear();
    }
}
