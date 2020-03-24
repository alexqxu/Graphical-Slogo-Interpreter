package slogo.logicalcontroller.codefilters;

import slogo.exceptions.InvalidFilterException;
import slogo.exceptions.ResourceBundleException;
import slogo.logicalcontroller.BundleInterface;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Utility class that filters the user input before sending it to the parser.
 * final keyword prevents subclassing and improves efficiency.
 * @author Alex Xu
 */
public final class MasterCodeFilterUtility {

    public static final String RESOURCE_BUNDLE_LOCATION = "src/properties/inputFilters.properties";
    public static final String INVALID_INSTANTIATION_ERROR = "Instantiating utility class.";
    public static final String CLASS_PREFIX = "slogo.logicalcontroller.codefilters.";
    public static final String LANGUAGES_PATH = "resources/languages/";
    public static final String PROPERTIES_SUFFIX = ".properties";
    public static final String PROPERTIES_METHOD_NAME = "methodName";

    /**
     * Applies all of the filters, user-defined through a properties file.
     */
    private MasterCodeFilterUtility(){
        throw new AssertionError(INVALID_INSTANTIATION_ERROR);
    }

    /**
     * Static class that returns the filtered result of the input, based on activated filters.
     * @param rawInput
     * @param language
     * @return
     */
    public static String filter(String rawInput, String language){
        String result;
        ResourceBundle filtersApplied;
        ResourceBundle languageBundle;
        try {
            filtersApplied = BundleInterface.createResourceBundle(RESOURCE_BUNDLE_LOCATION);
            languageBundle = BundleInterface.createResourceBundle(LANGUAGES_PATH + language + PROPERTIES_SUFFIX);
        } catch (Exception e) {
            throw new ResourceBundleException();
        }
        try {
            List<String> activeFilters = extractActiveFilters(filtersApplied);
            List<Class> activeFilterClasses = extractOperatingClasses(activeFilters);
            List<FilterSuperclass> filterObjectsList = extractFilterObjects(activeFilterClasses);
            List<Method> methodList = extractOperatingMethods(activeFilterClasses, filtersApplied.getString(PROPERTIES_METHOD_NAME));
            result = performFiltering(filterObjectsList, methodList, rawInput, languageBundle);
            return result;
        }
        catch(ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e){
            throw new InvalidFilterException();
        }
    }

    private static List<FilterSuperclass> extractFilterObjects(List<Class> activeFilters) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        List<FilterSuperclass> filterObjectsList = new ArrayList<>();
        for(Class myClass : activeFilters){
            Constructor filterConstructor = myClass.getConstructor(null);
            Object obj = filterConstructor.newInstance(null);
            FilterSuperclass filterObject = (FilterSuperclass) obj;
            filterObjectsList.add(filterObject);
        }
        return filterObjectsList;
    }

    private static String performFiltering(List<FilterSuperclass> classList, List<Method> methodList, String rawInput, ResourceBundle language) throws InvocationTargetException, IllegalAccessException {
        String result = rawInput;
        for(int i = 0; i<classList.size(); i++){
            Object resultObject = methodList.get(i).invoke(classList.get(i), result, language);
            result = (String) resultObject;
        }
        return result;
    }

    private static List<String> extractActiveFilters(ResourceBundle filterInformation){
        List<String> activeFiltersList = new ArrayList<>();

        Enumeration keys = filterInformation.getKeys();
        while(keys.hasMoreElements()){
            String key = (String) keys.nextElement();
            String value = filterInformation.getString(key);
            boolean activeState = Boolean.parseBoolean(value);
            if(activeState){
                activeFiltersList.add(key);
            }
        }
        return activeFiltersList;
    }

    private static List<Method> extractOperatingMethods(List<Class> classList, String methodKeyWord) throws NoSuchMethodException {
        List<Method> methodList = new ArrayList<>();
        for (Class myClass : classList){
            Method filterMethod = myClass.getMethod(methodKeyWord, String.class, ResourceBundle.class);
            methodList.add(filterMethod);
        }
        return methodList;
    }

    private static List<Class> extractOperatingClasses(List<String> activeFilters) throws ClassNotFoundException {
        List<Class> classList = new ArrayList<Class>();
        for (String className : activeFilters){
            Class classObject = Class.forName(createClassPath(CLASS_PREFIX, className));
            classList.add(classObject);
        }
        return classList;
    }

    private static String createClassPath(String prefix, String className){
        return prefix + className;
    }
}