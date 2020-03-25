package slogo.logicalcontroller;

import java.util.*;

/**
 * Utility class to extract information from the control flow commands
 */

public final class ControlFlowExtractor {
    public static final String INVALID_INSTANTIATION_ERROR = "Instantiating utility class.";

    private ControlFlowExtractor(){
        throw new AssertionError(INVALID_INSTANTIATION_ERROR);
    }

    /**
     * Method to return the contents within a single line set of brackets
     * Assumes that the line to be analyzed has a controlflow command with a set of opening and closing brackets
     * @param rawCommands List of raw commands from the user
     * @param line Line number of the controlflow command object
     * @return Returns a list of the arguments within the brackets
     */
    public static List<String> getBracketArguments(List<String> rawCommands, int line) {
        String myLine = rawCommands.get(line);
        List<String> ret = Arrays.asList(myLine.substring(myLine.indexOf("[") + 1, myLine.indexOf("]")).trim().split("\\s"));
        return ret;
    }

    /**
     * Method that given the raw commands list and the index of the controlflow object, returns the updated internal contents of the command
     * Assumes that all parameters are up to date for the iteration the program is on
     * @param rawCommands List of raw commands taken from the user
     * @param lineIndex Line Index of thr control flow object
     * @param bracIndex Index of thr opening bracket of the control flow object
     * @return Returns the updated body with the internal contents of the command
     */
    public static List<String> initControlFlow(List<String> rawCommands, int lineIndex, int bracIndex){
        int[] retIndexes = retEndIndex(rawCommands, lineIndex, bracIndex);
        int endLineIndex = retIndexes[0];
        int endBracIndex = retIndexes[1];
        List<String> body;
        if(lineIndex==endLineIndex){
            body = singleLineBody(bracIndex, endBracIndex, lineIndex, rawCommands);
        }
        else{
            body = getInternalContents(lineIndex, endLineIndex, rawCommands);
        }
        return body;

    }

    /**
     * Method to return the line number of the last bracket of the controlflow command, so that the contents can be extracted
     * Method assumes that the starting line of the command is accurate and the index of the opening bracket is included
     * @param rawCommands List of string representing the raw commands typed by the user
     * @param lineIndex Line number of the controlflow command of interest
     * @param bracIndex Bracket position of the opening bracket
     * @return
     */
    public static int getLineLastBrac(List<String> rawCommands, int lineIndex, int bracIndex){
        int[] retIndexes = retEndIndex(rawCommands, lineIndex, bracIndex);
        return retIndexes[0];
    }

    private static List<String> singleLineBody(int bracIndex, int endBracIndex, int lineIndex, List<String> rawCommands) {
        List<String> retLine = new ArrayList<String>();
        String line = rawCommands.get(lineIndex);
        String[] args = Arrays.copyOfRange(line.split("\\s+"), bracIndex+1, endBracIndex);
        retLine.add(String.join(" ", args));
        return retLine;
    }

    private static int[] retEndIndex(List<String> rawCommands, int lineIndex, int bracIndex){
        Stack<String> bracStack = new Stack<String>();

        for(int i = lineIndex; i<rawCommands.size(); i++){
            String line = rawCommands.get(i);
            String[] lineElems = line.split("\\s+");
            int eb = 0;
            for(int j = 0; j<lineElems.length; j++){
                if(lineElems[j].equals("[")){
                    bracStack.push("[");
                }else if(lineElems[j].equals("]")){
                    bracStack.pop();
                    eb = j;
                }
            }
            if(bracStack.isEmpty()){
                return new int[]{i, eb};
            }
        }

        return new int[0];

    }

    private static List<String> getInternalContents(int start, int end, List<String> rawCommands){
        List<String> retLines = new ArrayList<String>();
        for(int i = start+1; i<end; i++){
            retLines.add(rawCommands.get(i));
        }
        return retLines;
    }
}
