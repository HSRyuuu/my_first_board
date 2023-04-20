package hello.board.web.post.form;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PostHtmlForm {
    private Long id; //시스템 id
    private String writerId; // 작성자 login id
    private String title; //제목
    private String content; //내용
    private LocalDate date; //생성 날짜
    private Long views; // 조회수
}
