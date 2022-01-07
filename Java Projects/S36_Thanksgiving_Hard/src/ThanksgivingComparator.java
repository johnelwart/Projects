import java.util.Comparator;

public class ThanksgivingComparator implements Comparator<Food> {

    /**
     * Used to compare the Food objects in an ArrayList by their volume to enjoyment ratio. That ratio is calculated in
     * the beginning of the method.
     * @param o1 First Food object to be compared
     * @param o2 Second Food object to be compared
     * @return Returns a values that indicates how the two objects compare.
     */
    @Override
    public int compare(Food o1, Food o2) {
        double ratio1 = o1.getVolume() / o1.getEnjoyment();
        double ratio2 = o2.getVolume() / o2.getEnjoyment();

        if (ratio1 > ratio2) {
            return 1;
        } else if (ratio1 < ratio2) {
            return -1;
        } else {
            return 0;
        }
    }
}
