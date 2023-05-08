package hello.board.repository;

import hello.board.domain.comment.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    Comment save(Comment comment);
    Optional<Comment> findById(Long id);
    List<Comment> findByPostId(Long postId);
    void updateComment(Long commentId, String text);
    void deleteComment(Long commentId);
}
