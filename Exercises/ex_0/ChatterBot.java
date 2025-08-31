import java.util.*;

/**
 * Base file for the ChatterBot exercise.
 * The bot's replyTo method receives a statement.
 * If it starts with the constant REQUEST_PREFIX, the bot returns
 * whatever is after this prefix. Otherwise, it returns one of
 * a few possible replies as supplied to it via its constructor.
 * In this case, it may also include the statement after
 * the selected reply (coin toss).
 * @author Dan Nirel
 */
class ChatterBot {
	static final String REQUEST_PREFIX = "say ";

	static final String REQUEST_PREFIX_ECHO = "echo ";

	static final String PLACEHOLDER_FOR_REQUESTED_PHRASE = "<phrase>";

	static final String PLACEHOLDER_FOR_ILLEGAL_REQUEST = "<request>";

	String name;

	Random rand = new Random();

	String[] legalRequestsReplies;

	String[] illegalRequestsReplies;

	/**
	* Constructor for ChatterBot.
	* Initializes the bot's name, legal request replies, and illegal request replies.
	*
	* @param name the name of the bot
	* @param legalRequestsReplies array of possible replies to legal requests
	* @param illegalRequestsReplies array of possible replies to illegal requests
	 */
	ChatterBot(String name, String[] legalRequestsReplies, String[] illegalRequestsReplies) {
		this.name = name;
		this.illegalRequestsReplies = new String[illegalRequestsReplies.length];
		for(int i = 0 ; i < illegalRequestsReplies.length ; i = i+1) {
			this.illegalRequestsReplies[i] = illegalRequestsReplies[i];
		}

		this.legalRequestsReplies = new String[legalRequestsReplies.length];
		for(int i = 0 ; i < legalRequestsReplies.length ; i = i+1) {
			this.legalRequestsReplies[i] = legalRequestsReplies[i];
		}
	}

	/**
	* Replies to a given statement based on its prefix.
	* If the statement starts with a legal prefix, it processes it as a legal request.
	* Otherwise, it handles it as an illegal request.
	*
	* @param statement the input statement to respond to
	* @return the bot's reply
	*/

	String replyTo(String statement) {
		if((statement.startsWith(REQUEST_PREFIX_ECHO)) || (statement.startsWith(REQUEST_PREFIX))) {
			return replyToLegalRequest(statement);
		}
		return replyToIllegalRequest(statement);
		}

	/**
	* Handles legal requests.
	* If the statement starts with "echo", it removes the prefix and returns the rest.
	* Otherwise, it replaces the placeholder with the requested phrase.
	*
	* @param statement the input legal statement to respond to
	* @return the bot's reply to the legal request
	*/
	String replyToLegalRequest(String statement){
		if(statement.startsWith(REQUEST_PREFIX_ECHO)){
			return statement.replaceFirst(REQUEST_PREFIX_ECHO, "");
		}
		String phrase = statement.replaceFirst(REQUEST_PREFIX, "");
		return replacePlaceholderInARandomPattern(legalRequestsReplies,
				PLACEHOLDER_FOR_REQUESTED_PHRASE, phrase);
	}

	/**
	* Handles illegal requests.
	* Chooses a random reply template and replaces the placeholder with the illegal statement.
	*
	* @param statement the input illegal statement to respond to
	* @return the bot's reply to the illegal request
	*/
	String replyToIllegalRequest(String statement) {
		return replacePlaceholderInARandomPattern(illegalRequestsReplies,
				PLACEHOLDER_FOR_ILLEGAL_REQUEST, statement);
	}

	/**
	* Replaces a placeholder in a random pattern template with a given replacement text.
	*
	* @param patterns array of templates to choose from
	* @param placeholder the placeholder string to replace
	* @param replacement the string to replace the placeholder with
	* @return the completed reply with placeholder replaced
	*/
	String replacePlaceholderInARandomPattern(String[] patterns,
											  String placeholder,
											  String replacement) {
		int randomIndex = rand.nextInt(patterns.length);
		String replyTemplate = patterns[randomIndex];
		String reply = replyTemplate.replaceAll(placeholder, replacement);
		return reply;
	    }

	/**
	* Returns the name of the bot.
	*
	* @return the name of the bot
	*/
	String getName() {
		return this.name;
	}
}
