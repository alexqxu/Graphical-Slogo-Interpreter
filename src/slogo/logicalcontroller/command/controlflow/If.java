package slogo.logicalcontroller.command.controlflow;

import slogo.logicalcontroller.command.Command;

public class If extends ControlFlowCommand {
    private double value;

    public If(String inputvalue){
        value = Double.parseDouble(inputvalue);

    }

    @Override
    public double getValue() {
        return this.value;
    }

    @Override
    public String getCommandType() {
        return "If";
    }
}