package slogo.logicalcontroller.command.teller;

import slogo.logicalcontroller.command.Command;
import slogo.logicalcontroller.variable.VariableList;
import slogo.model.ModelCollection;
import slogo.model.ModelTurtle;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class for TellerCommand, operations on multiple turtles
 * @author Math Smith
 */
public abstract class TellerCommand implements Command {

    protected List<Integer> myArgs;

    public TellerCommand(List<String> args) {
        myArgs = new ArrayList<Integer>();
        for (String s: args) {
            // System.out.printf("attempting to add string (%s) to args list\n", s);
            this.myArgs.add(Integer.parseInt(s));
        }
    }

    /**
     * abstract call to execute, helps with code replace in parser
     * @param model
     * @return
     */
    public abstract String execute(ModelCollection model);

    @Override
    public String execute(VariableList list) {
        return null;
    }

    @Override
    public String execute(ModelTurtle turtle) {
        return null;
    }

}
