package hello.board.web.form.board;

import lombok.Data;

@Data
public class Searchform {
    private String searchCode;
    private String searchWord;

    public Searchform() {
    }

    public Searchform(String searchCode, String searchWord) {
        this.searchCode = searchCode;
        this.searchWord = searchWord;
    }
}
