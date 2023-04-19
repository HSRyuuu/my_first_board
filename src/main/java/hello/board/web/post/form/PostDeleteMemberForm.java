package hello.board.web.post.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class PostDeleteMemberForm {
    @NotEmpty
    private String password;
}
