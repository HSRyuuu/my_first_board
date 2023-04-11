package hello.board.domain.post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    Post save(Post post);
    Post findById(Long id);
    List<Post> findByWriterId(String writerId);
    List<Post> findByTitle(String title);
    List<Post> findAll();
    void clearStore();

}
