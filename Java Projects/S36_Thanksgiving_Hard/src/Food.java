public class Food {

    /**
     * Total volume of the stomach the food takes up
     */
    private final double volume;

    /**
     * Enjoyment value of the food item
     */
    private final double enjoyment;

    /**
     * Name of the food item
     */
    private final String name;

    /**
     * Constructor for the Food class
     * @param volume Volume passed in to set as the final volume value
     * @param enjoyment Enjoyment value passed in to set as the final enjoyment
     * @param name Name passed in to set as the final name value
     */
    public Food(double volume, double enjoyment, String name) {
        this.volume = volume;
        this.enjoyment = enjoyment;
        this.name = name;
    }

    /**
     * Getter for the volume variable
     * @return Returns the value of that food items volume
     */
    public double getVolume() {
        return volume;
    }

    /**
     * Getter for the enjoyment variable
     * @return Returns the value of that food items enjoyment
     */
    public double getEnjoyment() {
        return enjoyment;
    }

    /**
     * Getter for the foods name
     * @return Returns the value of that food items name
     */
    public String getName() {
        return name;
    }
}
