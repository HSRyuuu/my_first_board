package hello.board;

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
        String testContent = "<br><br>스프링 프레임워크(영어: Spring Framework)는 " +
                "자바 플랫폼을 위한 오픈 소스 애플리케이션 프레임워크로서 간단히 스프링(Spring)이라고도 한다. " +
                "동적인 웹 사이트를 개발하기 위한 여러 가지 서비스를 제공하고 있다. " +
                "대한민국 공공기관의 웹 서비스 개발 시 사용을 권장하고 있는 전자정부 표준프레임워크의 기반 기술로서 쓰이고 있다." +
                "<br><br>로드 존슨이 2002년에 출판한 자신의 저서인 Expert One-on-One J2EE Design and Development 에 " +
                "선보인 코드를 기반으로 시작하여 점점 발전하게 되었다.";
        Post post1 = new Post("test", "findByWriterId-test1", "content-findByWriterId1"+testContent);
        postRepository.save(post1);
        Post post2 = new Post("test", "findByWriterId-test2", "content-findByWriterId2"+testContent);
        postRepository.save(post2);
        Post post3 = new Post("test", "findByWriterId-test3", "content-findByWriterId3"+testContent);
        postRepository.save(post3);
        String writerIdstr = "writer";
        String titlestr = "test-title";
        String contentstr = "content";
        for(int i=0;i<10;i++){
            String writerId = "writer"+Integer.toString(i);
            String title = "test-title"+Integer.toString(i);
            String content = "content"+Integer.toString(i)+testContent;
            postRepository.save(new Post(writerId, title, content));
        }
        Member member = new Member("test","test!");
        member.setName("tester-Kim");
        memberRepository.save(member);
        for(int i=0;i<10;i++){
            String loginId = "writer"+Integer.toString(i);
            String password = "writer"+Integer.toString(i)+"!";
            memberRepository.save(new Member(loginId,password));
        }
    }
}