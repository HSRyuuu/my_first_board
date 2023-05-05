package hello.board.config;

import hello.board.repository.MemberRepository;
import hello.board.repository.PostRepository;
import hello.board.repository.jdbctemplate.JdbcMemberRepository;
import hello.board.repository.jdbctemplate.JdbcPostRepository;
import hello.board.repository.mybatis.MemberMapper;
import hello.board.repository.mybatis.MyBatisMemberRepository;
import hello.board.repository.mybatis.MyBatisPostRepository;
import hello.board.repository.mybatis.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class MyBatisConfig {
    private final MemberMapper memberMapper;
    private final PostMapper postMapper;

    @Bean
    public MemberRepository memberRepository(){
        return new MyBatisMemberRepository(memberMapper);
    }
    @Bean
    public PostRepository postRepository(){
        return new MyBatisPostRepository(postMapper);
    }
}
