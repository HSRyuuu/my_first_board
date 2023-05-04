package hello.board.config;

import hello.board.repository.jdbctemplate.JdbcMemberRepository;
import hello.board.repository.MemberRepository;
import hello.board.repository.jdbctemplate.JdbcPostRepository;
import hello.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class JdbcTemplateConfig {
    private final DataSource dataSource;

    @Bean
    public MemberRepository memberRepository(){
        return new JdbcMemberRepository(dataSource);
    }
    @Bean
    public PostRepository postRepository(){
        return new JdbcPostRepository(dataSource);
    }
}
