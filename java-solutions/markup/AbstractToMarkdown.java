package markup;

import java.util.List;

public abstract class AbstractToMarkdown implements Markup {
    protected StringBuilder newtext = new StringBuilder();
    private String shapemod;
    private String shape;
    private List<Markup> intext;

    public AbstractToMarkdown(String shapemod, String shape, List<Markup> intext) {
        this.shapemod = shapemod;
        this.shape = shape;
        this.intext = intext;
    }


    public final void toMarkdown(StringBuilder text) {
        text.append(shape);
        for (Markup i : intext) {
            i.toMarkdown(text);
        }
        text.append(shape);
    }

    public final void toTex(StringBuilder text) {
        text.append(shapemod + "{");
        for (Markup i : intext) {
            i.toTex(text);
        }
        text.append("}");
    }

}
