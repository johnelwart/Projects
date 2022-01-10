public class Scoreboard {
    /**
     * Holds the home team name chosen by the user
     */
    private String homeTeam;

    /**
     * Holds the away team name chosen by the user
     */
    private String awayTeam;

    /**
     * Keeps track of the score for the home team throughout the game
     */
    private int homeScore = 0;

    /**
     * Keeps track of the score for the away team throughout the game
     */
    private int awayScore = 0;

    /**
     * Constructor sets the home and away team names for the game chosen in main.
     * @param c_homeTeam Name of the home team.
     * @param c_awayTeam Name of the away team.
     */
    public Scoreboard(String c_homeTeam, String c_awayTeam) {
        homeTeam = c_homeTeam;
        awayTeam = c_awayTeam;
    }

    /**
     * Getter method for the home team variable.
     * @return Returns the home team name.
     */
    public String getHomeTeam() {
        return homeTeam;
    }

    /**
     * Getter method for the away team variable.
     * @return Returns the away team name.
     */
    public String getAwayTeam() {
        return awayTeam;
    }

    /**
     * Getter method for the home score variable.
     * @return Returns the score of the home team.
     */
    public int getHomeScore() {
        return homeScore;
    }

    /**
     * Getter method for the away score variable.
     * @return Returns the score of the away team.
     */
    public int getAwayScore() {
        return awayScore;
    }

    /**
     * Setter for homeTeam variable.
     * @param s_homeTeam String passed in to set the private value in the class.
     */
    public void setHomeTeam(String s_homeTeam) {
        homeTeam = s_homeTeam;
    }

    /**
     * Setter for awayTeam variable.
     * @param s_awayTeam String passed in to set the private value in the class.
     */
    public void setAwayTeam(String s_awayTeam) {
        awayTeam = s_awayTeam;
    }

    /**
     * Setter for homeScore variable.
     * @param s_homeScore integer passed in to set the private value in the class.
     */
    public void setHomeScore(int s_homeScore) {
        homeScore = s_homeScore;
    }

    /**
     * Setter for awayScore variable.
     * @param s_awayScore integer passed in to set the private value in the class.
     */
    public void setAwayScore(int s_awayScore) {
        awayScore = s_awayScore;
    }


    /**
     * Determines the winning team based of the score when called.
     * @return Returns either the home team or away team or tie game if the scores are equal.
     */
    public String getWinningTeam() {
        if (homeScore > awayScore) {
            return homeTeam;
        } else if (homeScore < awayScore) {
            return awayTeam;
        }
        return "Tie Game";
    }

    /**
     * Adds the amount of points passed to the total score using the setScore methods.
     * @param input ScoringMethod passed in. The method calls the getPointsToAdd method from the ScoringMethod class
     *              to determine the amount of points to add.
     * @param homeAway Character passed in that indicates if the points go towards the home score or the away score.
     */
    public void addScore(ScoringMethod input, char homeAway) {
        if (homeAway == 'h') {
            setHomeScore(homeScore + input.getPointsToAdd());
        } else if (homeAway == 'a') {
            setAwayScore(awayScore + input.getPointsToAdd());
        }
    }
}