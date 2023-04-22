package hello.board.domain.post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.util.List;
@Slf4j
//@Repository
@RequiredArgsConstructor
public class JdbcPostRepository implements PostRepository{
    private final DataSource dataSource;
    private static Long  sequence = 0L;

    @Override
    public Post save(Post post) {
        return post;
    }

    @Override
    public Post findById(Long id) {
        return null;
    }

    @Override
    public List<Post> findByWriterId(String writerId) {
        return null;
    }

    @Override
    public List<Post> findByTitle(String title) {
        return null;
    }

    @Override
    public List<Post> findByContents(String content) {
        return null;
    }

    @Override
    public List<Post> findByTitleAndContent(String word) {
        return null;
    }

    @Override
    public List<Post> findAll() {
        return null;
    }

    @Override
    public Post updatePost(Long id, Post updateParam) {
        return null;
    }

    @Override
    public void addView(Long postId) {

    }
    @Override
    public void deletePost(Long id) {

    }
    @Override
    public void clearStore() {

    }
}
