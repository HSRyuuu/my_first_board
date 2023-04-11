package hello.board.domain.member;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;


@Getter@Setter
public class Member {

    private Long id;
    @NotEmpty
    private String loginId;
    @NotEmpty
    private String password;

    private List<Long> postList;

    public Member() {
    }

    public Member(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }
}
