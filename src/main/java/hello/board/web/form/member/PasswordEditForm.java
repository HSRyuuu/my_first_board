package hello.board.web.form.member;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PasswordEditForm {
    @NotBlank
    private String currentPassword;
    @NotBlank
    private String editPassword;
    @NotBlank
    private String editPasswordCheck;

}
