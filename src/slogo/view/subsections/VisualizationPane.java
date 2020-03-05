package slogo.view.subsections;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import slogo.visualcontroller.VisualError;
import slogo.visualcontroller.VisualLine;
import slogo.visualcontroller.VisualTurtle;

public class VisualizationPane extends Group {

  private static final Color DEFAULT_BG_COLOR = Color.DARKGRAY;
  private static final Color DEFAULT_PEN_COLOR = Color.BLACK;

  private double groupWidth;
  private double groupHeight;

  private Color myBGColor = DEFAULT_BG_COLOR;
  private Color myPenColor = DEFAULT_PEN_COLOR;

  private List<VisualTurtle> myTurtles = new ArrayList<>();
  private List<VisualLine> myLines = new ArrayList<>();

  public VisualizationPane(double width, double height){
    super();
    groupWidth = width;
    groupHeight = height;
  }

  public void update() {
    setBackground();
    addTurtlesToVisualizer();
    addLinesToVisualizer();
    resize(groupWidth, groupHeight);
  }

  private void addLinesToVisualizer() {
    for (VisualLine line : myLines) {
      Line lineImage = new Line();

      setAdjustedLineLocations(line, lineImage);

      lineImage.setStroke(line.getColor());
      lineImage.setStrokeWidth(line.getThickness());

      getChildren().add(lineImage);
    }
  }

  private void setAdjustedLineLocations(VisualLine line, Line lineImage) {
    lineImage.setStartX(getAdjustedX(line.getStartX()));
    lineImage.setStartY(getAdjustedY(line.getStartY()));

    lineImage.setEndX(getAdjustedX(line.getEndX()));
    lineImage.setEndY(getAdjustedY(line.getEndY()));
  }

  private void addTurtlesToVisualizer() {
    for (VisualTurtle turtle : myTurtles) {
      ImageView turtleImage = new ImageView(turtle.getImage());

      turtleImage.setFitWidth(turtle.getSize());
      turtleImage.setPreserveRatio(true);

      setAdjustedX(turtleImage, turtle.getPreviousX());
      setAdjustedY(turtleImage, turtle.getPreviousY());

      Animation turtleAnimation = makeAnimation(turtle, turtleImage);
      turtleAnimation.play();

      Lighting lighting = getLightingEffect(turtle.getColor());
      turtleImage.setEffect(lighting);

      getChildren().add(turtleImage);

      turtle.setChangeState(false);
    }
  }

  private Animation makeAnimation (VisualTurtle turtle, Node agent) {
    // create something to follow
    Path path = new Path();
    path.getElements().add(new MoveTo(getAdjustedX(turtle.getPreviousX()),
        getAdjustedY(turtle.getPreviousY())));
    path.getElements().add(new LineTo(getAdjustedX(turtle.getCenterX()),
        getAdjustedY(turtle.getCenterY())));
    // create an animation where the shape follows a path
    PathTransition pt = new PathTransition(Duration.seconds(4), path, agent);
    // create an animation that rotates the shape
    RotateTransition rt = new RotateTransition(Duration.seconds(3));
    rt.setFromAngle(360 - turtle.getPreviousHeading());
    rt.setToAngle(360 - turtle.getHeading());
    // put them together in order
    return new SequentialTransition(agent, pt, rt);
  }
  
  private void setBackground() {
    Rectangle background = new Rectangle();
    background.setWidth(groupWidth);
    background.setHeight(groupHeight);
    background.setFill(myBGColor);
    getChildren().add(background);
  }

  private void setAdjustedX(ImageView turtleImage, double centerX) {
    double adjustedX = getAdjustedX(centerX);
    if (adjustedX <= groupWidth && adjustedX >= 0) {
      turtleImage.setX(adjustedX);
    } else {
      turtleImage.setVisible(false);
    }
  }

  private double getAdjustedX(double centerX) {
    return centerX + groupWidth /2;
  }

  private void setAdjustedY(ImageView turtleImage, double centerY) {
    double adjustedY = getAdjustedY(centerY);
    if (adjustedY <= groupHeight && adjustedY >= 0) {
      turtleImage.setY(adjustedY);
    } else {
      turtleImage.setVisible(false);
    }
  }

  private double getAdjustedY(double centerY) {
    return -1*centerY + groupHeight /2;
  }

  private Lighting getLightingEffect (Color color) {
    Lighting lighting = new Lighting();
    lighting.setDiffuseConstant(1.0);
    lighting.setSpecularConstant(0.0);
    lighting.setSpecularExponent(0.0);
    lighting.setSurfaceScale(0.0);
    lighting.setLight(new Light.Distant(45, 45, color));
    return lighting;
  }

  public void addVisualTurtle(VisualTurtle turtle) {
    myTurtles.add(turtle);
    update();
  }

  public void addVisualLine(VisualLine line) {
    myLines.add(line);
    update();
  }

  private void displayError(VisualError error) {
    //TODO: add code to have popup that displays error message with okay button
  }

  public void setBGColor(double red, double green, double blue) {
    myBGColor = new Color(red, green, blue, 1);
    update();
  }

  public void clearElements() {
    myTurtles = new ArrayList<>();
    myLines = new ArrayList<>();
    update();
  }

  public void resetBGColor() {
    myBGColor = DEFAULT_BG_COLOR;
    update();
  }

  public void setPenColor(Color customColor) {
    myPenColor = customColor;
    update();
  }
}
