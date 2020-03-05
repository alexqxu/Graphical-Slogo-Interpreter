package slogo.logicalcontroller.command.modifier;

import slogo.exceptions.InvalidCommandException;
import slogo.model.ModelTurtle;

import java.lang.reflect.Method;
import java.util.List;

public class Left extends ModifierCommand {

    public Left(List<String> args){
        super(args.get(0));
    }

    @Override
    public String toString() {
        return "left " + this.argument1;
    }
}
