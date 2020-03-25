package slogo.model;
/**
 * Class handles encapsulation of Model Turtle information and movement logic. This is a concrete implementation of the
 * ModelObject superclass. This shows off the advantage of using polymorphism, as ModelTurtles can be just one of many
 * different types of ModelObjects in SLogo, for extension. This makes the code more flexible, and hides implementation details/specifics away from the higher-level abstract classes and interfaces.
 * @author Alex Xu and Max S
 */
public class ModelTurtle extends ModelObject {

    private Pen myPen;
    private boolean isShowing;
    private boolean isActive;

    /**
     * Default constructor of ModelTurtle object
     */
    public ModelTurtle(){
        super();
        orientTurtle();
    }

    /**
     * Constructor for ModelTurtle object that takes a specific ID
     * @param id
     */
    public ModelTurtle(int id) {
        super(id);
        orientTurtle();
    }

    /**
     * Overrides method defined in the ModelObject Interface.
     * Sets the Pen thickness to the specified value
     */
    @Override
    public void setPenThickness(double thickness){
        myPen.setThickness(thickness);
    }

    /**
     * Overrides method defined in the ModelObject Interface. Returns the pen state
     * @return boolean representing whether or not the pen is active
     */
    @Override
    public boolean isPenActive() {
        return myPen.isActive();
    }

    /**
     * Overrides method defined in the ModelObject Interface. Returns the pen thickness.
     * @return double representing pen thickness.
     */
    @Override
    public double getPenThickness() {
        return myPen.getThickness();
    }

    /**
     * Deactivates/sets the pen to the up position
     * @return 0 value
     */
    public double penUp(){
        myPen.penUp();
        return 0;
    }

    /**
     * Activates/sets the pen to the down position
     * @return 1 value
     */
    public double penDown(){
        myPen.penDown();
        return 1;
    }

    /**
     * Sets the turtle to show itself
     * @return 1 value
     */
    public double showTurtle(){
        isShowing = true;
        return 1;
    }

    /**
     * Sets the turtle to hide itself
     * @return 0 value
     */
    public double hideTurtle(){
        isShowing = false;
        return 0;
    }

    /**
     * Activates the turtle, meaning that the commands given will be followed. (Enables Teller Commands)
     */
    public void activate() {
        this.isActive = true;
    }

    /**
     * Deactivates the turtle, meaning that the commands given will be followed. (Enables Teller Commands)
     */
    public void deactivate() {
        this.isActive = false;
    }

    /**
     * Returns whether or not the turtle is showing
     * @return True for showing, False otherwise
     */
    public boolean isShowing(){
        return isShowing;
    }

    /**Returns whether or not the turtle is active
     * @return True for active, False otherwise
     */
    public boolean isActive() {return this.isActive;}

    private void orientTurtle() {
        this.myPen = new Pen();
        this.isShowing = true;
        this.isActive = true;
    }
}
