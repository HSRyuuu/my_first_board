package hello.board.service.post;

import hello.board.domain.comment.Comment;
import hello.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public Comment save(Comment comment) {
       commentRepository.save(comment);
        return comment;
    }
    public Optional<Comment> findById(Long id) {
        return commentRepository.findById(id);
    }

    public List<Comment> findByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }
    public void updateComment(Long commentId, String text) {
        commentRepository.updateComment(commentId,text);
    }
    public void deleteComment(Long commentId) {
        commentRepository.deleteComment(commentId);
    }

}
