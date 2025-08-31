/**
 * The Tournament class orchestrates a series of
 * Tic-Tac-Toe games between two players
 * and determines the overall winner based on
 * the results of multiple rounds.
 * Features:
 * - Configurable number of rounds, board size, and win streak condition.
 * - Flexible player and renderer creation using factory classes.
 * - Tracks and displays the results,
 * including the number of wins for each player and ties.
 */
public class Tournament {

    private static final int POS_1 = 0;
    private static final int POS_2 = 1;
    private static final int POS_3 = 2;
    private static final int POS_4 = 3;
    private static final int POS_5 = 4;
    private static final int POS_6 = 5;
    private static final int MODULO_CONSTANT = 2;

    private final int rounds;
    private final Renderer renderer;
    private final Player player1;
    private final Player player2;

    /**
     * Constructor for creating a tournament.
     *
     * @param rounds   The number of rounds to be played in the tournament.
     * @param renderer The renderer for displaying the game board.
     * @param player1  Player 1 participating in the tournament.
     * @param player2  Player 2 participating in the tournament.
     */
    public Tournament(int rounds, Renderer renderer, Player player1, Player player2) {
        this.rounds = rounds;
        this.renderer = renderer;
        this.player1 = player1;
        this.player2 = player2;
    }

    /**
     * Main entry point for the tournament.
     * Parses command-line arguments, creates the necessary components,
     * and starts the tournament.
     * Command-line arguments:
     * - [0] Number of game rounds.
     * - [1] Board size.
     * - [2] Win streak condition.
     * - [3] Renderer type.
     * - [4] Player 1 type.
     * - [5] Player 2 type.
     *
     * @param args Command-line arguments for configuring the tournament.
     */
    public static void main(String[] args) {
        int gameRounds = Integer.parseInt(args[POS_1]);
        int size = Integer.parseInt(args[POS_2]);
        int winStreak =  Integer.parseInt(args[POS_3]);
        String renderInput = args[POS_4];
        String Player1 =  args[POS_5];
        String Player2 = args[POS_6];

        PlayerFactory playerCreator = new PlayerFactory();
        RendererFactory rendererCreator = new RendererFactory();
        Renderer renderer = rendererCreator.buildRenderer(renderInput, size);
        Player playerName1 = playerCreator.buildPlayer(Player1);
        Player playerName2 = playerCreator.buildPlayer(Player2);
        Tournament tournament = new Tournament(gameRounds, renderer, playerName1, playerName2);

        tournament.playTournament(size, winStreak, Player1, Player2);
    }

    /**
     * Executes the tournament by playing the specified number of rounds.
     * Tracks the number of wins for each player and ties, and prints the results.
     *
     * @param size       The size of the board for each game.
     * @param winStreak  The number of consecutive marks required to win a game.
     * @param playerName1 The name or type of Player 1.
     * @param playerName2 The name or type of Player 2.
     */
    public void playTournament(int size, int winStreak,
                               String playerName1, String playerName2) {
        int roundNum =  0;
        int [] winsCounter = {0, 0};
        Player [] players = {player1, player2};
        int tiesCounter = 0;

        while(roundNum < this.rounds) {
            int currIndex = roundNum % MODULO_CONSTANT;
            Game game = new Game(players[currIndex], players[1 - currIndex],
                    size, winStreak, renderer);
            Mark resultMark = game.run();
            if (resultMark == Mark.BLANK) {
                tiesCounter++;
            }
            else if(resultMark == Mark.X) {
                winsCounter[currIndex]++;
            }
            else{
                winsCounter[1-currIndex]++;
            }
            roundNum++;
        }
        printResult(playerName1, playerName2,winsCounter, tiesCounter);
    }

    /**
     * Prints the results of the tournament, including the number of wins for
     * each player and the number of ties.
     *
     * @param playerName1 The name or type of Player 1.
     * @param playerName2 The name or type of Player 2.
     * @param winsCounter Array tracking the number of wins for Player 1 and Player 2.
     * @param tiesCounter The number of games that ended in a tie.
     */
    private void printResult(String playerName1, String playerName2, int[] winsCounter, int tiesCounter) {
        System.out.println("######### Results #########");
        System.out.println("Player 1 " + playerName1 + " won: " + winsCounter[0] + " rounds");
        System.out.println("Player 2 " + playerName2 + " won: " + winsCounter[1] + " rounds");
        System.out.println("Ties: " + tiesCounter);
    }
}
