/*
    This program reads pseudocode lines from a given text file and executes it

    Example input file "example.txt":

    print 5

    x = 4
    y =35 +  78- x
    print x+    78 -23

    # This is a comment
    print y
    y = y + 1 # Changing the variable
    print y

    (end of file)

    Executing the program with the example file should have the following result:

    5
    59
    109
    110

 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class AKTKi {

    public static void main(String[] args) throws IOException {

        // Map for variables
        Map<String, Integer> variables = new HashMap<>();

        // Reading in lines from input file and executing them
        List<String> lines = readAndCleanLines(args[0]);

        for (String line : lines){
            executeLine(line,variables);
        }

    }

    /**
     * Reads the lines from a file one by one and cleans them of
     * whitespace and comments.
     * @param filename - File to read
     * @return - Returns a clean list of lines (without whitespaces/ comments)
     */
    public static List<String> readAndCleanLines(String filename) throws IOException{
        List<String> dirtyLines = Files.readAllLines(Paths.get(filename));
        List<String> cleanLines = new ArrayList<>();

        for (String line : dirtyLines){
            String trimmedLine = line.trim().split("#")[0].replaceAll("\\s","");
            if (!trimmedLine.isEmpty()){
                cleanLines.add(trimmedLine);
            }
        }

        return cleanLines;
    }

    /**
     * Executes the given line.
     * Line could assign a value to a variable or print something out
     * @param line - Input line
     * @param variables - Map with currently set variables
     */
    public static void executeLine(String line, Map<String, Integer> variables){

        if (line.length() >= 5){ // Printing out
            if (line.substring(0,5).equals("print")){
                System.out.println(calculateLine(line.substring(5),variables));
                return;
            }
        }
        if (line.contains("=")){ // Assigning a value to a variable
            String[] sides = line.split("=");
            String key = sides[0];
            if (key.length() > 1){
                throw new UnsupportedOperationException("Variable name too long!");
            }

            int value = calculateLine(sides[1],variables);
            variables.put(key,value);
        } else {
            throw new UnsupportedOperationException("Syntax error!");
        }

    }

    /**
     * Calculates the value of an equation. Supports addition and subtraction
     * @param line - Line to calculate
     * @param variables - List of set variables
     * @return - returns the value
     */
    public static int calculateLine(String line, Map<String, Integer> variables){

        String[] sides;

        // If the line is a single number, returns it
        if (isNumeric(line)){
            return Integer.parseInt(line);
        }

        // Addition
        if (line.contains("+")){
            sides = line.split("\\+", 2);

            String leftSide = sides[0];
            String rightSideDirty = sides[1];
            String[] rightSideSplit = rightSideDirty.split("(?=[-+])", 2);
            String rightSideClean = rightSideSplit[0];

            int answer = calculateLine(leftSide, variables) + calculateLine(rightSideClean,variables);

            if (rightSideSplit.length > 1){
                return calculateLine((Integer.toString(answer) + rightSideSplit[1]), variables);
            }

            return calculateLine(sides[0], variables) + calculateLine(sides[1], variables) ;

        }
        if (line.contains("-")){ // Subtraction
            sides = line.split("\\-", 2);

            String leftSide = sides[0];
            String rightSideDirty = sides[1];
            String[] rightSideSplit = rightSideDirty.split("(?=[-+])", 2);
            String rightSideClean = rightSideSplit[0];

            int answer = calculateLine(leftSide, variables) - calculateLine(rightSideClean,variables);

            if (rightSideSplit.length > 1){
                return calculateLine((Integer.toString(answer) + rightSideSplit[1]), variables);
            }

            return calculateLine(sides[0], variables) - calculateLine(sides[1], variables) ;
        }

        return variables.get(line);

    }

    /**
     * Checks if given string is a number
     * @param str - String to check
     * @return - True, if a string is a number
     */
    public static boolean isNumeric(String str)
    {
        try
        {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }

}
