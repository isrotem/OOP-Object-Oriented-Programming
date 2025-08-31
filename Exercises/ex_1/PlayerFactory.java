/**
 * The PlayerFactory class is responsible for creating
 * instances of different types of players
 * for a Tic-Tac-Toe game based on a given type identifier.
 * Supported player types:
 * - "whatever": Creates an instance of "WhateverPlayer".
 * - "human": Creates an instance of "HumanPlayer".
 * - "clever": Creates an instance of "CleverPlayer".
 * - "genius": Creates an instance of "GeniusPlayer".
 * This factory simplifies the process of creating players by encapsulating
 * the logic for determining which player to instantiate.
 */
public class PlayerFactory {

    public static final String HUMAN_PLAYER = "human";
    public static final String WHATEVER_PLAYER = "whatever";
    public static final String CLEVER_PLAYER = "clever";
    public static final String GENIUS_PLAYER = "genius";

    /**
     * Default constructor for the PlayerFactory.
     * Initializes a new instance with no specific configuration.
     */
    public PlayerFactory() {}

    /**
     * Creates a player instance based on the given type identifier.
     * Supported types:
     * - "human": Creates a "HumanPlayer".
     * - "whatever": Creates a "WhateverPlayer".
     * - "clever": Creates a "CleverPlayer".
     * - "genius": Creates a "GeniusPlayer".
     *
     * @param type The type of player to create (case-insensitive).
     * @return A Player instance corresponding to the specified type,
     *         or "null" if the type is unrecognized.
     */
    public Player buildPlayer(String type) {
        switch (type.toLowerCase()) {
            case HUMAN_PLAYER:
                return new HumanPlayer();
            case WHATEVER_PLAYER:
                return new WhateverPlayer();
            case CLEVER_PLAYER:
                return new CleverPlayer();
            case GENIUS_PLAYER:
                return new GeniusPlayer();
            default:
                return null;
        }
    }
}

