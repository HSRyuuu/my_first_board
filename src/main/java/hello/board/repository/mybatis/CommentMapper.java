package hello.board.repository.mybatis;

import hello.board.domain.comment.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Mapper
public interface CommentMapper {

    void save(Comment comment);
    Optional<Comment> findById(Long id);
    List<Comment> findByPostId(Long postId);
    void updateComment(@Param("commentId") Long commentId, @Param("text")String text );
    void deleteComment(Long commentId);
}
