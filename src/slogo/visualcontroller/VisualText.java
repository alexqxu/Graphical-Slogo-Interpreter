package slogo.visualcontroller;

/**
 * A visual text object which only stores text, populates the side panes appropriateley
 * @auther Max Smith
 */
public abstract class VisualText {

    private final String myString;

    public VisualText(String string) {
        this.myString = string;
    }

    @Override
    public String toString() {
        return this.myString;
    }

}
