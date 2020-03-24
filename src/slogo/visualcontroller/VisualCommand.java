package slogo.visualcontroller;

import slogo.logicalcontroller.command.Command;

/**
 * A visual command which only contains text, visualized to the user
 * @auther Max Smith
 */
public class VisualCommand extends VisualText{

    public VisualCommand(Command command) {
        super(command.toString());
    }

    public VisualCommand(String fullUserInput) {
        super(fullUserInput);
    }
}
