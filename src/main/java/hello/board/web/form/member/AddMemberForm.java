package hello.board.web.form.member;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AddMemberForm {

    @NotBlank
    private String name;
    @NotBlank
    private String nickname;
    @NotBlank
    private String loginId;
    @NotBlank
    private String password;
    @NotBlank
    private String passwordCheck;
    @Email @NotBlank
    private String email;
}
