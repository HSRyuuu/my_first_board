package hello.board.web.format;

public class HtmlFormatter {

    public String getFormattedContent(String content) {
        return content.replaceAll("\n", "<br>");
    }
}
