package hello.board.config.memory;

import hello.board.repository.member.MemberRepository;
import hello.board.repository.member.MemoryMemberRepository;
import hello.board.repository.post.MemoryPostRepository;
import hello.board.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


//Configuration
@RequiredArgsConstructor
public class MemoryConfig {

    @Bean
    public MemberRepository memberRepository(){
        return new MemoryMemberRepository();
    }
    @Bean
    public PostRepository postRepository(){
        return new MemoryPostRepository();
    }
    @Bean
    public TestDataInit testDataInit(){
        return new TestDataInit(memberRepository(),postRepository());
    }
}
