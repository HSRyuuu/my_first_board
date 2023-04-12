package hello.board.web.board;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class FindByWriterIdForm {
    @NotBlank
    private String writerId;
}
