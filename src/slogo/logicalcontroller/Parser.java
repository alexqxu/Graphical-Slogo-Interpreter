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
    
    @Override
    public void setLanguage(String language) throws IOException {
        this.myLanguage = language;
        this.myLanguageResources = BundleInterface.createResourceBundle(nameLanguageFile());
    }

    private String nameLanguageFile() {
        return "resources/languages/" + this.myLanguage + ".properties";
    }

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

    @Override
    public Command getLatestCommand() {
        return this.myLatestCommand;
    }

    @Override
    public VariableList getVariables() {return this.myVariableList; }

    @Override
    public boolean isFinished(){
        return this.myUserInput.isFinished();
    }

    @Override
    public ModelCollection getModel(){
        return this.myModelCollection;
    }

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
