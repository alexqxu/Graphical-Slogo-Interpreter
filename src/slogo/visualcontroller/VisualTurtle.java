package slogo.visualcontroller;

import javafx.scene.paint.Color;
import slogo.model.ModelTurtle;
import slogo.view.TurtleImage;

/**
 * A visual turtle object which is constructed from a ModelTurtle object, displayed in the view
 * @auther Max Smith, Grant LoPresti
 */
public class VisualTurtle extends VisualObject {

  public static final boolean DEFAULT_CHANGE_STATE = false;
  public static final TurtleImage DEFAULT_IMAGE = TurtleImage.TURTLE;
  public static final Color DEFAULT_COLOR = Color.PURPLE;
  public static final double DEFAULT_SIZE = 30;
  public static final double DEFAULT_X = 0;
  public static final double DEFAULT_Y = 0;
  public static final double DEFAULT_HEADING = 0;

  private int myID;
  private boolean myChangeState = DEFAULT_CHANGE_STATE;
  private TurtleImage myImage = DEFAULT_IMAGE;
  private Color myColor = DEFAULT_COLOR;
  private double myCenterX = DEFAULT_X;
  private double myCenterY = DEFAULT_Y;
  private double myPreviousX = DEFAULT_X;
  private double myPreviousY = DEFAULT_Y;
  private double myHeading = DEFAULT_HEADING;
  private double myPreviousHeading = DEFAULT_HEADING;
  private double mySize = DEFAULT_SIZE;
  private double myPenThickness;

  /**
   * Empty constructor for testing within VisualizationPane
   */
  public VisualTurtle(){

  }

  /**
   * Constructor for visual turtle object from a model turtle object
   * @param turtle is a model turtle, from the visual controller (via logical controller)
   *               that is constructed here for model manipulation
   */
  public VisualTurtle(ModelTurtle turtle) {
    setCurrentValues(turtle);
    myID = turtle.getID();
  }

  /**
   * Called on VisualTurtle in VisualController to modify turtle state
   * @param turtle
   * TODO - Only pass around immutable turtle objects
   */
  public void updateVisualTurtle(ModelTurtle turtle) {
    if (turtle.getID() == myID) {
      updatePriorValues();
      setCurrentValues(turtle);
    } else {
      // TODO - implement error condition, visual controller attempting to modify wrong turtle
    }

  }

  private void setCurrentValues(ModelTurtle turtle) {
    myCenterX = turtle.getX();
    myCenterY = turtle.getY();
    myHeading = turtle.getHeading();
    myPenThickness = turtle.getPenThickness();
  }

  private void updatePriorValues() {
    myPreviousX = myCenterX;
    myPreviousY = myCenterY;
    myPreviousHeading = myHeading;
  }

  /**
   * called by view to get size
   * @return size
   */
  public double getSize() {
    return mySize;
  }

  /**
   * called by view to set size
   * @return size
   */
  public void setSize(double size) {
    mySize = size;
  }

  /**
   * called by view to get image
   * @return image
   */
  public TurtleImage getImage() {
    return myImage;
  }

  /**
   * called by view to set image
   * @return image
   */
  public void setImage(TurtleImage image) {
    myImage = image;
  }

  /**
   * called by view to get centerx
   * @return centerx
   */
  public double getCenterX() {
    return myCenterX;
  }
  public void setCenterX(double centerX) {
    myCenterX = centerX;
  }

  /**
   * called by view to get centery
   * @return centery
   */
  public double getCenterY() {
    return myCenterY ;
  }
  public void setCenterY(double centerY) {
    myCenterY = centerY;
  }

  /**
   * called by view to get priorx
   * @return priorx
   */
  public double getPreviousX() {
    return myPreviousX;
  }
  public void setPreviousX(double previousX) {
    myPreviousX = previousX;
  }

  /**
   * called by view to get priory
   * @return priory
   */
  public double getPreviousY() {
    return myPreviousY;
  }
  public void setPreviousY(double previousY) {
    myPreviousY = previousY;
  }

  /**
   * called by view to get heading
   * @return heading
   */
  public double getHeading() {
    return myHeading;
  }
  public double getPreviousHeading() {
    return myPreviousHeading;
  }
  public void setHeading(double heading) {
    myHeading = heading;
  }

  /**
   * called by view to get/set color
   * @return color
   */
  public Color getColor() {
    return myColor;
  }
  public void setColor(Color color) {
    myColor = color;
  }

  /**
   * called by view to set coordinate
   * @return none
   */
  public void setCenter(double centerX, double centerY) {
    myCenterX = centerX;
    myCenterY = centerY;
  }

  public void setPreviousCenter(double centerX, double centerY) {
    myPreviousX = centerX;
    myPreviousY = centerY;
  }

  /**
   * called by view to draw line of thickness
   * @return line thickness
   */
  public double getPenThickeness() {
    return myPenThickness;
  }

  @Override
  public String toString() {return "Testing";}

  /**
   * called by viewcontroller to determine multiple turtle commands
   * @return id
   */
  public Integer getId() {
    return myID;
  }
}
