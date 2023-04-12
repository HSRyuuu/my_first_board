package hello.board.web;

import hello.board.domain.member.Member;
import hello.board.domain.member.MemberRepository;
import hello.board.domain.post.Post;
import hello.board.domain.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    @PostConstruct
    public void init(){
        Post post1 = new Post("test", "findByWriterId-test1", "content-findByWriterId1");
        postRepository.save(post1);
        Post post2 = new Post("test", "findByWriterId-test2", "content-findByWriterId2");
        postRepository.save(post2);
        Post post3 = new Post("test", "findByWriterId-test3", "content-findByWriterId3");
        postRepository.save(post3);
        String writerIdstr = "writer";
        String titlestr = "test-title";
        String contentstr = "content";
        for(int i=0;i<10;i++){
            String writerId = "writer"+Integer.toString(i);
            String title = "test-title"+Integer.toString(i);
            String content = "content"+Integer.toString(i);
            postRepository.save(new Post(writerId, title, content));
        }
        Member member = new Member("test","test!");
        memberRepository.save(member);
        for(int i=0;i<10;i++){
            String loginId = "writer"+Integer.toString(i);
            String password = "writer"+Integer.toString(i)+"!";
            memberRepository.save(new Member(loginId,password));
        }
    }
}
