public class Basketball extends Game{

    /**
     * Length of the period for a Basketball game
     */
    private final int periodLength = 12;

    /**
     * Name of the period for a basketball game
     */
    private final String periodName = "Period";

    /**
     * Array of strings that represent the choices given to the user
     */
    private final String[] bbChoices = { "1. " + getHomeTeam() + " 3 pointer",
            "2. " + getHomeTeam() + " 2 pointer",
            "3. " + getHomeTeam() + " 1 pointer",
            "4. " + getAwayTeam() + " 3 pointer",
            "5. " + getAwayTeam() + " 2 pointer",
            "6. " + getAwayTeam() + " 1 pointer",
            "7. End current period"};

    /**
     * ScoringMethod for a three pointer
     */
    ScoringMethod threePoint = new ScoringMethod(3);

    /**
     * ScoringMethod for a two pointer
     */
    ScoringMethod twoPoint = new ScoringMethod(2);

    /**
     * ScoringMethod for a one pointer
     */
    ScoringMethod onePoint = new ScoringMethod(1);

    /**
     * Array of scoring methods passed to Game
     */
    private final ScoringMethod[] bbMethods = {threePoint, twoPoint, onePoint};

    /**
     * Constructor that passes the array of choices and scoring methods to Game and sets the values
     * of the home and away teams
     * @param c_homeTeam Sends the home team name to the Scoreboard class
     * @param c_awayTeam Sends the away team name to the Scoreboard class
     */
    public Basketball(String c_homeTeam, String c_awayTeam) {
        super(c_homeTeam, c_awayTeam);

        for (int i = 0; i < bbChoices.length; i++) {
            setGameChoices(bbChoices, bbChoices.length);
        }

        for (int i = 0; i < bbMethods.length; i++) {
            setScoringMethods(bbMethods, bbMethods.length);
        }
    }

    /**
     * Processes the choice passed in from main and calls the appropriate methods and parameters
     * @param p_choice Choice read in from the user that's passed in to the method
     */
    @Override
    public void processChoice(int p_choice) {
        if (p_choice == 1) {
            addScore(threePoint, 'h');

        } else if (p_choice == 2) {
            addScore(twoPoint, 'h');

        } else if (p_choice == 3) {
            addScore(onePoint, 'h');

        } else if (p_choice == 4) {
            addScore(threePoint, 'a');

        } else if (p_choice == 5) {
            addScore(twoPoint, 'a');

        } else if (p_choice == 6) {
            addScore(onePoint, 'a');

        } else if (p_choice == 7) {
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
        if (getGamePeriod() == 5) {
            return true;
        }
        return false;
    }
}
