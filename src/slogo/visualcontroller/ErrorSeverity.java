package slogo.visualcontroller;

import javafx.scene.paint.Color;

/**
 * An enumerated type to help determine properties of an error
 * @auther Max Smith
 */
public enum ErrorSeverity {
    BASIC ("Basic", Color.BLUE),
    MEDIUM ("Medium", Color.YELLOW),
    CRITICAL ("Critical", Color.RED);

    private final String mySevereLevel;
    private final Color myColor;
    ErrorSeverity(String level, Color color) {
        mySevereLevel = level;
        myColor = color;
    }

    /**
     * called by view to get properties of error element
     * @return
     */
    public Color getColor() {return myColor;}
    public String getLevel() {return mySevereLevel;}
}
