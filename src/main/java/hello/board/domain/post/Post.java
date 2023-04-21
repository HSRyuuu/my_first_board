package hello.board.domain.post;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter@Setter
public class Post {
    private Long id; //시스템 id
    private String writerId; // 작성자 login id
    private String title; //제목
    private String content; //내용
    private LocalDateTime create_date; //생성 날짜
    private LocalDateTime modified_date; //수정 날짜
    private Long views; // 조회수

    public Post(String title,String content){
        this.title = title;
        this.content = content;
        this.views = 0L;

    }
    public Post(String writerId, String title, String content) {
        this.writerId = writerId;
        this.title = title;
        this.content = content;
        this.views = 0L;
    }
}
