package hello.board.repository.memory;

import hello.board.domain.comment.Comment;
import hello.board.repository.CommentRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

@Repository
public class MemoryCommentRepository implements CommentRepository {
    private static final Map<Long, Comment> store = new HashMap<>();
    private static Long  sequence = 0L;
    @Override
    public Comment save(Comment comment) {
        comment.setCreateDate(LocalDateTime.now());
        comment.setId(++sequence);
        store.put(comment.getId(), comment);
        return comment;
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Comment> findByPostId(Long postId) {
        List<Comment> list = new ArrayList<>(store.values());
        List<Comment> findList = new ArrayList<>();

        for (Comment comment : list) {
            if(comment.getPostId().equals(postId)){
                list.add(comment);
            }
        }
        return list;
    }

    @Override
    public void updateComment(Long commentId, String text) {

    }

    @Override
    public void deleteComment(Long commentId) {

    }

    /**
     * test용
     */
    public void clear() {
        store.clear();
    }
}
