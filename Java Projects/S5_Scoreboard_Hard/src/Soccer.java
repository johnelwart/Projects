public class Soccer extends Game{

    /**
     * Length of the period for a Soccer game
     */
    private final int periodLength = 45;

    /**
     * Name of the period for a Soccer game
     */
    private final String periodName = "Half";

    /**
     * Array of strings that represent the choices given to the user
     */
    private final String[] sChoices = { "1. " + getHomeTeam() + " goal",
            "2. " + getAwayTeam() + " goal",
            "3. End current period" };

    /**
     * ScoringMethod that represents a goal
     */
    ScoringMethod goal = new ScoringMethod(1);

    /**
     * Array of scoring methods passed to Game
     */
    private final ScoringMethod[] sMethods = {goal};

    /**
     * Constructor that passes the array of choices and scoring methods to Game and sets the values
     * of the home and away teams
     * @param c_homeTeam Sends the home team name to the Scoreboard class
     * @param c_awayTeam Sends the away team name to the Scoreboard class
     */
    public Soccer(String c_homeTeam, String c_awayTeam) {
        super(c_homeTeam, c_awayTeam);

        for (int i = 0; i < sChoices.length; i++) {
            setGameChoices(sChoices, sChoices.length);
        }

        for (int i = 0; i < sMethods.length; i++) {
            setScoringMethods(sMethods, sMethods.length);
        }
    }

    /**
     * Processes the choice passed in from main and calls the appropriate methods and parameters
     * @param p_choice Choice read in from the user that's passed in to the method
     */
    @Override
    public void processChoice(int p_choice) {
        if (p_choice == 1) {
            addScore(goal, 'h');

        } else if (p_choice == 2) {
            addScore(goal, 'a');

        } else if (p_choice == 3) {
            endPeriod();
            isOver();
        }
    }

    /**
     * Gets the period length
     * @return Returns the period length of this particular sport
     */
    @Override
    public int getPeriodLength() {
        return periodLength;
    }

    /**
     * Gets the period name
     * @return Returns the period name for this particular sport
     */
    @Override
    public String getPeriodName() {
        return periodName;
    }

    /**
     * Checks if the game is over and returns true if it is to stop the while loop in main
     * @return Returns true if the game is over and false if it is not
     */
    @Override
    public boolean isOver() {
        if (getGamePeriod() == 3) {
            return true;
        }
        return false;
    }
}
