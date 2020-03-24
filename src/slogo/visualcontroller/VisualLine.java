package slogo.visualcontroller;

import javafx.scene.paint.Color;

/**
 * A visual line which is immediately populated inside of the view
 * @auther Max Smith
 */
public class VisualLine {
  private static final Color DEFAULT_COLOR = Color.BLACK;
  private static final double DEFAULT_THICKNESS = 5;

  private final double myStartX;
  private final double myStartY;
  private final double myEndX;
  private final double myEndY;
  private final Color myColor;
  private final double myThickness;

  public VisualLine(double startX, double startY, double endX, double endY, Color color, double thickness) {
    myStartX = startX;
    myStartY = startY;
    myEndX = endX;
    myEndY = endY;
    myColor = color;
    myThickness = thickness;
  }

  public VisualLine(VisualTurtle turtle) {
    myStartX = turtle.getPreviousX();
    myEndX = turtle.getCenterX();
    myStartY = turtle.getPreviousY();
    myEndY = turtle.getCenterY();
    myColor = turtle.getColor();
    myThickness = turtle.getPenThickeness();
  }

  /**
   * called by view to draw line
   * @return startx
   */
  public double getStartX() {
    return myStartX;
  }

  /**
   * called by view to draw line
   * @return starty
   */
  public double getStartY() {
    return myStartY;
  }

  /**
   * called by view to draw line
   * @return endx
   */
  public double getEndX() {
    return myEndX;
  }

  /**
   * called by view to draw line
   * @return endy
   */
  public double getEndY() {
    return myEndY;
  }

  /**
   * called by view to get color
   * @return color
   */
  public Color getColor() {
    return myColor;
  }

  /**
   * called by view to draw line
   * @return thickness
   */
  public double getThickness() {
    return myThickness;
  }

}
