package hello.board.repository.jdbctemplate;

import hello.board.domain.post.Post;
import hello.board.repository.PostRepository;
import hello.board.web.form.board.Searchform;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
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
    public List<Post> findAll(Searchform form) {
        String sql = "SELECT id, writerId, title, content, create_date, modified_date, views FROM POST";

        if(!StringUtils.hasText(form.getSearchWord())){
            return template.query(sql, postRowMapper());
        }

        SqlParameterSource param = new BeanPropertySqlParameterSource(form);
        String searchCode = form.getSearchCode();
        switch(searchCode){
            case "title" : {
                sql+= " WHERE LOWER(title) LIKE LOWER(concat('%',:searchWord,'%'))";
            }break;

            case "content" :{
                sql+= " WHERE LOWER(content) LIKE LOWER(concat('%',:searchWord,'%'))";
            }break;

            case "writerId" :{
                sql+= " WHERE LOWER(writerId)=LOWER(:searchWord)";
            }break;

            case "titleAndContent" :{
                sql+= " WHERE LOWER(title) LIKE LOWER(concat('%',:searchWord,'%')) or LOWER(content) LIKE LOWER(concat('%',:searchWord,'%'))";
            }break;

        }

        return template.query(sql, param, postRowMapper());
    }

    @Override
    public Post updatePost(Long id, Post updateParam) {
        String sql = "UPDATE post SET title=:title, content=:content, MODIFIED_DATE=:modified_date" +
                    " where id=:id";
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("title", updateParam.getTitle())
                .addValue("content", updateParam.getContent())
                .addValue("modified_date", LocalDateTime.now())
                .addValue("id", id);
        template.update(sql, param);
        return findById(id).get();
    }

    @Override
    public void addView(Long postId) {
        String sql = "UPDATE post SET views=views+1 where id=:id";
        template.update(sql, Map.of("id",postId));
    }

    @Override
    public void deletePost(Long postId) {
        String sql = "DELETE FROM post WHERE id=:id";
        template.update(sql, Map.of("id",postId));
    }


    private RowMapper<Post> postRowMapper() {
        return BeanPropertyRowMapper.newInstance(Post.class); //camel 변환 지원
    }

    private RowMapper<Post> postRowMapper2(){
        return ((rs, rowNum) -> {
            Post post = new Post();
            post.setId(rs.getLong("id"));
            post.setWriterId(rs.getString("writerId"));
            post.setTitle(rs.getString("title"));
            post.setContent(rs.getString("content"));
            post.setCreate_date(rs.getTimestamp("create_date").toLocalDateTime());
            post.setModified_date(rs.getTimestamp("modified_date").toLocalDateTime());
            post.setViews(rs.getLong("views"));
            return post;
        });
    }
}
