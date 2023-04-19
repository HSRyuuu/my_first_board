package hello.board.domain.comment;

import java.util.List;

public interface CommentRepository {
    Comment save(Comment comment);
    Comment findById(Long id);
    List<Comment> findByPostId(Long postId);
    List<Comment> findAll();
    void clear();

}
