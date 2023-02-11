package markup;

import java.util.List;

public class Paragraph implements Markup {
    private final List<Markup> text;

    public Paragraph(List<Markup> text) {
        this.text = text;
    }

    public void toMarkdown(StringBuilder newtext) {
        for (Markup i : text) {
            i.toMarkdown(newtext);
        }
    }

    public void toTex(StringBuilder newtext) {
        for (Markup i : text) {
            i.toTex(newtext);
        }
    }

}
