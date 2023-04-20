package hello.board.domain.comment;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter@Setter
public class Comment {

    private Long id;
    private String writerId;
    private String text;
    private String postId;
    private LocalDateTime create_date;
    private LocalDateTime modified_date;


    public Comment(String writerId, String text) {
        this.writerId = writerId;
        this.text = text;
    }
}
