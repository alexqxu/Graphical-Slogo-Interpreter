package slogo.logicalcontroller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * Purpose of this class is to convert a file into a property resource bundle, effectively a static method
 * @auther Max Smith
 */
public interface BundleInterface {

    static ResourceBundle createResourceBundle(String filename) throws IOException {
        return new PropertyResourceBundle(new FileInputStream(filename));
    }
}
