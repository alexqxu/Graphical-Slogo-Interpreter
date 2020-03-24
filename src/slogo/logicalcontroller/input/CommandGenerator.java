package slogo.logicalcontroller.input;

import slogo.exceptions.InvalidCommandException;
import slogo.logicalcontroller.command.Command;

import java.lang.reflect.Constructor;
import java.util.*;

/**
 * Purpose of this class is to aid the Parser in generating command objects in a static way
 * @auther Max Smith
 */
public final class CommandGenerator {

    public static final String COLON = ":";
    public static final String INVALID_INSTANTIATION_ERROR = "Instantiating utility class.";

    private CommandGenerator(){
        throw new AssertionError(INVALID_INSTANTIATION_ERROR);
    }

    static Command createControlCommand(String superclass, String command, List<List<String>> args) {
        try {
            // System.out.println("Class name path: " + createCommandPath(superclass, command));
            // System.out.println("Argument 2: " + args.get(1).get(0));
            Class clazz = Class.forName(createCommandPath(superclass, command));
            Constructor ctor = clazz.getConstructor(List.class);
            // System.out.println("Checkpoint importanttt");
            return (Command) ctor.newInstance(args);
        } catch (Exception e) {
            throw new InvalidCommandException("Could not create control flow command");
        }
    }

    static String createCommandPath(String superclass, String command) {
        final String SLOGO_COMMAND = "slogo.logicalcontroller.command.";
        String path = String.format("%s%s.%s", SLOGO_COMMAND, superclass, command);
        return path;
    }

    static Command createCommand(String superclass, String command, List<String> arguments) {
        // System.out.printf("trying to createCommand from superclass: %s and command: %s\n", superclass, command);
        try {
            Class clazz = Class.forName(createCommandPath(superclass, command));
            Constructor ctor = clazz.getConstructor(List.class);
            // System.out.printf("clazz: %s \nconstructor: %s \nargs: ", clazz.toString(), ctor.toString());
            // for (String s: arguments) {System.out.println("s");}
            return (Command) ctor.newInstance(arguments);
        } catch (Exception e) {
            throw new InvalidCommandException("Could not create command");
        }
    }

    static boolean isValidCommand(String s, ResourceBundle bundle) {
        if (s.contains(COLON)) {
            // System.out.println("cound colon in command " + s);
            // System.out.println("returning true");
            return true;
        }
        for(String key: Collections.list(bundle.getKeys())){
            String regex = bundle.getString(key);
            String[] regexElems = regex.split("\\|");
            if(regexElems[0].equals(s) || ((regexElems.length >1) && regexElems[1].equals(s))){
                return true;
            }
        }
        return false;
    }

    /**
     * Use properties file to translate command Key into superclass for reflections
     * @param command
     * @return
     */
    static String getCommandSuperclass(String command, ResourceBundle bundle) {
        Enumeration<String> resourceEnumeration = bundle.getKeys();
        String key;
        while (resourceEnumeration.hasMoreElements()) {
            key = resourceEnumeration.nextElement();
            if (key.equals(command)) {return bundle.getString(key);}
        }
        return "";
    }

}
