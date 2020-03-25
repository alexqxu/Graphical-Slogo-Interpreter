package slogo.model;

/**
 * Interface that dictates public methods that all ModelObjects are expected to support. This is the API for the model.
 * Assumptions Include: Pen is always at the same location of the ModelObject. I think that this code is well-designed
 * because it fully utilizes the inheritance to provide for the most flexibility in implementing objects included in the Model.
 * This is the public API that other components of the program can communicate through.
 * @author Alex Xu
 */
public interface ModelInterface {
    /**
     * Returns the X location of the ModelObject, depending on implementation
     * @return double representing X coordinate
     */
    public double getX();

    /**
     * Returns the Y location of the ModelObject, depending on the implementation
     * @return double representing Y coordinate
     */
    public double getY();

    /**
     * Returns heading (in degrees) of the ModelObject
     * @return double representing degrees
     */
    public double getHeading();

    /**
     * Returns the ID of the ModelObject
     * @return int representing the ID number
     */
    public int getID();

    /**
     * Returns whether the Pen is Active or Not.
     * @return boolean representing pen state.
     */
    public boolean isPenActive();

    /**
     * Returns the thickness of the pen
     * @return double representing the thickness of the Pen.
     */
    public double getPenThickness();

    /**
     * Sets the thickness of a Pen with the specified value
     */
    public void setPenThickness(double thickness);

    /**
     * Moves the ModelObject by a distance
     * @param distance to move the ModelObject
     */
    public void move(double distance);

    /**
     * Sets the Heading to the specified degree
     * @param degree to set the ModelObject heading to
     */
    public double setHeading(double degree);

    /**
     * Turns the ModelObject (heading) by the specified degree
     * @param degree to turn the ModelObject by
     */
    public void turn(double degree);
}