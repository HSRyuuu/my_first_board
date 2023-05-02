package hello.board.web.form.post;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class PostEditForm {
    @NotEmpty
    private String title;
    @NotEmpty
    private String content;

    public PostEditForm() {
    }

    public PostEditForm(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
