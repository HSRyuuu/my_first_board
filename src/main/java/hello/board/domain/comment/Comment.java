package hello.board.domain.comment;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"createDate","modifiedDate"})
@ToString
public class Comment {

    private Long id;
    private String writerId;
    private Long postId;
    @NotBlank
    private String text;
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;

    public Comment() {
    }

    public Comment(Long postId, String writerId, String text) {
        this.postId = postId;
        this.writerId = writerId;
        this.text = text;
        this.createDate = LocalDateTime.now();
        this.modifiedDate = LocalDateTime.now();
    }
}
