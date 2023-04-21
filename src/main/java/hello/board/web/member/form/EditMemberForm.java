package hello.board.web.member.form;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class EditMemberForm {
    @NotBlank
    private String loginId;
    @NotBlank
    private String name;
    @NotBlank
    private String nickname;
    @Email@NotBlank
    private String email;



    public EditMemberForm() {
    }

    public EditMemberForm(String loginId, String name, String nickname, String email){
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.loginId = loginId;
    }
}
