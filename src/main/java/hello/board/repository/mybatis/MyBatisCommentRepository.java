package hello.board.repository.mybatis;

import hello.board.domain.comment.Comment;
import hello.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MyBatisCommentRepository implements CommentRepository {

    private final CommentMapper commentMapper;

    @Override
    public Comment save(Comment comment) {
        commentMapper.save(comment);
        return comment;
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return commentMapper.findById(id);
    }

    @Override
    public List<Comment> findByPostId(Long postId) {
        return commentMapper.findByPostId(postId);
    }

    @Override
    public void updateComment(Long commentId, String text) {
        commentMapper.updateComment(commentId,text);
    }

    @Override
    public void deleteComment(Long commentId) {
        commentMapper.deleteComment(commentId);
    }

}
