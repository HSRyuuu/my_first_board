package hello.board.web.post;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class PostDeleteMemberForm {
    @NotEmpty
    private String password;
}
