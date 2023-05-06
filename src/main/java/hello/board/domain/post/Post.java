package hello.board.domain.post;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;


@Getter
@Setter
@ToString
@EqualsAndHashCode(exclude = {"createDate","modifiedDate"})
public class Post {
    private Long id; //시스템 id
    private String writerId; // 작성자 login id
    private String title; //제목
    private String content; //내용
    private LocalDateTime createDate; //생성 날짜
    private LocalDateTime modifiedDate; //수정 날짜
    private Long views; // 조회수


    public Post() {
    }

    public Post(String title, String content){
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
