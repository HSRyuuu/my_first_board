/*
package hello.board.domain.post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
@Slf4j
//@Repository
@RequiredArgsConstructor
public class JdbcPostRepository implements PostRepository{
    private final DataSource dataSource;
    private static Long  sequence = 0L;

    @Override
    public Post save(Post post) {
        post.setCreate_date(LocalDateTime.now());
        post.setModified_date(LocalDateTime.now());
        post.setId(++sequence);
        java.sql.Date sqlDate = java.sql.Date.valueOf(post.getCreate_date());
        String sql = "insert into post(writerId, title, content, create_date, modified_date, views) values (?, ?, ?, ?, ?, ?)";

        Connection con = null; //커넥션
        PreparedStatement pstmt = null; // db에 쿼리를 날리는 도구

        try {
            con = getConnection(); // 커넥션 획득 메소드
            pstmt = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            //파라미터 바인딩
            //pstmt.setLong(1, post.getId());
            pstmt.setString(1,post.getWriterId());
            pstmt.setString(2,post.getTitle());
            pstmt.setString(3,post.getContent());
            pstmt.setDate(4,sqlDate);
            pstmt.setLong(5,post.getViews());

            pstmt.executeUpdate();

            return post;
        } catch (SQLException e) {
            log.error("db error", e);

        } finally{
            close(con,pstmt,null);
        }
        return post;
    }

    @Override
    public Post findById(Long id) {
        return null;
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
    public void deletePost(Long id) {

    }

    */
/**
     * pstmt에서 exception이 발생하면 con.close() 가 호출이 안될수도 있다.
     * 따라서 exception이 발생해도 마저 close 하도록 설정한다.
     *//*

    private void close(Connection con, Statement stmt, ResultSet rs){
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(stmt);
        JdbcUtils.closeConnection(con);
    }
    private Connection getConnection() throws SQLException {
        Connection con = dataSource.getConnection();
        log.info("get connection={}, class={}", con, con.getClass());
        return con;
    }
    @Override
    public void clearStore() {

    }
}
*/
