package hello.board.domain.post;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemoryPostRepositoryTest {
    PostRepository postRepository = new MemoryPostRepository();
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
        Post foundPost = postRepository.findById(post.getId());
        assertThat(foundPost).isEqualTo(savedPost);

    }

    @Test
    void findById() {
    }

    @Test
    void findByWriterId() {
    }

    @Test
    void findByTitle() {
    }

    @Test
    void findAll() {
    }

    @Test
    void clearStore() {
    }
}