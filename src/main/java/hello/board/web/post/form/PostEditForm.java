package hello.board.web.post.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class PostEditForm {
    @NotEmpty
    private String title;
    @NotEmpty
    private String content;

}
