package hello.board.repository.mybatis;

import hello.board.domain.post.Post;
import hello.board.repository.PostRepository;
import hello.board.web.form.board.Searchform;

import java.util.List;
import java.util.Optional;

public class MyBatisPostRepository implements PostRepository {
    @Override
    public Post save(Post post) {
        return null;
    }

    @Override
    public Optional<Post> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Post> findAll(Searchform form) {
        return null;
    }

    @Override
    public Post updatePost(Long id, Post updateParam) {
        return null;
    }

    @Override
    public void addView(Long postId) {

    }

    @Override
    public void deletePost(Long postId) {

    }
}
