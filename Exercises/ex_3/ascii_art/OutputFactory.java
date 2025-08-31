package ascii_art;

import ascii_art.exceptions.OutputFormatException;
import ascii_output.AsciiOutput;
import ascii_output.ConsoleAsciiOutput;
import ascii_output.HtmlAsciiOutput;

/**
 * The OutputFactory class is responsible for creating instances
 * of AsciiOutput implementations.
 * It provides a factory method to generate output methods
 * based on the specified format (HTML or Console).
 * Supported formats include:
 * - HTML: Outputs ASCII art to an HTML file.
 * - Console: Outputs ASCII art directly to the console.
 * This class uses the Factory design pattern to encapsulate
 * the instantiation logic.
 * @author Rotem Israeli, Nadav Benjamin
 */
public class OutputFactory {
	private static final String HTML = "html";
	private static final String OUTPUT_FILE_NAME = "out.html";
	private static final String OUTPUT_FONT = "Courier New";
	private static final String CONSOLE = "console";
	
	private OutputFactory() {}

	/**
	 * Factory method to create an instance of AsciiOutput
	 * based on the specified output format.
	 * @param output The desired output format.
	 *               Supported values are "html" and "console".
	 * @return An instance of AsciiOutput corresponding to
	 * the specified format.
	 * @throws OutputFormatException If the specified format is not supported.
	 */
	public static AsciiOutput getOutputMethod(String output) throws OutputFormatException {
		return switch (output) {
			case HTML -> new HtmlAsciiOutput(OUTPUT_FILE_NAME, OUTPUT_FONT);
			case CONSOLE -> new ConsoleAsciiOutput();
			default -> throw new OutputFormatException();
		};
	}
}
