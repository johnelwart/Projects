public class ScoringMethod {
    /**
     * Points that each ScoringMethod adds to the total score
     */
    private final int pointsToAdd;

    /**
     * Constructor for the ScoringMethod class
     * @param c_pointsToAdd Sets the private variable passed in from each of the calls in the sport
     *                      classes
     */
    ScoringMethod(int c_pointsToAdd) {
        pointsToAdd = c_pointsToAdd;
    }

    /**
     * Gets the points to add for each ScoringMethod
     * @return Returns the amount of points
     */
    public int getPointsToAdd() {
        return pointsToAdd;
    }
}
