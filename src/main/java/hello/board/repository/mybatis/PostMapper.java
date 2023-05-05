package hello.board.repository.mybatis;

import hello.board.domain.post.Post;
import hello.board.web.form.board.Searchform;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PostMapper {

    void save(Post post);
    Optional<Post> findById(Long id);
    List<Post> findAll(Searchform form);
    void updatePost(@Param("id") Long id, @Param("updateParam") Post updateParam);
    void addView(Long postId);
    void deletePost(Long postId);
}
