public class printInfo {
    /**
     * Prints the info from each truck using values passed in from the DeliveryTruck class
     * @param input Multidimensional string array passed in containing the orders on that truck
     * @param truckNum Truck number the values are coming from
     */
    public static synchronized void toString(String[][] input, String truckNum) {
        if (truckNum.equals("1")) {
            System.out.println("----==== Deliveries from Truck 1 ====----");

            for (int i = 0; i < input.length; i++) {
                if (input[i][0] != null) {
                    System.out.print(input[i][4] + "\n");
                    System.out.print(input[i][0] + "\n");
                    System.out.print(input[i][1] + ", " + input[i][2] + ", " + input[i][3] + "\n");
                    System.out.print(input[i][5] + ", " + input[i][6] + "\n");
                    System.out.print(input[i][7] + ", " + input[i][8] + ", " + input[i][9] + "\n");

                    System.out.print("\n");
                } else {
                    System.out.print("Truck " + truckNum + " is done delivering. Shipping center 1\n");
                    break;
                }
            }

        } else if (truckNum.equals("2")) {
            System.out.println("----==== Deliveries from Truck 2 ====----");

            for (int i = 0; i < input.length; i++) {
                if (input[i][0] != null) {
                    System.out.print(input[i][4] + "\n");
                    System.out.print(input[i][0] + "\n");
                    System.out.print(input[i][1] + ", " + input[i][2] + ", " + input[i][3] + "\n");
                    System.out.print(input[i][5] + ", " + input[i][6] + "\n");
                    System.out.print(input[i][7] + ", " + input[i][8] + ", " + input[i][9] + "\n");

                    System.out.print("\n");
                } else {
                    System.out.print("Truck " + truckNum + " is done delivering. Shipping center 1\n");
                    break;
                }
            }

        } else if (truckNum.equals("3")) {
            System.out.println("----==== Deliveries from Truck 3 ====----");

            for (int i = 0; i < input.length; i++) {
                if (input[i][0] != null) {
                    System.out.print(input[i][4] + "\n");
                    System.out.print(input[i][0] + "\n");
                    System.out.print(input[i][1] + ", " + input[i][2] + ", " + input[i][3] + "\n");
                    System.out.print(input[i][5] + ", " + input[i][6] + "\n");
                    System.out.print(input[i][7] + ", " + input[i][8] + ", " + input[i][9] + "\n");

                    System.out.print("\n");
                } else {
                    System.out.print("Truck " + truckNum + " is done delivering. Shipping center 2\n");
                    break;
                }
            }

        } else if (truckNum.equals("4")) {
            System.out.println("----==== Deliveries from Truck 4 ====----");

            for (int i = 0; i < input.length; i++) {
                if (input[i][0] != null) {
                    System.out.print(input[i][4] + "\n");
                    System.out.print(input[i][0] + "\n");
                    System.out.print(input[i][1] + ", " + input[i][2] + ", " + input[i][3] + "\n");
                    System.out.print(input[i][5] + ", " + input[i][6] + "\n");
                    System.out.print(input[i][7] + ", " + input[i][8] + ", " + input[i][9] + "\n");

                    System.out.print("\n");
                } else {
                    System.out.print("Truck " + truckNum + " is done delivering. Shipping center 2\n");
                    break;
                }
            }
        }
    }
}
