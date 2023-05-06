package hello.board.config.memory;

import hello.board.repository.MemberRepository;
import hello.board.repository.memory.MemoryMemberRepository;
import hello.board.repository.memory.MemoryPostRepository;
import hello.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


//@Configuration
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

}
