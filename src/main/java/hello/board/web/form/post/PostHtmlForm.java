package hello.board.web.form.post;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class PostHtmlForm {
    private Long id; //시스템 id
    private String writerId; // 작성자 login id
    private String title; //제목
    private String content; //내용
    private LocalDateTime create_date; //생성 날짜
    private LocalDateTime modified_date; //수정 날짜
    private Long views; // 조회수
}
