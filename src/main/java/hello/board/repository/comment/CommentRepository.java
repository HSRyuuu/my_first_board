package hello.board.repository.comment;

import hello.board.domain.comment.Comment;

import java.util.List;

public interface CommentRepository {
    Comment save(Comment comment);
    Comment findById(Long id);
    List<Comment> findByPostId(Long postId);
    List<Comment> findAll();
    void clear();

}
