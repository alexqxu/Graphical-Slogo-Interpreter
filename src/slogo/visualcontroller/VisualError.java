package slogo.visualcontroller;

import javafx.scene.paint.Color;
import slogo.exceptions.DeprecationException;
import slogo.exceptions.LogicalException;

/**
 * A visual error which is displayed to the user in a tab and as a popup
 * @auther Max Smith
 */
public class VisualError {

    private final String myMessage;
    private final ErrorSeverity mySeverity;
    private static final String SEPARATOR = " ERROR: ";
    private static final String SLEEP_MESSAGE =  "\nProgram will exit after close popup";

    public VisualError(LogicalException e) {
        this.myMessage = e.getMessage();
        this.mySeverity = e.getErrorSeverity();
    }

    public VisualError(DeprecationException e) {
        this.myMessage = e.getMessage() + SLEEP_MESSAGE;
        this.mySeverity = e.getErrorSeverity();
    }

    public VisualError(String message, ErrorSeverity severe) {
        this.myMessage = message;
        this.mySeverity = severe;
    }

    public ErrorSeverity getSeverity() {return this.mySeverity;}
    public Color getColor() {return this.mySeverity.getColor();}

    @Override
    public String toString() {return errorMessage();}

    private String errorMessage() {
        return mySeverity.getLevel().toUpperCase() + SEPARATOR + this.myMessage;
    }

}
