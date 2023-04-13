package hello.board.web.board;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class FindByWriterIdForm {
    @NotBlank
    private String writerId;
}
