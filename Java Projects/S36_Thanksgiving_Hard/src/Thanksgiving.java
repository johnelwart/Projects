import java.util.ArrayList;
import java.util.Comparator;

public class Thanksgiving {

    /**
     * This method find the maximum enjoyment when given an array of food objects with different volumes and enjoyment values
     * @param foodlist Array of Food objects constructed in the main or test classes that is added to an ArrayList to be
     *                 sorted by volume to enjoyment ratio in ascending order.
     * @param totCapacity Capacity that was defined by the user to limit how much of each food you can use.
     * @return Returns the total enjoyment calculated by the method.
     */
    public static int findMax(Food[] foodlist, int totCapacity) {
        ArrayList<Food> foodList = new ArrayList<>();
        int totEnjoyment = 0;

        for (int i = 0; i < foodlist.length; i++) {
            foodList.add(foodlist[i]);
        }
        Comparator<Food> comparePairs = new ThanksgivingComparator();

        foodList.sort(comparePairs);

        for (int i = 0; i < foodList.size(); i++) {
            while (totCapacity >= foodList.get(i).getVolume()) {
                totCapacity -= foodList.get(i).getVolume();

                totEnjoyment += foodList.get(i).getEnjoyment();

                System.out.println("Food eaten: " + foodList.get(i).getName());
            }
        }

        System.out.println("\nCapacity left: " + totCapacity + "\n\n");

        return totEnjoyment;
    }
}
