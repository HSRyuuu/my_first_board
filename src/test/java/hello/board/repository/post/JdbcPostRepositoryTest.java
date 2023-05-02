package hello.board.repository.post;

import hello.board.domain.post.Post;
import hello.board.web.form.board.Searchform;
import hello.board.web.form.post.PostEditForm;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@Transactional
@SpringBootTest
class JdbcPostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Test
    void save() {
        //given
        Post post = new Post("testId", "title", "content");
        //when
        Post savedPost = postRepository.save(post);

        //then
        Post findPost = postRepository.findById(savedPost.getId()).get();
        assertThat(findPost).isEqualTo(savedPost);
    }


    @Test
    void findAll() {
        //given
        Post post1 = new Post("testId1", "title1", "content1");
        Post post2 = new Post("testId2", "title2", "content2");
        Post post3 = new Post("writer123", "hello", "hello world!!");
        //when
        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);

        //findAll
        test(null, null, post1,post2,post3);

        //findByTitle
        test("title",null,post1,post2,post3);
        test("title","1",post1);
        test("title","hel",post3);

        //findByContent
        test("content",null,post1,post2,post3);
        test("content","content",post1,post2);
        test("content","hello",post3);

        //findByWriterId
        test("writerId",null,post1,post2,post3);
        test("writerId","testId2",post2);
        test("writerId","writer123",post3);

        //findByTitleAndContent
        test("titleAndContent","",post1,post2,post3);
        test("titleAndContent","2",post2);
        test("titleAndContent","hello",post3);
    }


    @Test
    void updatePost() {
        //given
        Post post = new Post("testId", "title", "content");
        Post savedPost = postRepository.save(post);
        Long postId = savedPost.getId();

        //when
        PostEditForm form = new PostEditForm("hello","hello world!!");
        Post editPost = new Post(form.getTitle(),form.getContent());
        postRepository.updatePost(postId,editPost);

        //then
        Post findPost = postRepository.findById(postId).get();
        assertThat(findPost.getTitle()).isEqualTo(form.getTitle());
        assertThat(findPost.getContent()).isEqualTo(form.getContent());
    }

    @Test
    void addView() {
        //given
        Post post = new Post("testId", "title", "content");
        Post savedPost = postRepository.save(post);

        //when
        postRepository.addView(savedPost.getId());
        Post findPost = postRepository.findById(savedPost.getId()).get();

        //then
        assertThat(findPost.getViews()).isEqualTo(1);
    }

    @Test
    void deletePost() {
        //given
        Post post = new Post("testId", "title", "content");
        Post savedPost = postRepository.save(post);

        //when
        postRepository.deletePost(savedPost.getId());

        //then
        assertThat(postRepository.findAll(new Searchform())).isEmpty();
    }

    void test(String searchCode,String searchWord, Post ...posts){
        List<Post> findList = postRepository.findAll(new Searchform(searchCode,searchWord));
        assertThat(findList).containsExactly(posts);
    }
}