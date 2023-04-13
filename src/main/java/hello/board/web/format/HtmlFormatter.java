package hello.board.web.format;

public class HtmlFormatter {

    /**
     * String을 받아서 html에서 줄바꿈이 적용되도록 바꿔준다.
     * String에 포함된 "\n" 을 "<br>"로 변경한다.
     */
    public String getFormattedContent(String content) {
        return content.replaceAll("\n", "<br>");
    }
}
