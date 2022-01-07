import java.util.Scanner;

public class Change {

    /**
     * calcTotalAmount calculates the total amount the customer paid using the number of bills passed in to the
     * method.
     * @param bill20 Number of twenty dollar bills the customer paid with.
     * @param bill10 Number of ten dollar bills the customer paid with
     * @param bill5 Number of five dollar bills the customer paid with.
     * @param bill1 Number of one dollar bills the customer paid with.
     * @param coin25 Number of quarters the customer paid with.
     * @param coin10 Number of dimes the customer paid with.
     * @param coin5 Number of nickels the customer paid with.
     * @param coin1 Number of pennies the customer paid with.
     * @return The total amount paid by the customer.
     */

    public static double calcTotalAmt(int bill20, int bill10, int bill5, int bill1, int coin25, int coin10, int coin5, int coin1) {
        double amountPaid = 0;

        amountPaid += (bill20 * 20);
        amountPaid += (bill10 * 10);
        amountPaid += (bill5 * 5);
        amountPaid += bill1;
        amountPaid += (coin25 * 0.25);
        amountPaid += (coin10 * 0.1);
        amountPaid += (coin5 * 0.05);
        amountPaid += (coin1 * 0.01);

        return amountPaid;
    }

    /**
     * calcChange converts the USD change passed into the function to Euros and then figures out the amount of each
     * Euro bill/coin to give back to the customer. To account for the double having more decimal places than we need,
     * I multiply the value by 100 and then cast it to an int to get rid of those decimal places.
     * @param usdChange Change in USD that was calculated in main.
     * @return The array of each Euro bill/coin that the customer is going to receive.
     */
    public static int[] calcChange(double usdChange){
        double euroChange;
        euroChange = usdChange * 0.8463;

        euroChange = euroChange * 100;
        euroChange = (int) euroChange;

        System.out.println("Euro change: €" + String.format("%.2f", euroChange/100)); //Test

        int[] billList;
        billList = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0};

        while (euroChange >= 2000) {
            euroChange -= 2000;
            billList[0]++;
        }

        while (euroChange >= 1000) {
            euroChange -= 1000;
            billList[1]++;
        }

        while (euroChange >= 500) {
            euroChange -= 500;
            billList[2]++;
        }

        while (euroChange >= 100) {
            euroChange -= 100;
            billList[3]++;
        }

        while (euroChange >= 50) {
            euroChange -= 50;
            billList[4]++;
        }

        while (euroChange >= 20) {
            euroChange -= 20;
            billList[5]++;
        }

        while (euroChange >= 10) {
            euroChange -= 10;
            billList[6]++;
        }

        while (euroChange >= 5) {
            euroChange -= 5;
            billList[7]++;
        }

        while (euroChange >= 1) {
               euroChange -= 1;
            billList[8]++;
        }

        return billList;
    }

    /**
     * The first user input is the price of the item the customer intends to buy. The second is the number of each
     * bill/coin the customer gives to the cashier. After calling calcTotalAmt and storing the value in amountPaidWith,
     * main calculates the change that is to be given by subtracting the item price from the amount paid with and
     * passes that into calcChange.
     * @param args Standard parameter
     */
    public static void main(String[] args){
        Scanner scnr = new Scanner(System.in);
        double itemPrice, amountPaidWith, changeGiven;
        int twenties, tens, fives, singles, quarters, dimes, nickels, pennies;
        int[] list;

        System.out.print("Please enter the item price: $");
        itemPrice = scnr.nextDouble();

        do {
            System.out.println("Enter the number of each bills the customer paid with");
            System.out.println("Use the format: $20 $10 $5 $1 25₵ 10₵ 5₵ 1₵");

            twenties = scnr.nextInt();
            tens = scnr.nextInt();
            fives = scnr.nextInt();
            singles = scnr.nextInt();
            quarters = scnr.nextInt();
            dimes = scnr.nextInt();
            nickels = scnr.nextInt();
            pennies = scnr.nextInt();

            System.out.println("\nBills paid with: ");
            System.out.println(twenties + " $20 bills\n" + tens + " $10 bills\n" + fives + " $5 bills\n" +
                    singles + " $1 bills\n" + quarters + " quarters\n" + dimes + " dimes\n" +
                    nickels + " nickels\n" + pennies + " pennies\n"); //Test

            amountPaidWith = Change.calcTotalAmt(twenties, tens, fives, singles, quarters, dimes, nickels, pennies);
            System.out.println("Total amount paid: $" + String.format("%.2f", amountPaidWith)); //Test

            if (amountPaidWith < itemPrice){
                System.out.println("\nNot enough to cover total!\n");
            }

        } while (amountPaidWith < itemPrice);

        changeGiven = amountPaidWith - itemPrice;
        System.out.println("Total Change: $" + String.format("%.2f", changeGiven)); //Test

        if (changeGiven != 0.0) {

            list = calcChange(changeGiven);

            System.out.println("\nChange to be given: ");
            System.out.println(list[0] + " €20 bills\n" + list[1] + " €10 bills\n" + list[2] + " €5 bills\n" +
                    list[3] + " €1 bills\n" + list[4] + " €50 coins\n" + list[5] + " €20 coins\n" +
                    list[6] + " €10 coins\n" + list[7] + " €5 coins\n" + list[8] + " €1 coins\n"); //test
        }
        else{
            System.out.println("\nNo change needs to be given!");
        }
    }
}