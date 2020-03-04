package slogo.logicalcontroller.command.math;

import slogo.logicalcontroller.command.Command;

public class NaturalLog extends MathCommand {

    public NaturalLog(String input){
        super(input);
    }

    @Override
    protected void performMath() {
        setReturnValue(Math.log(this.myArgument1));
    }

    @Override
    public String getCommandType() {
        return "NaturalLog";
    }
}
