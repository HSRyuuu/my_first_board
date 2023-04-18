package hello.board.web.board;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SearchForm {
    @NotBlank
    private String searchWord;

    private String searchCode;
}
