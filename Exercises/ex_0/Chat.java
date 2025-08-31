import java.util.Scanner;

/**
 * The Chat class is a simple console-based chatbot simulator that uses multiple instances
 * of the ChatterBot class to simulate a conversation between two bots.
 */
class Chat {
    // Number of bot instances to be used in the conversation
    static final int BOTS_NUM = 2;
    // Initial statement to start the conversation
    static final String CURR_STATEMENT = "say orange";

    public static void main(String[] args){

        // Define replies for illegal requests for the first bot
        String[] repliesToIlegalRequests_first = new String[]{
                "what?!!",
                "what " + ChatterBot.PLACEHOLDER_FOR_ILLEGAL_REQUEST,
                "say I should say " + ChatterBot.PLACEHOLDER_FOR_ILLEGAL_REQUEST,
                "say what? " + ChatterBot.PLACEHOLDER_FOR_ILLEGAL_REQUEST + "? what is it "
                + ChatterBot.PLACEHOLDER_FOR_ILLEGAL_REQUEST + "?"
        };

        // Define replies for legal requests for the second bot
        String[] repliesTolegalRequests_first = new String[]{
                "You want me to say " + ChatterBot.PLACEHOLDER_FOR_REQUESTED_PHRASE +
                ", do you? alright: " + ChatterBot.PLACEHOLDER_FOR_REQUESTED_PHRASE,
                "say " + ChatterBot.PLACEHOLDER_FOR_REQUESTED_PHRASE + "? okay: " +
                ChatterBot.PLACEHOLDER_FOR_REQUESTED_PHRASE,
                "O.K, I say: " + ChatterBot.PLACEHOLDER_FOR_REQUESTED_PHRASE
        };

        // Define replies for illegal requests for the first bot
        String[] repliesToIlegalRequests_second = new String[]{
                "whaaat",
                "say say " + ChatterBot.PLACEHOLDER_FOR_ILLEGAL_REQUEST,
                "WHAT?!, " + ChatterBot.PLACEHOLDER_FOR_ILLEGAL_REQUEST
        };

        // Define replies for legal requests for the first bot
        String[] repliesTolegalRequests_second = new String[]{
                "You want me to say " + ChatterBot.PLACEHOLDER_FOR_REQUESTED_PHRASE +
                ", do you? alright: " + ChatterBot.PLACEHOLDER_FOR_REQUESTED_PHRASE,
                "I got you, " + ChatterBot.PLACEHOLDER_FOR_REQUESTED_PHRASE,
                "Fine, I say: " + ChatterBot.PLACEHOLDER_FOR_REQUESTED_PHRASE

        };

        // Initialize the array of ChatterBot objects
        ChatterBot[] bots = new ChatterBot[BOTS_NUM];

        bots[0] = new ChatterBot("Sammy", repliesTolegalRequests_first, repliesToIlegalRequests_first);
        bots[1] = new ChatterBot("Ruthy", repliesTolegalRequests_second, repliesToIlegalRequests_second);

        String statement = CURR_STATEMENT;

        System.out.println(statement);

        Scanner scanner = new Scanner(System.in);

        // Infinite loop to simulate conversation between bots
        for(int i = 0;; i++){
            statement = bots[(i % (bots.length))].replyTo(statement);
            String currName = bots[(i % (bots.length))].getName();
            System.out.print(currName + ": " + statement);
            scanner.nextLine();
        }
    }
}