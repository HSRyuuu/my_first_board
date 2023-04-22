package hello.board.web.form.login;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginForm {
    @NotEmpty
    String loginId;

    @NotEmpty
    String password;
}
