package slogo.logicalcontroller.command.modifier;

import slogo.exceptions.InvalidCommandException;
import slogo.logicalcontroller.BundleInterface;
import slogo.logicalcontroller.command.Command;
import slogo.logicalcontroller.variable.VariableList;
import slogo.model.ModelTurtle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * Abstract class for Modifier Commands.
 * Takes in 0, 1, or 2 input commands.
 * @author Alex Xu, Max Smith
 */
public abstract class ModifierCommand implements Command, ModifierInterface {
    public static final String RESOURCE_BUNDLE_LOCATION = "src/properties/modifierCommands.properties";

    private FileInputStream fis;
    private ResourceBundle methodMappings;

    protected double argument1;
    protected double argument2;


    public ModifierCommand()  {
        try {
            this.methodMappings = BundleInterface.createResourceBundle(RESOURCE_BUNDLE_LOCATION);
        } catch (IOException e) {
            // System.out.println("File not found exception");
        }
    }

    public ModifierCommand(String input1){
        this();
        // System.out.println("successfully created modifier command");
        argument1 = Double.parseDouble(input1);
    }

    public ModifierCommand(String input1, String input2){
        this(input1);
        argument2 = Double.parseDouble(input2);
    }


    /**
     * Returns the method name to be called on turtle objects, based on a properties file.
     */
    protected String getMethodName(){
        String key = this.getCommandType();
        return methodMappings.getString(key);
    }

    @Override
    public String getCommandType() {
        String className = this.getClass().getSimpleName();
        return className;
    }

    /**
     * Returns ModifierCommand name using reflection.
     * @return
     */
    public String getCommandCategory(){
        String className = this.getClass().getSuperclass().getSimpleName();
        return className;
    }

    public double getArgument1(){
        return argument1;
    }

    public double getArgument2(){
        return argument2;
    }

    @Override
    public String execute(ModelTurtle turtle){
        try {
            String name = this.getMethodName();
            Method method = turtle.getClass().getMethod(name.toLowerCase(), double.class);
            double value = this.getArgument1();
            Object result = method.invoke(turtle, value);
            return result.toString();
        } catch (Exception e) {
            throw new InvalidCommandException();
        }
    }

    protected String executeDoubleParameter(ModelTurtle turtle) {
        try {
            String name = this.getMethodName();
            Method method = turtle.getClass().getMethod(name, double.class, double.class);
            Double value = this.getArgument1();
            Double value2 = this.getArgument2();
            return method.invoke(turtle, value, value2).toString();
        } catch (Exception e) {
            throw new InvalidCommandException();
        }
    }

    @Override
    public abstract String toString();

    @Override
    public String execute(VariableList list) {
        return null;
    }

}