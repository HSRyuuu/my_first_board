package hello.board.web.login;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginForm {
    @NotEmpty
    String loginId;

    @NotEmpty
    String password;
}
