import java.util.Scanner;

public class PostFixTest {

    /**
     * Prints to the run window and reads the lines from the user to pass to the processLine function.
     * @param args Standard argument
     */
    public static void main(String[] args) {
        Scanner scnr;
        StringBuffer lineBuffer;

        scnr = new Scanner(System.in);
        System.out.println("Postfix Calculator");
        System.out.println("Separate the inputs by spaces");
        System.out.println("Enter \"end\" to end program" +
                ", \"inspect\" to see the state of the stack, and \"clear\" to clear the stack");
        System.out.println("Operations: +, -, *, /, ^, %");

        PostFixEvaluator calc = new PostFixEvaluator();

        while (scnr.hasNextLine()) {
            try {
                String line = scnr.nextLine();
                lineBuffer = new StringBuffer(line);

                System.out.println("Processing line: " + line);
                calc.processLine(lineBuffer);

                String test = String.valueOf(lineBuffer);
                if (test.equals("inspect") || test.equals("clear")) {
                    System.out.println("Done!");
                } else {
                    System.out.println("Result is " + calc.push(calc.pop()));
                }
            } catch (Exception e) {
                System.out.println("Exception: " + e);
            }
        }
    }
}
