package hello.board.domain.post;

import hello.board.repository.post.MemoryPostRepository;
import hello.board.repository.post.PostRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class MemoryPostRepositoryTest {
    MemoryPostRepository postRepository = new MemoryPostRepository();

    @AfterEach
    void afterEach(){
        postRepository.clearStore();
    }
    @Test
    void save() {
        //given
        Post post = new Post("writer1", "title1", "content1");
        //when
        Post savedPost = postRepository.save(post);
        //then
        Post foundPost = postRepository.findById(post.getId()).get();
        assertThat(foundPost).isEqualTo(savedPost);

    }

    @Test
    void findByWriterId() {
        //given
        Post post1 = new Post("writer1", "title1", "content1");
        Post post2 = new Post("writer1", "title2", "content2");
        Post post3 = new Post("writer2", "title2", "content3");

        //when
        Post savedPost1 = postRepository.save(post1);
        Post savedPost2 = postRepository.save(post2);
        Post savedPost3 = postRepository.save(post3);
        //then
        List<Post> foundList = postRepository.findByWriterId("writer1");
        assertThat(foundList).containsExactly(post1,post2);

    }

    @Test
    void findByTitle() {
        //given
        Post post1 = new Post("writer1", "title1", "content1");
        Post post2 = new Post("writer2", "title2", "content2");
        Post post3 = new Post("writer3", "hello", "content2");

        //when
        Post savedPost1 = postRepository.save(post1);
        Post savedPost2 = postRepository.save(post2);
        Post savedPost3 = postRepository.save(post3);

        //then
        List<Post> foundList = postRepository.findByTitle("tle");
        assertThat(foundList.size()).isEqualTo(2);
        assertThat(foundList).containsExactly(post1,post2);
    }

    @Test
    void findByContent(){
        //given
        Post post1 = new Post("writer1", "title1", "content1");
        Post post2 = new Post("writer2", "title2", "content2");
        Post post3 = new Post("writer3", "hello", "content2");

        //when
        Post savedPost1 = postRepository.save(post1);
        Post savedPost2 = postRepository.save(post2);
        Post savedPost3 = postRepository.save(post3);

        //then
        List<Post> foundList = postRepository.findByContents("content");
        System.out.println(foundList.size());
    }

    @Test
    void printTest(){

        Post post1 = new Post("writer1", "title1", "content1");
        Post post2 = new Post("writer2", "title2", "content2");
        Post post3 = new Post("writer3", "hello", "content2");
        Post savedPost1 = postRepository.save(post1);
        Post savedPost2 = postRepository.save(post2);
        Post savedPost3 = postRepository.save(post3);

        System.out.println(savedPost1.getContent());
        if(savedPost1.getContent().contains("asdf")){
            System.out.println("true");
        }else System.out.println("false");
    }

    @Test
    void findAll() {
        //given
        Post post1 = new Post("writer1", "title1", "content1");
        Post post2 = new Post("writer2", "title2", "content2");
        Post post3 = new Post("writer3", "hello", "content2");

        //when
        Post savedPost1 = postRepository.save(post1);
        Post savedPost2 = postRepository.save(post2);
        Post savedPost3 = postRepository.save(post3);

        //then
        List<Post> foundList = postRepository.findAll();
        assertThat(foundList.size()).isEqualTo(3);
        assertThat(foundList).containsExactly(post1,post2,post3);
    }

}