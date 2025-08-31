/**
 * The RendererFactory class is responsible for creating instances of renderers
 * for displaying the Tic-Tac-Toe board based on a specified type.

 * Supported renderer types:
 * - "void": Creates a "VoidRenderer",
 * which does not render the board (used for testing or silent runs).
 * - "console": Creates a "ConsoleRenderer",
 * which displays the board in the console.

 * This factory simplifies the process of
 * creating renderers by encapsulating the logic
 * for determining which renderer to instantiate.
 */

public class RendererFactory {

    public static final String VOID_RENDERER = "void";
    public static final String CONSOLE_RENDERER = "console";

    /**
     * Default constructor for the RendererFactory.
     * Initializes a new instance with no specific configuration.
     */
    public RendererFactory() {}

    /**
     * Creates a renderer instance based on the given type identifier.
     * Supported types:
     * - "void": Creates a "VoidRenderer".
     * - "console": Creates a "ConsoleRenderer" with the specified board size.
     *
     * @param type The type of renderer to create (case-insensitive).
     * @param size The size of the board, used by some renderers.
     * @return A Renderer instance corresponding to the specified type,
     *         or "null" if the type is unrecognized.
     */
    public Renderer buildRenderer(String type, int size) {
        switch (type.toLowerCase()) {
            case VOID_RENDERER :
                return new VoidRenderer();
            case CONSOLE_RENDERER:
                return new ConsoleRenderer(size);
            default:
                return null;
        }
    }
}
