package slogo.logicalcontroller.codefilters;

/**
 * This interface defines what is expected of a Filter module/class that implements this interface. In other words,
 * if future filter pre-processing functionality is desired, it MUST implement this interface to ensure correct functionality.
 * @author Alex Xu
 */
public interface filtersInterface {

    /**
     * Returns a processed String.
     * @return
     */
    public String filter();

}
