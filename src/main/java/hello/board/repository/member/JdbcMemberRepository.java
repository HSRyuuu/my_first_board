package hello.board.repository.member;

import hello.board.domain.member.Member;
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
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class JdbcMemberRepository implements MemberRepository{

    private final NamedParameterJdbcTemplate template;
    public JdbcMemberRepository(DataSource dataSource){
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Member save(Member member) {
        String sql = "INSERT INTO member(name, nickname, loginId, password, email)" +
                     "values (:name, :nickname, :loginId, :password, :email)";
        SqlParameterSource param = new BeanPropertySqlParameterSource(member);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(sql, param, keyHolder);

        long key = keyHolder.getKey().longValue();
        member.setId(key);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        String sql = "SELECT * FROM member where id=:id";
        try{
            Map<String, Object> param = Map.of("id", id);
            Member member = template.queryForObject(sql, param, memberRowMapper());
            return Optional.of(member);
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public Optional<Member> findByLoginId(String loginId) {
        String sql = "SELECT * FROM member where loginId=:loginId";
        try{
            Map<String, Object> param = Map.of("loginId", loginId);
            Member member = template.queryForObject(sql, param, memberRowMapper());
            return Optional.of(member);
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public List<Member> findAll() {
        SqlParameterSource param = new BeanPropertySqlParameterSource(Member.class);
        String sql = "SELECT * FROM POST";
        return template.query(sql, param, memberRowMapper());
    }

    @Override
    public void updateMember(Long id, Member updateParam) {
        String sql = "UPDATE member SET name=:name, nickname=:nickname, email=:email" +
                                " where id=:id";
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("name", updateParam.getName())
                .addValue("nickname",updateParam.getNickname())
                .addValue("email", updateParam.getEmail())
                .addValue("id",id);
        template.update(sql, param);
    }

    @Override
    public void editPassword(Long id, String password) {
        String sql = "UPDATE member SET password=:password where id=:id";
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("password", password)
                .addValue("id",id);
        template.update(sql, param);
    }


    private RowMapper<Member> memberRowMapper() {
        return BeanPropertyRowMapper.newInstance(Member.class); //camel 변환 지원
    }

}
