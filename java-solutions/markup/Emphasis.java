package markup;

import java.util.List;

public class Emphasis extends AbstractToMarkdown {

    public Emphasis(List<Markup> intext) {
        super("\\emph", "*", intext);
    }
}
