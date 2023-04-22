package hello.board.web.form.board;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class WritingForm {
    @NotBlank
    private String title; //제목
    @NotBlank
    private String content; //내용
}
