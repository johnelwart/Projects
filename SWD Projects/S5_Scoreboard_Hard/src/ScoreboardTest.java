import java.util.Scanner;

public class ScoreboardTest {
    /**
     * Method where all of the printing to the screen takes place as well as where the input is read in to
     * the program
     * @param args Standard parameter
     */
    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);
        int choice;
        String m_homeTeam, m_awayTeam;
        Game testGame = null;

        System.out.print("Enter home team: ");
        m_homeTeam = scnr.next();
        System.out.print("Enter away team: ");
        m_awayTeam = scnr.next();

        do {
            System.out.println("\nEnter the type of game: \n1. Football \n2. Basketball \n3. Hockey \n4. Soccer");
            System.out.print("Choice: ");
            choice = scnr.nextInt();
            
            if (choice == 1) {
                testGame = new Football(m_homeTeam, m_awayTeam);
            } else if (choice == 2) {
                testGame = new Basketball(m_homeTeam, m_awayTeam);
            } else if (choice == 3) {
                testGame = new Hockey(m_homeTeam, m_awayTeam);
            } else if (choice == 4) {
                testGame = new Soccer(m_homeTeam, m_awayTeam);
            }
            
        } while (choice < 1 || choice > 4);

        while (!testGame.isOver()) {

            System.out.println("\n" + testGame.getHomeTeam() + " - " + testGame.getHomeScore() + "\n" +
                               testGame.getAwayTeam() + " - " + testGame.getAwayScore() + "\n" +
                               "Current " + testGame.getPeriodName() + ": " + testGame.getGamePeriod() + "\n" );

            do {
                for (int i = 0; i < testGame.getChoicesLength(); i++) {
                    System.out.println(testGame.getChoiceAt(i));
                }

                System.out.print("Choice: ");
                choice = scnr.nextInt();

                testGame.processChoice(choice);

            } while (choice < 1 || choice > testGame.getChoicesLength());
        }

        System.out.println("\nGame Over! \n" +
                           testGame.getHomeTeam() + " - " + testGame.getHomeScore() + "\n" +
                           testGame.getAwayTeam() + " - " + testGame.getAwayScore() + "\n" +
                           "Winner: " + testGame.getWinningTeam());

    }
}