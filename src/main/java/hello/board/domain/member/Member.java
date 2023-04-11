package hello.board.domain.member;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;


@Getter@Setter
public class Member {

    private Long id;
    @NotEmpty
    private String loginId;
    @NotEmpty
    private String password;


}
