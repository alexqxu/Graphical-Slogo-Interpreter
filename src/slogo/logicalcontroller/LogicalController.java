package slogo.logicalcontroller;

import slogo.exceptions.DeprecationException;
import slogo.exceptions.InvalidCommandException;
import slogo.exceptions.InvalidLanguageException;
import slogo.exceptions.LogicalException;
import slogo.logicalcontroller.codefilters.MasterCodeFilterUtility;
import slogo.logicalcontroller.command.Command;
import slogo.logicalcontroller.variable.VariableList;
import slogo.model.ModelCollection;
import slogo.visualcontroller.VisualController;

import java.io.IOException;
import java.util.Arrays;

/**
 * Logical controller handles the interaction between the user input from the GUI, the parser, command objects,
 * variables, and changes in the Model package.
 * @author Alex Xu, Max Smith
 */
public class LogicalController {
  public static final String DEFAULT_LANGUAGE = "ENGLISH";
  public static final String NEW_LINE = "\n";

  private Parser myParser;
  private ModelCollection myModelCollection;
  private VisualController myVisualController;
  private VariableList myVariables;
  private String myLanguage;

  /**
   * Constructor for the Logical Controller
   * @param modelCollection
   * @param visualController
   * @param variables
   */
  public LogicalController(ModelCollection modelCollection, VisualController visualController, VariableList variables){
    this.myModelCollection = modelCollection;
    this.myVisualController = visualController;
    this.myVariables = variables;
    this.myLanguage = DEFAULT_LANGUAGE;
    createParser();
  }

  /**
   * To be called from the front-end to change the language (also needs to happen the first time).
   * @param language
   */
  public void setLanguage(String language) {
    myLanguage = language;
    try {
      myParser.setLanguage(language);
    } catch (IOException e) {
      this.myVisualController.updateErrors(new InvalidLanguageException());
    }
  }

  /**
   * Code that interacts with the GUI, and receives strings as commands
   * Assumption is that there is only one ModelObject (Turtle)
   * @param fullUserInput
   * @throws InvalidCommandException
   */
  public void handleNewCommand(String fullUserInput) {
    try {
      String filteredInput = applyFilters(fullUserInput);
      this.myParser.parse(Arrays.asList(filteredInput.split(NEW_LINE)));
      fetchExecuteCycle();
      this.myVisualController.updateCommand(fullUserInput);
    } catch (LogicalException e) {
      this.myVisualController.updateErrors(e);
    } catch (DeprecationException e) {
      this.myVisualController.deprecateProgram(e);
    }
  }

  private void createParser() {
    try {
      myParser= new Parser(DEFAULT_LANGUAGE, this.myModelCollection, this.myVariables);
    } catch (Exception e) {
      System.exit(0);
    }
  }

  private String applyFilters(String userInput) {
    return MasterCodeFilterUtility.filter(userInput, myLanguage);
  }

  private void fetchExecuteCycle(){
    while(!this.myParser.isFinished()){
      this.myParser.executeNextCommand();
      Command latestCommand = this.myParser.getLatestCommand();
      ModelCollection newModel = this.myParser.getModel();
      VariableList newVariables = this.myParser.getVariables();
      this.myVisualController.update(newModel, newVariables, latestCommand);
    }
  }
}