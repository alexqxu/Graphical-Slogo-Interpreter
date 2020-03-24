package slogo.visualcontroller;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import slogo.exceptions.DeprecationException;
import slogo.exceptions.LogicalException;
import slogo.logicalcontroller.command.Command;
import slogo.logicalcontroller.variable.Variable;
import slogo.logicalcontroller.variable.VariableList;
import slogo.model.ModelCollection;
import slogo.model.ModelObject;
import slogo.model.ModelTurtle;
import slogo.view.TurtleImage;
import slogo.view.windows.SlogoView;

import java.util.*;

/**
 * The VisualController is used to mediate information exchange between the LogicalController and the View.
 * It uses two media of communication, method calls for larger objects and bindings for text.
 * @auther Max Smith
 */
public class VisualController implements VisualInterface {

  private double myAnimationRate;
  private SlogoView mySlogoView;

  // Currently mirroring structure of VisualizationPane.java (change to bindings)
  private Map<Integer, VisualTurtle> myTurtles = new HashMap<>();
  private ObservableList<VisualTurtle> myTurtlesList = FXCollections.observableArrayList(myTurtles.values());

  private List<VisualLine> myLines = new ArrayList<>();

  private SimpleObjectProperty<ObservableList<VisualError>> myErrorsProperty;
  private SimpleObjectProperty<ObservableList<VisualCommand>> myCommandsProperty;
  private SimpleObjectProperty<ObservableList<VisualUserFunction>> myFunctionsProperty;
  private SimpleObjectProperty<ObservableList<VisualData>> myDataProperty;
  private SimpleObjectProperty<ObservableList<VisualVariable>> myVariablesProperty;
  private SimpleObjectProperty<ObservableList<VisualFile>> myFilesProperty;
  private SimpleObjectProperty<ObservableList<Integer>> myTurtleNamesProperty;

  public VisualController() {
    initProperties();
  }

  private void initProperties() {
    myErrorsProperty = new SimpleObjectProperty<>(FXCollections.observableArrayList());
    myCommandsProperty = new SimpleObjectProperty<>(FXCollections.observableArrayList());
    myFunctionsProperty = new SimpleObjectProperty<>(FXCollections.observableArrayList());
    myDataProperty = new SimpleObjectProperty<>(FXCollections.observableArrayList());
    myVariablesProperty = new SimpleObjectProperty<>(FXCollections.observableArrayList());
    myFilesProperty = new SimpleObjectProperty<>(FXCollections.observableArrayList());
    myTurtleNamesProperty = new SimpleObjectProperty<>(FXCollections.observableArrayList());
  }

  @Override
  public void setSlogoView(SlogoView view) {
    mySlogoView = view;
  }

  /**
   * Public method callable by the logical controller to adjust the animation rate
   * @param rate is the new animation rate for adding lines into the view
   * TODO - implement animation rate of object addition via queueing/threading (not threading lol)
   */
  @Override
  public void setAnimationRate(double rate) {
    myAnimationRate = rate;
  }

  @Override
  public void update(ModelCollection model, VariableList variableList, Command command) {
    moveModelObject(model, command);
    updateVariables(variableList);
  }

  /**
   *
   * @param e Exception that was just thrown by the logical controller
   * TODO - Incorporate error severity into logical controller error creation
   */
  @Override
  public void updateErrors(LogicalException e) {
    VisualError error = new VisualError(e);
    myErrorsProperty.getValue().add(error);
    mySlogoView.announceError(error);
  }

  @Override
  public void changeTurtleImage(String newValue) {
    TurtleImage image = TurtleImage.valueOf(newValue);
    for (Integer i : myTurtles.keySet()) {
      // System.out.printf("INDEX: ", i);
      myTurtles.get(i).setImage(image);
    }
  }

  @Override
  public Property getProperty(VisualProperty type) {
    switch (type) {
      case COMMAND:
        return myCommandsProperty;
      case DATA:
        return myDataProperty;
      case VARIABLE:
        return myVariablesProperty;
      case ERROR:
        return myErrorsProperty;
      case FUNCTION:
        return myFunctionsProperty;
      case FILE:
        return myFilesProperty;
      case TURTLE:
        return myTurtleNamesProperty;
      default:
        throw new IllegalArgumentException();
    }
  }

  private void updateVariables(VariableList variableList) {
    myVariablesProperty.getValue().clear();
    if (!variableList.isEmpty()) {
      Iterator iter = variableList.iterator();
      while (iter.hasNext()) {
        myVariablesProperty.getValue().add(new VisualVariable((Variable)iter.next()));
      }
    }
  }

  // TODO: leverage command knowledge to dictate turtle motion (rotations specifically)
  private void moveModelObject(ModelCollection modelCollection, Command command) {
    Collection<ModelObject> turtles = modelCollection.getActiveTurtles().getModelMap().values();
    for (Object o: turtles) {
      ModelTurtle turtle = (ModelTurtle) o;
      moveTurtle(turtle);
    }
  }

  @Override
  public void updateCommand(String userInput) {
    FXCollections.reverse(this.myCommandsProperty.getValue());
    this.myCommandsProperty.getValue().add(new VisualCommand(userInput));
    FXCollections.reverse(this.myCommandsProperty.getValue());
  }

  @Override
  public void deprecateProgram(DeprecationException e) {
    VisualError ve = new VisualError(e);
    this.mySlogoView.announceError(ve);
    System.exit(0);
  }

  private void moveTurtle(ModelTurtle turtle) {
    VisualTurtle visualTurtle = addTurtleToMap(turtle);
    myTurtlesList.add(visualTurtle);
    visualTurtle.updateVisualTurtle(turtle);
    try {
      mySlogoView.updateVisualTurtles(new ArrayList<>(List.of(visualTurtle)));
      if (turtle.isPenActive())
        appendLine(new VisualLine(visualTurtle));
    } catch (NullPointerException e) {
      // System.out.println("Given null turtle set, passing on draw");
    }
  }

  private void appendLine(VisualLine line) {
    myLines.add(line);
    mySlogoView.updateVisualLines(new ArrayList<VisualLine>(List.of(line)));
  }

  private VisualTurtle addTurtleToMap(ModelTurtle turtle) {
    myTurtles.putIfAbsent(turtle.getID(), new VisualTurtle(turtle));
    myTurtleNamesProperty.getValue().add(turtle.getID());
    return myTurtles.get(turtle.getID());
  }
}
