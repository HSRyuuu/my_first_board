package hello.board.repository.mybatis;

import hello.board.domain.post.Post;
import hello.board.repository.PostRepository;
import hello.board.web.form.board.Searchform;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class MyBatisPostRepository implements PostRepository {

    private final PostMapper postMapper;

    @Override
    public Post save(Post post) {
        post.setCreate_date(LocalDateTime.now());
        post.setModified_date(LocalDateTime.now());
        postMapper.save(post);
        return post;
    }

    @Override
    public Optional<Post> findById(Long id) {
        return postMapper.findById(id);
    }

    @Override
    public List<Post> findAll(Searchform form) {
        return postMapper.findAll(form);
    }

    @Override
    public Post updatePost(Long id, Post updateParam) {
        updateParam.setModified_date(LocalDateTime.now());
        postMapper.updatePost(id,updateParam);
        return findById(id).get();
    }

    @Override
    public void addView(Long postId) {
        postMapper.addView(postId);
    }

    @Override
    public void deletePost(Long postId) {
        postMapper.deletePost(postId);
    }
}
