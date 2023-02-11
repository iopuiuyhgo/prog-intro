package markup;

import java.util.List;

public class Strikeout extends AbstractToMarkdown {
    //private StringBuilder newtext = new StringBuilder();

    public Strikeout(List<Markup> intext) {
        super("\\textst", "~", intext);
    }

}
