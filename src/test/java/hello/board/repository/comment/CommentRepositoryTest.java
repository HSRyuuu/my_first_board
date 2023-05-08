package hello.board.repository.comment;

import hello.board.domain.comment.Comment;
import hello.board.repository.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@Transactional
@SpringBootTest
class CommentRepositoryTest {
    @Autowired
    CommentRepository commentRepository;
    @Test
    @DisplayName("save and findById")
    void save() {
        //given
        Comment comment = new Comment(12L,"test", "text comment");

        //when
        Comment savedComment = commentRepository.save(comment);
        //then
        Comment findComment = commentRepository.findById(savedComment.getId()).orElseThrow();
        log.info("findComment={}", findComment);
        assertThat(findComment).isEqualTo(savedComment);
    }
    @Test
    void findByPostId(){
        //given
        Comment comment1 = new Comment(12L,"test", "text comment");
        Comment comment2 = new Comment(12L,"test", "text comment");
        Comment comment3 = new Comment(13L,"test", "text comment");
        Comment savedComment1 = commentRepository.save(comment1);
        Comment savedComment2 = commentRepository.save(comment2);
        Comment savedComment3 = commentRepository.save(comment3);

        //when
        List<Comment> findList12 = commentRepository.findByPostId(12L);
        List<Comment> findList13 = commentRepository.findByPostId(13L);

        //then
        assertThat(findList12).containsExactly(savedComment1,savedComment2);
        assertThat(findList13).containsExactly(savedComment3);
    }

    @Test
    void updateComment(){
        //given
        Comment comment = new Comment(12L,"test", "text comment");
        Comment savedComment = commentRepository.save(comment);

        //when
        commentRepository.updateComment(savedComment.getId(), "updated text");

        //then
        assertThat(commentRepository.findById(savedComment.getId()).get().getText()).isEqualTo("updated text");
    }

    @Test
    void deleteComment(){
        //given
        Comment comment = new Comment(12L,"test", "text comment");
        Comment savedComment = commentRepository.save(comment);

        //when
        commentRepository.deleteComment(savedComment.getId());

        //then
        assertThat(commentRepository.findById(savedComment.getId())).isEqualTo(Optional.empty());
    }

}