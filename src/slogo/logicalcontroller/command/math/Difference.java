package slogo.logicalcontroller.command.math;

import slogo.logicalcontroller.command.Command;

public class Difference extends MathCommand {

    public Difference(String input1, String input2){
        super(input1, input2);
    }

    @Override
    public double performMath() {
        return this.myArgument1 - this.myArgument2;
    }

    @Override
    public String getCommandType() {
        return "Difference";
    }
}
