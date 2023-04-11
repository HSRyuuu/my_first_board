package hello.board.domain.post;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter@Setter
public class Post {
    private Long id; //시스템 id
    private String writerId; // 작성자 login id
    private String title; //제목
    private String content; //내용
    private String date; //생성 날짜
    private Long views; // 조회수

    public Post(String writerId, String title, String content) {
        this.writerId = writerId;
        this.title = title;
        this.content = content;
        this.date = String.valueOf(LocalDate.now());
        this.views = 0L;
    }
}
