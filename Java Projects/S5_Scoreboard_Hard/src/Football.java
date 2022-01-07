public class Football extends Game {

    /**
     * Length of the period for a Football game
     */
    private final int periodLength = 15;

    /**
     * Name of the period for a Football game
     */
    private final String periodName = "Quarter";

    /**
     * Array of strings that represent the choices given to the user
     */
    private final String[] fbChoices = { "1. " + getHomeTeam() + " touchdown",
            "2. " + getHomeTeam() + " extra point",
            "3. " + getHomeTeam() + " 2 point conversion",
            "4. " + getHomeTeam() + " field goal",
            "5. " + getAwayTeam() + " touchdown",
            "6. " + getAwayTeam() + " extra point",
            "7. " + getAwayTeam() + " 2 point conversion",
            "8. " + getAwayTeam() + " field goal",
            "9. End current period"};

    /**
     * ScoringMethod that represents a touchdown
     */
    ScoringMethod touchdown = new ScoringMethod(6);

    /**
     * ScoringMethod that represents an extra point
     */
    ScoringMethod extraPoint = new ScoringMethod(1);

    /**
     * ScoringMethod that represents a two point conversion
     */
    ScoringMethod twoPtConv = new ScoringMethod(2);

    /**
     * ScoringMethod that represents a field goal
     */
    ScoringMethod fieldGoal = new ScoringMethod(3);

    /**
     * Array of scoring methods passed to Game
     */
    private final ScoringMethod[] fbMethods = {touchdown, extraPoint, twoPtConv, fieldGoal};

    /**
     * Constructor that passes the array of choices and scoring methods to Game and sets the values
     * of the home and away teams
     * @param c_homeTeam Sends the home team name to the Scoreboard class
     * @param c_awayTeam Sends the away team name to the Scoreboard class
     */
    public Football(String c_homeTeam, String c_awayTeam) {
        super(c_homeTeam, c_awayTeam);

        for (int i = 0; i < fbChoices.length; i++) {
            setGameChoices(fbChoices, fbChoices.length);
        }

        for (int i = 0; i < fbMethods.length; i++) {
            setScoringMethods(fbMethods, fbMethods.length);
        }
    }

    /**
     * Processes the choice passed in from main and calls the appropriate methods and parameters
     * @param p_choice Choice read in from the user that's passed in to the method
     */
    @Override
    public void processChoice(int p_choice) {
        if (p_choice == 1) {
            addScore(touchdown, 'h');

        } else if (p_choice == 2) {
            addScore(extraPoint, 'h');

        } else if (p_choice == 3) {
            addScore(twoPtConv, 'h');

        } else if (p_choice == 4) {
            addScore(fieldGoal, 'h');

        } else if (p_choice == 5) {
            addScore(touchdown, 'a');

        } else if (p_choice == 6) {
            addScore(extraPoint, 'a');

        } else if (p_choice == 7) {
            addScore(twoPtConv, 'a');

        } else if (p_choice == 8) {
            addScore(fieldGoal, 'a');

        } else if (p_choice == 9) {
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
