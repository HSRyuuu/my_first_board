package hello.board.web.member;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class EditMemberForm {

    private String loginId;
    @NotEmpty
    private String password;
    @NotEmpty
    private String name;
}
