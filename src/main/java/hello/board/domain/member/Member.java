package hello.board.domain.member;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Primary;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;


@Getter@Setter
public class Member {

    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String nickname;
    @NotBlank
    private String loginId;
    @NotBlank
    private String password;
    @Email
    private String email;



    public Member() {
    }

    public Member(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }

    public Member(String name, String nickname, String loginId, String password, String email) {
        this.name = name;
        this.nickname = nickname;
        this.loginId = loginId;
        this.password = password;
        this.email = email;
    }
}
