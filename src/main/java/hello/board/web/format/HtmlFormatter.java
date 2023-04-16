package hello.board.web.format;

public class HtmlFormatter {

    /**
     * String을 받아서 html에서 줄바꿈이 적용되도록 바꿔준다.
     * String에 포함된 "\n" 을 "<br>"로 변경한다.
     */
    public String getHtmlContent(String content) {
        String htmlContent = content.replaceAll("\n", "<br>");
        return htmlContent;
    }

    /**
     * Html format을 받아서 String으로 줄바꿈이 적용되도록 바꿔준다.
     * Html format에 포함된 "<br>"을 "\n"로 변경한다.
     */
    public String getStringContent(String content) {
        String stringContent = content.replaceAll("<br>", "\n");
        return stringContent;
    }
}
