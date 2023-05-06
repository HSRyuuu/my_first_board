package hello.board.config;

import hello.board.repository.jdbctemplate.JdbcMemberRepository;
import hello.board.repository.MemberRepository;
import hello.board.repository.jdbctemplate.JdbcPostRepository;
import hello.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Slf4j
//@Configuration
@RequiredArgsConstructor
public class JdbcTemplateConfig {
    private final DataSource dataSource;

    @Bean
    public MemberRepository memberRepository(){
        log.info("JdbcMemberRepository Config");
        return new JdbcMemberRepository(dataSource);
    }
    @Bean
    public PostRepository postRepository(){
        log.info("JdbcPostRepository Config");
        return new JdbcPostRepository(dataSource);
    }
}
