package slogo.logicalcontroller;

import slogo.exceptions.InvalidCommandException;
import slogo.exceptions.NoCommandFound;
import slogo.logicalcontroller.command.Command;
import slogo.logicalcontroller.command.modifier.Forward;
import slogo.logicalcontroller.input.UserInput;
import slogo.logicalcontroller.variable.MakeVariable;
import slogo.logicalcontroller.variable.Variable;
import slogo.model.ModelCollection;
import slogo.visualcontroller.VisualController;

import javax.script.ScriptException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Logical controller handles the interaction between the user input from the GUI, the parser, command objects,
 * variables, and changes in the Model package.
 * @author Alex Xu
 */
public class LogicalController {
  private static final String DEFAULT_LANGUAGE = "ENGLISH";

  private Parser myParser;

  private ModelCollection myModelCollection;
  private VisualController myVisualController;
  private List<Variable> myVariables;

  private LogicalController(){
  };

  public LogicalController(ModelCollection modelCollection, VisualController visualController, List<Variable> variables){
    myModelCollection = modelCollection;
    myVisualController = visualController;
    // TODO - update visualController initial state to empty lists to get first turtle to show
    // myVisualController.moveModelObject(myModelCollection);
    myVariables = variables;
    try {
      this.setLanguage(DEFAULT_LANGUAGE);
    } catch (Exception e) {
      System.exit(0);
    }
  }

  /**
   * To be called from the front-end to change the language (also needs to happen the first time).
   * @param language
   * @throws IOException
   */
  public void setLanguage(String language) throws IOException {
    this.myParser = new Parser(language);                         //TODO: Might need to change.
  }

  /**
   * Code that interacts with the GUI, and receives strings as commands
   * Assumption is that there is only one ModelObject (Turtle)
   * @param fullUserInput
   * @throws InvalidCommandException
   */
  public void handleNewCommand(String fullUserInput) throws InvalidCommandException, NoSuchMethodException, InstantiationException, ScriptException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
    myParser.parse(Arrays.asList(fullUserInput.split("\n")));
    while(!myParser.isFinished()){
      myParser.executeNextCommand();
      //Command latestCommand = myParser.getLatestCommand();
      ModelCollection newModel = myParser.getModel();
      //List<Variable> newVariables = myParser.getVariables();
      //myVisualController.update(newModel, newVariables, latestCommand);
    }
  }

  //TODO: Below is just for testing purposes, for the Parser functionality.
  public static void main (String[] args) throws IOException, NoSuchMethodException, InstantiationException, ScriptException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
    LogicalController test = new LogicalController();
    test.setLanguage(DEFAULT_LANGUAGE);

    String userInput = "fd fd 50 \n left 10";
    test.handleNewCommand(userInput);
  }

}