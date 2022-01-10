import java.util.Scanner;
import java.util.Stack;
import java.lang.Math;


public class PostFixEvaluator {
    private static Stack<Integer> stack;
    private static final String[] operations = {"+", "-", "*", "/", "^", "%"};
    private static int x = 0, y = 0;

    /**
     * Constructor for PostFix, creates a new stack of integers for the calculator.
     */
    public PostFixEvaluator() {
        stack = new Stack<>(){};
    }

    /**
     * Pushes the value passed into the parameter "x" on the stack
     * @param x Value passed in to the enter method to be pushed on the top of the stack
     */
    public void enter(int x) {
        stack.push(x);
    }

    /**
     * Adds the value at the bottom of the stack to the value above it and pushes it back on to
     * the stack.
     */
    public void add() {
        y = stack.pop();
        x = stack.pop();
        stack.push(x + y);
    }

    /**
     * Subtracts the value just above the bottom of the stack from the bottom of the stack and pushes it
     * back on to the stack.
     */
    public void subtract() {
        y = stack.pop();
        x = stack.pop();
        stack.push(x - y);
    }

    /**
     * Multiplies the value at the bottom of the stack to the value above it and pushes it back on to
     * the stack.
     */
    public void multiply() {
        y = stack.pop();
        x = stack.pop();
        stack.push(x * y);
    }

    /**
     * Uses the value on the bottom of the stack as the numerator and the value just above it on the stack as
     * the denominator and divides the two, then pushes it back on to the stack.
     */
    public void divide() {
        y = stack.pop();
        x = stack.pop();
        stack.push(x / y);
    }

    /**
     * The value on the bottom of the stack is the base and the value just above is the exponent, the
     * result is pushed back on to the stack.
     */
    public void power() {
        y = stack.pop();
        x = stack.pop();
        stack.push((int) Math.pow(x, y));
    }

    /**
     * Finds the remainder, if there is one, of two values when divided. The result is pushed back on the
     * stack.
     */
    public void remainder() {
        y = stack.pop();
        x = stack.pop();
        stack.push(x % y);
    }

    /**
     * Pops the value on the top of the stack if the stack is larger than 0.
     * @return The popped value.
     */
    public int pop() {
        if (stack.size() != 0) {
            return stack.pop();
        } else {
            return 0;
        }
    }

    /**
     * Pushes a value passed in from an outside class onto the stack
     * @param value Value passed in from an outside class.
     */
    public int push(int value) {
        stack.push(value);
        return value;
    }

    /**
     * Using a for loop, all the elements in the stack are cleared. A new calculation can begin for the
     * next line.
     */
    public void clear() {
        for (int i = 0; i < stack.size() - 1; i++) {
            stack.pop();
        }
    }

    /**
     * Processes the line entered from the run window or executes a command from the list provided
     * @param line Line entered in the run window
     */
    public void processLine(StringBuffer line) {
        Scanner linescan = new Scanner(String.valueOf(line));

        for (int i = 0; i < stack.size() - 1; i++){
            enter(stack.pop());
        }

        while (linescan.hasNext() && line.length() != 0) {
            if (linescan.hasNextInt() ) {
                stack.push(linescan.nextInt());
            } else {
                String operation = linescan.next();

                if (operation.equals("end")) {
                    System.exit(0);
                } else if (operation.equals("inspect")) {
                    System.out.println("Stack: " + stack);
                } else if (operation.equals("clear")){
                    clear();
                }

                for (int i = 0; i < operations.length; i++) {
                    if (operation.equals(operations[i])) {
                        switch (operations[i]) {
                            case "+":
                                add();
                                break;
                            case "-":
                                subtract();
                                break;
                            case "*":
                                multiply();
                                break;
                            case "/":
                                divide();
                                break;
                            case "^":
                                power();
                                break;
                            case "%":
                                remainder();
                                break;
                            default:
                                System.out.println("Invalid operation!");
                        }
                    }
                }
            }
        }
    }
}
