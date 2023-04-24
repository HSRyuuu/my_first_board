package hello.board.domain.post;

import lombok.Data;

@Data
public class PostSearchCode {

    private String code;
    private String displayName;

    public PostSearchCode(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }
}
