package markup;

import java.util.List;

public class Strong extends AbstractToMarkdown {
    //private StringBuilder newtext = new StringBuilder();

    public Strong(List<Markup> intext) {
        super("\\textbf", "__", intext);
    }

}
