package hello.board.repository.post;

import com.zaxxer.hikari.HikariDataSource;
import hello.board.domain.post.Post;
import hello.board.web.form.board.Searchform;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

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

    @Test
    void writerId_findAll(){
        Searchform form = new Searchform("find-by-writer-id", "test");
        List<Post> list = postRepository.findAll(form);
        System.out.println(list.get(0).getContent());
        System.out.println(list.get(1).getContent());
    }
    @Test
    void title_findAll(){
        Searchform form = new Searchform("find-by-title", "글쓰기");

        List<Post> list = postRepository.findAll(form);
        System.out.println(list.get(0).getContent());
        System.out.println(list.get(1).getContent());
    }
    @Test
    void content_findAll(){
        Searchform form = new Searchform("find-by-content", "기 입");

        List<Post> list = postRepository.findAll(form);
        System.out.println(list.get(0).getTitle());
        System.out.println(list.get(1).getTitle());
    }
    @Test
    void title_and_content_findAll(){
        Searchform form = new Searchform("find-by-title-and-content", "테스트");

        List<Post> list = postRepository.findAll(form);
        System.out.println(list.get(0).getContent());
        System.out.println(list.get(1).getContent());
        System.out.println(list.get(2).getContent());
    }

    @Test
    void addView(){
        postRepository.addView(4L);
    }

    @Test
    void testSave() {
    }

    @Test
    void findById() {
    }

    @Test
    void findAll() {
    }

    @Test
    void updatePost() {
    }

    @Test
    void testAddView() {
    }

    @Test
    void deletePost() {
    }
}