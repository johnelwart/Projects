public abstract class Game extends Scoreboard{

    /**
     * String array that holds the game choices copied from the chosen sport
     */
    private String[] gameChoices;

    /**
     * ScoringMethod array that holds the ScoringMethods from the chosen sport
     */
    private ScoringMethod[] scoringMethods;

    /**
     * Keeps track of the game period starting at period 1
     */
    private int gamePeriod = 1;

    /**
     * Passes the home and away team names to the Scoreboard class
     * @param c_homeTeam Name of the home team defined by the user
     * @param c_awayTeam Name of the away team defined by the user
     */
    Game(String c_homeTeam, String c_awayTeam) {
        super(c_homeTeam, c_awayTeam);
    }

    /**
     * Gets the length of the choices passed in from whichever sport is chosen
     * @return Returns the length of the area
     */
    public int getChoicesLength() {
        return gameChoices.length;
    }

    /**
     * Gets the choice at the i value passed in from the choices passed in from the sport chosen
     * @param i i value passed in from a for loop in main
     * @return Returns the choice at that i value
     */
    public String getChoiceAt(int i) {
        return gameChoices[i];
    }

    /**
     * Sets the gameChoices string array in Game from the one passed in from whichever sport is chosen in main
     * @param input String array passed in from the chosen sport
     * @param length Length of the array to set the new array size
     */
    public void setGameChoices (String[] input, int length) {
        gameChoices = new String[length];

        for (int i = 0; i < gameChoices.length; i++) {
            gameChoices[i] = input[i];
        }
    }

    /**
     * Sets the scoringmethods array in game from the array passed in from the chosen sport
     * @param input Array passed in from the sport class
     * @param length Length of the array to set the new array size
     */
    public void setScoringMethods (ScoringMethod[] input, int length) {
        scoringMethods = new ScoringMethod[length];

        for (int i = 0; i < scoringMethods.length; i++) {
            scoringMethods[i] = input[i];
        }
    }

    /**
     * Gets the current game period
     * @return Returns the game period
     */
    public int getGamePeriod() {
        return gamePeriod;
    }

    /**
     * Ends the current period by adding one to the gamePeriod variable
     */
    public void endPeriod() {
        gamePeriod += 1;
    }

    /**
     * Processes the choice passed in from main
     * @param p_choice Choice read in from the user that's passed in to the method
     */
    public abstract void processChoice(int p_choice);

    /**
     * Gets the period length from the sport chosen
     * @return The period length in minutes
     */
    public abstract int getPeriodLength();

    /**
     * Gets the period name from the sport chosen
     * @return Returns the period name
     */
    public abstract String getPeriodName();

    /**
     * Checks to see if the game is over based on the current period
     * @return Returns a boolean value that is used to keep the while loop going
     */
    public abstract boolean isOver();
}
