package hello.board.repository.post;

import hello.board.domain.post.Post;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
public class JdbcPostRepository implements PostRepository {

    private final NamedParameterJdbcTemplate template;
    public JdbcPostRepository(DataSource dataSource){
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Post save(Post post) {
        post.setCreate_date(LocalDateTime.now());
        post.setModified_date(LocalDateTime.now());

        String sql = "INSERT INTO post( writerId, title, content, create_date, modified_date, views)" +
                     "values(:writerId, :title, :content, :create_date, :modified_date, :views) ";
        SqlParameterSource param = new BeanPropertySqlParameterSource(post);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(sql, param, keyHolder);

        long key = keyHolder.getKey().longValue();
        post.setId(key);
        return post;
    }

    @Override
    public Optional<Post> findById(Long id) {
        String sql = "SELECT * FROM POST where id=:id ";
        try {
            Map<String, Object> param = Map.of("id", id);
            //queryForObject : 하나의 객체를 가져올때
            Post post = template.queryForObject(sql, param, postRowMapper());
            return Optional.of(post);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Post> findByWriterId(String writerId) {
        return null;
    }

    @Override
    public List<Post> findByTitle(String title) {
        return null;
    }

    @Override
    public List<Post> findByContents(String content) {
        return null;
    }

    @Override
    public List<Post> findByTitleAndContent(String word) {
        return null;
    }

    @Override
    public List<Post> findAll() {
        SqlParameterSource param = new BeanPropertySqlParameterSource(Post.class);
        String sql = "SELECT id, writerId, title, content, create_date, modified_date, views FROM POST";
        return template.query(sql, param, postRowMapper());
    }

    @Override
    public Post updatePost(Long id, Post updateParam) {
        return null;
    }

    @Override
    public void addView(Long postId) {

    }

    @Override
    public void deletePost(Long id) {

    }


    public void clearStore() {

    }

    private RowMapper<Post> postRowMapper() {
        return BeanPropertyRowMapper.newInstance(Post.class); //camel 변환 지원
    }
}
