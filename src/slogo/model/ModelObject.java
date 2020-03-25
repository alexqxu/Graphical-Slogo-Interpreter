package slogo.model;
/**
 * Abstract class that dictates encapsulation and logic for all ModelObjects. This is good code design as it
 * uses inheritance to create an abstract ModelObject class, which would allow for polymorphism for different types of
 * ModelObjects, not just turtles. This allows for greater flexibility.
 * @author Alex Xu and Max S.
 */
public abstract class ModelObject implements ModelInterface{

    private static final double ORIGIN = 0.0;
    private static final double CIRCLE = 360;

    private double xCoordinate;
    private double yCoordinate;
    private double heading;
    protected int ID;

    /**
     * Default Constructor for all ModelObjects
     */
    public ModelObject(){
        xCoordinate = ORIGIN;
        yCoordinate = ORIGIN;
        heading = ORIGIN;
        ID = (int) ORIGIN;
    }

    /**
     * Constructor for ModelObject that takes in a specific ID Value.
     * @param idValue
     */
    public ModelObject(int idValue){
        this();
        ID = idValue;
    }

    /**
     * Overrides method defined in the ModelObject Interface. Returns the X location of the ModelObjects (Center of ModelObjects).
     * This coordinate is not necessarily a direct Pixel location.
     * @return int representing X coordinate in an imaginary plane
     */
    @Override
    public double getX() {
        return xCoordinate;
    }

    /**
     * Overrides method defined in the ModelObject Interface. Returns the Y location of the ModelObjects (Center of ModelObjects).
     * This coordinate is not necessarily a direct Pixel location.
     * @return int representing Y coordinate in an imaginary plane
     */
    @Override
    public double getY() {
        return yCoordinate;
    }

    /**
     * Overrides method defined in the ModelObject Interface. Returns the Heading of the ModelObjects (in degrees)
     * @return double representing the heading value
     */
    @Override
    public double getHeading() {
        return heading;
    }

    /**
     * Overrides method defined in the ModelObject Interface. Moves the ModelObjects by the specified distance.
     * @param distance to move the ModelObjects
     */
    @Override
    public void move(double distance) {
        xCoordinate += calcX(distance);
        yCoordinate += calcY(distance);
    }

    /**
     * Overrides method defined in the ModelObject Interface. Sets the heading of the ModelObjects to the specified degree.
     * @param degree to set the ModelObjects heading to
     */
    @Override
    public double setHeading(double degree) {
        double prevHeading = heading;
        heading = degree;
        return heading-prevHeading;
    }

    /**
     * Overrides method defined in the ModelObject Interface. Turns the ModelObjects (heading) by the specified degree.
     * @param degree to turn the ModelObjects by
     */
    @Override
    public void turn(double degree) {
        heading += degree;
        while(heading >= CIRCLE){
            heading = heading - CIRCLE;
        }
        while(heading < 0){
            heading = heading + CIRCLE;
        }
    }
    /**
     * Returns the ID Number of the ModelObject.
     */
    @Override
    public int getID(){
        return ID;
    }


    /**
     * Sets the position of the Model object to the given coordinates.
     * @param x x coordinate
     * @param y y coordinate
     * @return distance traveled.
     */
    public double setPosition(double x, double y){
        double prevX = xCoordinate;
        double prevY = yCoordinate;
        xCoordinate = x;
        yCoordinate = y;
        return calcDistance(prevX -x, prevY -y);
    }
    /**
     * Turns the model object left
     * @param degree to turn the model object left by
     * @return degree turned
     */
    public double left(double degree){
        turn(degree);
        return degree;
    }

    /**
     * Turns the model object right
     * @param degree to turn the model object right by
     * @return degree turned
     */
    public double right(double degree){
        turn(-1*degree);
        return degree;
    }

    /**
     * Sets the ModelObject to point toward a particular point.
     * @param x x coordiante
     * @param y y coordinate
     * @return degrees turned
     */
    public double setTowards(double x, double y){
        double prevHeading = heading;
        double relativeX = x - xCoordinate;
        double relativeY = y - yCoordinate;
        double theta = Math.atan(relativeY / relativeX);
        double degrees = Math.toDegrees(theta);
        double angle;
        if(relativeY >= ORIGIN){
            angle = degrees;
        }
        else{
            angle = degrees + CIRCLE/2;
        }
        setHeading(angle);
        return Math.abs(heading - prevHeading);
    }

    /**
     * Moves the model object forward by a certain value
     * @param value to move the model object forward by
     * @return the value the model object moved forward
     */
    public double forward(double value){
        move(value);
        return value;
    }

    /**
     * Moves the model object backward by a certain value
     * @param value to move the model object backward by
     * @return the value the model object moved backward
     */
    public double backward(double value){
        move(-1*value);
        return value;
    }

    private double calcX(double distance){
        double radians = Math.toRadians(heading);
        double cosValue = Math.cos(radians);
        return distance*cosValue;
    }

    private double calcY(double distance){
        double radians = Math.toRadians(heading);
        double sinValue = Math.sin(radians);
        return distance*sinValue;
    }

    private double calcDistance(double x, double y){
        return Math.sqrt(x*x + y*y);
    }
}