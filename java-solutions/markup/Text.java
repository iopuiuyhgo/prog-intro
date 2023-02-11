package markup;

public class Text implements Markup {
    private final String word;

    public Text(String word) {
        this.word = word;
    }

    public void toMarkdown(StringBuilder text) {
        text.append(word);
    }

    public void toTex(StringBuilder text) {
        text.append(word);
    }

}
