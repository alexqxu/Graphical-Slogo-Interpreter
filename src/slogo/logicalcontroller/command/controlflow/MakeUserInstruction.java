package slogo.logicalcontroller.command.controlflow;

import slogo.model.ModelTurtle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MakeUserInstruction extends ControlFlowCommand {
    private String funcName;
    private List<String> body;
    private List<String> variables;

    public MakeUserInstruction(List<List<String>> rawInput){
        super(rawInput.get(2));
        funcName = rawInput.get(0).get(0);
        variables = rawInput.get(1);
        unravelCode();
    }

    /**
     * Getter method to get the name of the command, which is a private variable stored in the class
     * @return The string with the name of the command
     */
    public String getName(){
        return this.funcName;
    }

    /**
     * Method to get the unraveled version of a command that has a body
     * @return A list of strings with the new parsed commands
     */
    @Override
    public List<String> getUnraveledCode(){
        return getBody();

    }

    @Override
    protected void unravelCode() {
        setUnraveledCode(body);
    }
}