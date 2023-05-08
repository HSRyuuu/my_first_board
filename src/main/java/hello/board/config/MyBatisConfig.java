package hello.board.config;

import hello.board.repository.CommentRepository;
import hello.board.repository.MemberRepository;
import hello.board.repository.PostRepository;
import hello.board.repository.mybatis.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class MyBatisConfig {
    private final MemberMapper memberMapper;
    private final PostMapper postMapper;
    private final CommentMapper commentMapper;

    @Bean
    public MemberRepository memberRepository(){
        log.info("MyBatisMemberRepository Config");
        return new MyBatisMemberRepository(memberMapper);
    }
    @Bean
    public PostRepository postRepository(){
        log.info("MyBatisPostRepository Config");
        return new MyBatisPostRepository(postMapper);
    }

    @Bean
    public CommentRepository commentRepository(){
        log.info("MyBatisCommentRepository Config");
        return new MyBatisCommentRepository(commentMapper);

    }
}
