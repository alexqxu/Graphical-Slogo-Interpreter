package slogo.model;

import java.util.*;

/**
 * The purpose of this class is to provide an encapsulation of the data structure used to hold multiple
 * ModelObjects, so that the Logical Controller does not need to depend on any specific implementation
 * of data structure.
 * @author Alex Xu, Max Smith
 */
public class ModelCollection {

    private Map<Integer, ModelObject> myModelObjectMap;

    /**
     * Default Constructor for ModelCollection object
     */
    public ModelCollection(){
        initLists();
    }

    private void initLists() {
        this.myModelObjectMap = new HashMap<Integer, ModelObject>();
    }

    public ModelCollection(List<ModelObject> objects) {
        this();
        for (ModelObject o: objects) {
            this.append(o);
        }
    }

    /**
     * Add a ModelObject to the ModelCollection
     * @param object
     */
    public void append(ModelObject object){
        this.myModelObjectMap.putIfAbsent(object.getID(), object);
    }

    public Map<Integer, ModelObject>  getModelMap() {
        return this.myModelObjectMap;
    }

    /**
     *
     * @return new model collection with all active turtles after a teller has been run
     */
    public ModelCollection getActiveTurtles() {
        List<ModelObject> activeTurtles = new ArrayList<ModelObject>();
        for (ModelObject o: this.myModelObjectMap.values()) {
            ModelTurtle turtle = (ModelTurtle) o;
            if (turtle.isActive()) {
                activeTurtles.add(turtle);
            }
        }
        return new ModelCollection(activeTurtles);
    }

    public void activate(int id) {
        ModelObject o = myModelObjectMap.get(id);
        ModelTurtle turtle = (ModelTurtle)o;
        turtle.activate();
    }

    public void deactivate(int id) {
        ModelObject o = myModelObjectMap.get(id);
        ModelTurtle turtle = (ModelTurtle)o;
        turtle.deactivate();
    }

}
