package bricker.brick_strategies;
import bricker.main.BrickerGameManager;

/**
 * Factory class for creating collision strategies in the Bricker game.
 * Based on a provided option, this factory generates different implementations of the {@code CollisionStrategy} interface.
 * This design allows for dynamic and flexible assignment of collision behaviors during gameplay.
 * Implements a switch-case logic to determine the appropriate strategy.
 * @see CollisionStrategy
 * @see BrickerGameManager
 */
public class CollisionStrategiesFactory {

    /**
     * Creates a collision strategy based on the specified option.
     * The strategy is dynamically determined by the {@code randomizedOption} parameter.
     * @param randomizedOption An integer representing the type of collision strategy to create.
     *                         - 0: Creates a {@code PuckCollisionStrategy}.
     *                         - 1: Creates an {@code ExtraPaddleCollisionStrategy}.
     *                         - 2: Creates a {@code TurboBallCollisionStrategy}.
     *                         - 3: Creates a {@code BonusLifeCollisionStrategy}.
     * @param manager The game manager used to manage the game's state and objects.
     * @return An instance of a class implementing {@code CollisionStrategy}, or {@code null} if the option is invalid.
     */
    public CollisionStrategy createCollisionStrategy(int randomizedOption, BrickerGameManager manager) {

        switch (randomizedOption) {
            case 0:
                return new PuckCollisionStrategy(manager);
            case 1:
                return new ExtraPaddleCollisionStrategy(manager);
            case 2:
                return new TurboBallCollisionStrategy(manager);
            case 3:
                return new BonusLifeCollisionStrategy(manager);
            default:
                return null;
        }
    }
}
