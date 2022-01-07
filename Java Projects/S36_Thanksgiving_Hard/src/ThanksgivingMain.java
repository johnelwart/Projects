import java.util.Scanner;

public class ThanksgivingMain {

    /**
     * Allows the user to define values for each of the Food items and also the stomach capacity. Then constructs an array
     * of those foods and passes them in to the findMax method in Thanksgiving.java
     * @param args Standard parameter
     */
    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);
        int capacity;
        double volume = 0.0, enjoyment = 0.0;

        System.out.print("Enter the volume and enjoyment values for turkey separated by a space: ");
        volume = scnr.nextDouble();
        enjoyment = scnr.nextDouble();
        Food turkey = new Food(volume, enjoyment, "Turkey");

        System.out.print("Enter the volume and enjoyment values for pie separated by a space: ");
        volume = scnr.nextDouble();
        enjoyment = scnr.nextDouble();
        Food pie = new Food(volume, enjoyment, "Pie");

        System.out.print("Enter the volume and enjoyment values for potatoes separated by a space: ");
        volume = scnr.nextDouble();
        enjoyment = scnr.nextDouble();
        Food potatoes = new Food(volume, enjoyment, "Potatoes");

        System.out.print("Enter the volume and enjoyment values for gravy separated by a space: ");
        volume = scnr.nextDouble();
        enjoyment = scnr.nextDouble();
        Food gravy = new Food(volume, enjoyment, "Gravy");

        System.out.print("Enter the volume and enjoyment values for stuffing separated by a space: ");
        volume = scnr.nextDouble();
        enjoyment = scnr.nextDouble();
        Food stuffing = new Food(volume, enjoyment, "Stuffing");

        System.out.print("Enter the volume and enjoyment values for cranberries separated by a space: ");
        volume = scnr.nextDouble();
        enjoyment = scnr.nextDouble();
        Food cranberries = new Food(volume, enjoyment, "Cranberries");

        System.out.print("Enter the volume and enjoyment values for casserole separated by a space: ");
        volume = scnr.nextDouble();
        enjoyment = scnr.nextDouble();
        Food casserole = new Food(volume, enjoyment, "Casserole");

        Food[] foodArray = {turkey, pie, potatoes, gravy, stuffing, cranberries, casserole};

        System.out.print("Enter your stomach capacity: ");
        capacity = scnr.nextInt();

        System.out.println("\nTotal enjoyment: " + Thanksgiving.findMax(foodArray, capacity));
    }
}
