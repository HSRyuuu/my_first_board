package hello.board.domain.comment;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MemoryCommentRepository implements CommentRepository {
    private static final Map<Long, Comment> store = new HashMap<>();
    private static Long  sequence = 0L;
    @Override
    public Comment save(Comment comment) {
        comment.setDate(LocalDate.now());
        comment.setId(++sequence);
        store.put(comment.getId(), comment);
        return comment;
    }

    @Override
    public Comment findById(Long id) {
        return store.get(id);
    }

    @Override
    public List<Comment> findByPostId(Long postId) {
        List<Comment> list = new ArrayList<>();
        for (Comment comment : findAll()) {
            if(comment.getPostId().equals(postId)){
                list.add(comment);
            }
        }
        return list;
    }

    @Override
    public List<Comment> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void clear() {
        store.clear();
    }
}
