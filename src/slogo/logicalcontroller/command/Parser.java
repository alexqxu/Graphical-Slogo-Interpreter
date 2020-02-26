package slogo.logicalcontroller.command;

import java.util.*;
import java.io.*;
import java.lang.reflect.*;
import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

public class Parser {

    private String lang;
    private Map<String, String> commandArray;
    private Stack<String> commands;
    private Stack<String> values;
    private ArrayList<Command> commandObjs;
    private ResourceBundle resources;
    private HashMap<String, String> type1;
    private HashSet<String> type2;

    public Parser(String language) throws IOException {
        this.lang = language;
        commandArray = new HashMap<String, String>();
        commands = new Stack<String>();
        values = new Stack<String>();
        commandObjs = new ArrayList<Command>();
        FileInputStream fis = new FileInputStream("resources/languages/"+this.lang+".properties");
        resources = new PropertyResourceBundle(fis);
        genCommandArray();

    }

    public ArrayList<Command> getCommands(){
        return this.commandObjs;
    }

    public void parse(List<String> lines) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, ScriptException {
        for (String line : lines) {
            int numVals = 0;
            int numCommands = 0;
            int compoundVal = 0;
            if (line.trim().length() > 0) {
                String[] splited = line.split("\\s+");
                checkMath(splited);
                boolean prev = false;
                for(String s: splited){
                    if(!hasValue(s)){
                        commands.push(s);
                        prev = true;
                        numCommands++;
                    }
                    else{
                        values.push(s);
                        numVals++;
                    }
                }

            }

            if(numCommands>numVals){
                compoundVal = numCommands-numVals;
            }
            System.out.println(compoundVal);
            unravel(compoundVal);
        }

    }

    public double checkMath(String[] splited) throws ScriptException {
        System.out.println(Arrays.toString(splited));
        String op = retMath(splited);
        String[] operation = op.split("\\s+");
        type1 = new HashMap<String, String>(){{
            put("sum", "+");
            put("difference", "-");
            put("product", "*");
            put("quotient", "/");
            put("remainder", "%");
            put("minus", "~");
        }};
        type2 = new HashSet<String>(Arrays.asList("random","sin","cos","tan","atan","log","pow","pi"));

        for(int i = 0; i<operation.length; i++){
            if((type1.keySet()).contains(operation[i])){
                String temp = operation[i];
                operation[i] = operation[i+1];
                operation[i+1] = type1.get(operation[i]);
                i+=2;
            }
            else if(type2.contains(operation[i])){
                operation[i] = "Math."+operation[i];
                operation[i+1] = "(" + operation[i+1] + ")";
                i+=1;
            }
        }

        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");
        System.out.println(String.join("", operation));
        double ret = (double)engine.eval(String.join("", operation));
        return ret;
    }

    public String retMath(String[] splited){

        HashSet mathTypes = new HashSet<String>(Arrays.asList("random","sin","cos","tan","atan","log","pow","pi","sum", "+","difference", "-","product","*","quotient","/","remainder", "%","minus","~"));

        String math = "";
        for(String s: splited){
            if(mathTypes.contains(s) || s.matches(".*\\d.*")){
                math+=s + " ";
            }
        }

        math = math.substring(0, math.length()-1);
        return math;

    }

    public void unravel(int cv) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        String theVal = null;
        int comVal = cv;
        while(!commands.isEmpty() && !values.isEmpty()){
            Class cl = Class.forName("slogo.logicalcontroller.command."+commandArray.get(commands.pop()));
            Constructor con = cl.getConstructor(String.class);
            theVal = values.pop();
            Object obj = con.newInstance(theVal);
            commandObjs.add((Command) obj);
            System.out.println((Command)obj);
        }
        while(!commands.isEmpty() && comVal>0){
            Class cl = Class.forName("slogo.logicalcontroller.command."+commandArray.get(commands.pop()));
            Constructor con = cl.getConstructor(String.class);
            Object obj = con.newInstance(theVal);
            commandObjs.add((Command) obj);
            comVal--;
        }
    }

    private boolean hasValue(String val){
        char[] chars = val.toCharArray();
        for(char c: chars){
            if(Character.isDigit(c)){
                return true;
            }
        }
        return false;
    }

    public String getSymbol(String text){
        final String ERROR = "NO MATCH";
        for(String s:commandArray.keySet()){
            if(isMatch(s, commandArray.get(s))){
                return commandArray.get(s);
            }
        }
        return ERROR;
    }

    public boolean isMatch(String text, String regex){
        return regex.matches(text);
    }

    private void genCommandArray() {
        for(String key: Collections.list(resources.getKeys())){
            String regex = resources.getString(key);
            if(regex.indexOf("|") != -1){
                commandArray.put(regex.substring(0, regex.indexOf("|")), key);
                commandArray.put(regex.substring(regex.indexOf("|")+1), key);
            }
            else{
                commandArray.put(regex, key);
            }
        }
    }

    public String getLang(){
        return this.lang;
    }

    public static void main (String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, ScriptException {
        Parser p = new Parser("English");
        List<String> test = new ArrayList<String>();
        test.add("fd fd fd tan 50");
        p.parse(test);
        System.out.println(p.getLang());
        ArrayList<Command> testt = p.getCommands();
        for(Command c: testt){
            System.out.println(testt);
        }





    }
}
