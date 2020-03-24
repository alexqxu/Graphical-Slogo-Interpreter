package slogo.logicalcontroller;

import slogo.exceptions.ReflectionException;
import slogo.exceptions.ResourceBundleException;
import slogo.logicalcontroller.command.Command;
import slogo.logicalcontroller.command.comparison.ComparisonCommand;
import slogo.logicalcontroller.command.controlflow.ControlFlowCommand;
import slogo.logicalcontroller.command.userDefinedCommands.customCommandList;
import slogo.logicalcontroller.command.math.MathCommand;
import slogo.logicalcontroller.command.modifier.ModifierCommand;
import slogo.logicalcontroller.command.querie.QuerieCommand;
import slogo.logicalcontroller.command.teller.TellerCommand;
import slogo.logicalcontroller.command.variables.VariableCommand;
import slogo.logicalcontroller.input.UserInput;
import slogo.logicalcontroller.variable.ParserInterface;
import slogo.logicalcontroller.variable.VariableList;
import slogo.model.ModelCollection;
import slogo.model.ModelObject;
import slogo.model.ModelTurtle;

import java.util.*;
import java.io.*;
import java.lang.reflect.*;

/**
 * Purpose of this class is to parse incoming commands from the console and from a text file that the user will have an option to read in.
 * @authors Max S, Alex X, Amjad S
 */
public class Parser implements BundleInterface, ParserInterface {

    private String myLanguage;
    private VariableList myVariableList;
    private customCommandList myCustomCommandList;
    private UserInput myUserInput;
    private ModelCollection myModelCollection;
    private ResourceBundle myLanguageResources;
    private Command myLatestCommand;
    private static final String EXECUTE = "execute";

    /**
     * Constructor for the Parser class that takes in the input language and initializes all the used variables that are required for parsing.
     * @param Takes in the language, model, and the variables
     */
    public Parser(String language, ModelCollection model, VariableList variables) throws IOException {
        setLanguage(language);
        this.myModelCollection = model;
        this.myVariableList = variables;
    }

    /**
     * Reads in the language of the appropriate resource file and loads it into a resource bundle for future use.
     *  Assumes that the language fed in is one from the list already determined
     * @param Takes in the language selected as a string
     */
    @Override
    public void setLanguage(String language) throws IOException {
        this.myLanguage = language;
        this.myLanguageResources = BundleInterface.createResourceBundle(nameLanguageFile());
    }

    private String nameLanguageFile() {
        return "resources/languages/" + this.myLanguage + ".properties";
    }

    /**
     * Called by SlogoView with lines to parse into executable commmands
     * Two stage process, first sets up the UserInput object so that it can interpret and parse based on the language selected
     * @param Takes in parameter of list of raw commands inputted, as strings
     */
    @Override
    public void parse(List<String> lines) throws ResourceBundleException {
        this.myUserInput = new UserInput(lines, this.myLanguageResources);
    }

    private List<String> executeCommand(Command command) {
        try {
            Class superclazz = command.getClass().getSuperclass();
            String name = EXECUTE + superclazz.getSimpleName();
            Method method = this.getClass().getDeclaredMethod(name, superclazz); //Command.class
            Object o = method.invoke(this, command);
            return (List<String>) o;
        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException | NullPointerException e) {
            throw new ReflectionException("Unable to apply Reflection in parser");
        }
    }

    /**
     * Method to find and execute the next command in the arraylist of raw commands. Represents one step of the turtle.
     * Method assumes that there are more command in the list to be executed
     * No parameters as this is a getter method
     */
    @Override
    public void executeNextCommand(){
        this.myLatestCommand = this.myUserInput.getNextCommand();
        List<String> myList = this.executeCommand(this.myLatestCommand);
        this.myUserInput.setCodeReplacement(myList, this.myLatestCommand);
    }

    private List<String> executeGeneralCommand(Command command) {
        String replace = "";
        Collection<ModelObject> turtles = this.myModelCollection.getActiveTurtles().getModelMap().values();
        for (Object o : turtles){
            ModelTurtle turtle = (ModelTurtle) o;
            replace = command.execute(turtle);
        }
        return new ArrayList<String>(List.of(replace));
    }

    private List<String> executeModifierCommand(ModifierCommand command) {
        return executeGeneralCommand(command);
    }

    private List<String> executeQuerieCommand(QuerieCommand command) {
        return executeGeneralCommand(command);
    }

    private List<String> executeComparisonCommand(ComparisonCommand command) {
        return executeGeneralCommand(command);
    }

    private List<String> executeMathCommand(MathCommand command) {
        return executeGeneralCommand(command);
    }

    private List<String> executeTellerCommand(TellerCommand command) {
        return new ArrayList<String>(List.of(command.execute(this.myModelCollection)));
    }

    private List<String> executeControlFlowCommand(ControlFlowCommand command) {
        return new ArrayList<String>();
    }

    private List<String> executeVariableCommand(VariableCommand command) {
        return new ArrayList<String>(List.of(command.execute(this.myVariableList)));
    }

    /**
     * This method returns the latest command that was executed
     * If there was no previous command, it will return null
     * No parameters as this is a getter method
     * @return Returns a Command object representing the latest command executed
     */
    @Override
    public Command getLatestCommand() {
        return this.myLatestCommand;
    }

    /**
     * This method is to return the list of variables that may be defined by the user in the program
     * This method can fail potentially if the data structure goes null
     * No parameters as this is a getter method
     * @return Returns a VariableList object with all the variables in it
     */
    @Override
    public VariableList getVariables() {return this.myVariableList; }

    @Override
    public boolean isFinished(){
        return this.myUserInput.isFinished();
    }

    /**
     * This method is for returning the current model collection, which includes important data about the current state of the model that will be useful in the future
     * If anything causes the modelcollection variable to be null, things may not work as intended
     * This method is dependent on the myModelCollection private instance variable in the Parser class, and takes in no parameters
     * @return This method returns a ModelCollection object
     */
    @Override
    public ModelCollection getModel(){
        return this.myModelCollection;
    }

    /**
     * This method is for returning the custom command list variable that is a part of this class
     * This method assumes that the value of the myCustomCommandList variable is not null
     * Used by other classes to access the custom command list
     * @return customCommandList object
     */
    public customCommandList getCustomCommandList(){
        return this.myCustomCommandList;
    }

    private void setUserInput(List<String> userInput) {
        this.myUserInput = new UserInput(userInput, this.myLanguageResources);
    }

    private UserInput getUserInput() {
        return this.myUserInput;
    }

}
