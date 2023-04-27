package hello.board.repository.post;

import com.zaxxer.hikari.HikariDataSource;
import hello.board.domain.post.Post;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class JdbcPostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Test
    void save(){
        //given
        Post post1 = new Post("test", "findByWriterId-test1", "content-findByWriterId1");

        //when
        postRepository.save(post1);

        //then
        Post findPost = postRepository.findById(post1.getId()).get();
        Assertions.assertThat(findPost).isEqualTo(post1);



    }
}