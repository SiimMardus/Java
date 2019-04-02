import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class EquationOrder {
    public static void main(String[] args) {

        // Some examples:

        System.out.println("\nTest 1:");
        getOrder("(((a+b)*(c-d*k)+2.5)/(pi*(1-e*(4+(7*e)))))");
        System.out.println("\nTest 2:");
        getOrder("");
        System.out.println("\nTest 3:");
        getOrder("(a*b+(s))");
        System.out.println("\nTest 4:");
        getOrder("(ab)");
        System.out.println("\nTest 5:");
        getOrder("(a*34)+7.4)");
    }

    /**
     * Prints out the operations of given equation in the correct order.
     * @param equation - Equation as a string.
     */
    public static void getOrder(String equation){

        // Clearing the whitespace
        equation = equation.replaceAll("\\s+","");

        // Checking if the equation has the same amount of opening and closing parentheses
        int openingParentheses = equation.length() - equation.replaceAll("\\(","").length();
        int closingParentheses = equation.length() - equation.replaceAll("\\)","").length();
        if ((openingParentheses != closingParentheses) || equation.length() == 0){
            System.out.println("Invalid input!");
            return;
        }

        // Initializing a stack and a list of equations
        Stack<String> stack = new Stack<String>();
        List<String> equations = new ArrayList<>();

        // Looping through the equation until it's empty
        while (equation.length() > 0){

            // Pushing the first character into the stack
            stack.push(equation.substring(0,1));

            // Adding the equation between two parentheses into the equations list
            if (stack.peek().equals(")")){

                stack.pop();
                StringBuilder equationBuilder = new StringBuilder();

                while (!stack.peek().equals("(")){
                    equationBuilder.append(stack.pop());
                }
                stack.pop();

                // Replacing the equation with "S" + index
                equations.add(equationBuilder.reverse().toString());
                stack.push("S");
                stack.push(Integer.toString(equations.size()));
            }

            // Continuing looking at the rest of the equation
            equation = equation.substring(1);
        }

        //Printing the equations
        System.out.println("Order of operations:");
        for (int i = 0; i < equations.size(); i++){
            System.out.println("S"+(i+1)+":="+equations.get(i));
        }
    }
}
