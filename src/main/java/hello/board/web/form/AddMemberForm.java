package hello.board.web.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddMemberForm {
    @NotBlank
    private String loginId;
    @NotBlank
    private String password;
    @NotBlank
    private String name;
}
