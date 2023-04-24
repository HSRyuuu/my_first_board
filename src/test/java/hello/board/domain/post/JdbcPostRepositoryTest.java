package hello.board.domain.post;

import com.zaxxer.hikari.HikariDataSource;
import hello.board.repository.post.JdbcPostRepository;
import hello.board.repository.post.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static hello.board.repository.db.ConnectionConst.*;

class JdbcPostRepositoryTest {

    PostRepository postRepository;
    @BeforeEach
    void beforeEach(){
        //커넥션 풀링
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);

        postRepository = new JdbcPostRepository(dataSource);
    }
    @Test
    void save() {
        Post post1 = new Post("test", "findByWriterId-test1", "content-findByWriterId1");
        Post post2 = new Post("test", "findByWriterId-test2", "content-findByWriterId2");

        postRepository.save(post1);
        postRepository.save(post2);

    }


}