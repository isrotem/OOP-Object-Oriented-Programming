package ascii_art;

import ascii_art.exceptions.*;
import ascii_output.AsciiOutput;
import ascii_output.ConsoleAsciiOutput;
import image.Image;
import image.ImageManipulator;
import image_char_matching.SubImgCharMatcher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a command-line interface for generating
 * ASCII art from images.
 * The Shell class processes user commands to configure
 * character sets, adjust
 * resolution, and generate ASCII art output.
 * It interacts with various subsystems
 * such as image manipulation, character matching, and output rendering.
 * @author Rotem Israeli, Nadav Benjamin
 * @see SubImgCharMatcher
 * @see ImageManipulator
 * @see AsciiOutput
 */
public class Shell {
	private static final int DEFAULT_RESOLUTION = 2;
	private static final char[] INITIAL_CHARSET = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
	private static final String PRE_INPUT_MESSAGE = ">>> ";
	private static final String EXIT = "exit";
	private static final String CHARS = "chars";
	private static final String ADD = "add";
	private static final String REMOVE = "remove";
	private static final String RES = "res";
	private static final String ROUND = "round";
	private static final String OUTPUT = "output";
	private static final String ASCII_ART = "asciiArt";
	private static final int SINGLE_CHAR = 1;
	private static final int ALL_OR_RANGE = 3;
	private static final int SPACE_LENGTH = 5;
	private static final char MIN_ASCII_VALUE = 32;
	private static final char MAX_ASCII_VALUE = 126;
	private static final String ALL = "all";
	private static final int FIRST_CHAR = 0;
	private static final int MIDDLE_CHAR = 1;
	private static final int LAST_CHAR = 2;
	private static final char RANGE_DASH = '-';
	private static final String SPACE_STRING = "space";
	private static final char SPACE_CHAR = ' ';
	private static final String UP = "up";
	private static final String DOWN = "down";
	private static final int RESOLUTION_FACTOR = 2;
	private static final int MIN_CHAR_COUNT = 2;
	private static final String SET_RESOLUTION_MESSAGE = "Resolution set to ";
	private final SubImgCharMatcher subImgCharMatcher;
	private AsciiOutput outputMethod;
	private int currResolution;
	private int maxResolution;
	private int minResolution;

	/**
	 * Constructs a new Shell with default settings.
	 * Initializes the character matcher, output method, and resolution.
	 */
	public Shell() {
		this.subImgCharMatcher = new SubImgCharMatcher(INITIAL_CHARSET);
		this.outputMethod = new ConsoleAsciiOutput();
		this.currResolution = DEFAULT_RESOLUTION;
	}


	/**
	 * Runs the Shell with a given image file.
	 * Processes user commands in a loop until
	 * the exit command is received.
	 * @param imageName The path to the image file to process.
	 */
	public void run(String imageName) {
		Image paddedImage;
		try {
			paddedImage = ImageManipulator.padImage(new Image(imageName));
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return;
		}
		this.maxResolution = paddedImage.getWidth();
		this.minResolution = Math.max(1, paddedImage.getWidth() / paddedImage.getHeight());
		boolean running = true;
		while (running) {
			System.out.print(PRE_INPUT_MESSAGE);
			String[] input = KeyboardInput.readLine().split(" ");
			try {
				switch (input[0]) {
					case EXIT:
						running = false;
						break;
					case CHARS:
						printChars();
						break;
					case ADD:
						addChars(input.length == 1 ? "" : input[1]);
						break;
					case REMOVE:
						removeChars(input.length == 1 ? "" : input[1]);
						break;
					case RES:
						changeRes(input.length == 1 ? "" : input[1]);
						break;
					case ROUND:
						this.subImgCharMatcher.changeRoundingStrategy(input.length == 1 ? "" : input[1]);
						break;
					case OUTPUT:
						this.outputMethod = OutputFactory.getOutputMethod(input.length == 1 ? "" : input[1]);
						break;
					case ASCII_ART:
						if (this.subImgCharMatcher.getCharSet().size() < MIN_CHAR_COUNT) {
							throw new SmallCharsetException();
						}
						AsciiArtAlgorithm algorithm = new AsciiArtAlgorithm(this.subImgCharMatcher,
								this.currResolution, paddedImage);
						this.outputMethod.out(algorithm.run());
						break;
					default:
						throw new IncorrectCommandException();
				}
			} catch (CustomException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	private void printChars() {
		List<Character> charsList = new ArrayList<>(this.subImgCharMatcher.getCharSet());
		Collections.sort(charsList);
		for (Character character : charsList) {
			System.out.print(character + " ");
		}
		System.out.println();
	}


	/**
	 * Main entry point for running the Shell.
	 * @param args Command-line arguments containing the image file name.
	 */
	public static void main(String[] args) {
		Shell shell = new Shell();
		shell.run(args[0]);
	}
	
	private boolean checkCharInRange(char c) {
		return (c >= MIN_ASCII_VALUE && c <= MAX_ASCII_VALUE);
	}
	
	private void addChars(String charsToAdd) throws AddFormatException {
		switch (charsToAdd.length()) {
			case SINGLE_CHAR:
				if (checkCharInRange(charsToAdd.charAt(FIRST_CHAR))) {
					this.subImgCharMatcher.addChar(charsToAdd.charAt(FIRST_CHAR));
				} else {
					throw new AddFormatException();
				}
				break;
			case ALL_OR_RANGE:
				if (charsToAdd.equals(ALL)) {
					for (char c = MIN_ASCII_VALUE; c <= MAX_ASCII_VALUE; c++) {
						this.subImgCharMatcher.addChar(c);
					}
				} else if (charsToAdd.charAt(MIDDLE_CHAR) == RANGE_DASH &&
								   checkCharInRange(charsToAdd.charAt(FIRST_CHAR)) &&
								   checkCharInRange(charsToAdd.charAt(LAST_CHAR))) {
					char smallerChar = charsToAdd.charAt(FIRST_CHAR) < charsToAdd.charAt(LAST_CHAR) ?
											   charsToAdd.charAt(FIRST_CHAR) : charsToAdd.charAt(LAST_CHAR);
					char biggerChar = charsToAdd.charAt(FIRST_CHAR) < charsToAdd.charAt(LAST_CHAR) ?
											  charsToAdd.charAt(LAST_CHAR) : charsToAdd.charAt(FIRST_CHAR);
					for (char c = smallerChar; c <= biggerChar; c++) {
						this.subImgCharMatcher.addChar(c);
					}
				} else {
					throw new AddFormatException();
				}
				break;
			case SPACE_LENGTH:
				if (charsToAdd.equals(SPACE_STRING)) {
					this.subImgCharMatcher.addChar(SPACE_CHAR);
				} else {
					throw new AddFormatException();
				}
				break;
			default:
				throw new AddFormatException();
		}
	}
	
	private void removeChars(String charsToRemove) throws RemoveFormatException {
		switch (charsToRemove.length()) {
			case SINGLE_CHAR:
				this.subImgCharMatcher.removeChar(charsToRemove.charAt(FIRST_CHAR));
				break;
			case ALL_OR_RANGE:
				if (charsToRemove.equals(ALL)) {
					for (char c = MIN_ASCII_VALUE; c <= MAX_ASCII_VALUE; c++) {
						this.subImgCharMatcher.removeChar(c);
					}
				} else if (charsToRemove.charAt(MIDDLE_CHAR) == RANGE_DASH) {
					char smallerChar = charsToRemove.charAt(FIRST_CHAR) < charsToRemove.charAt(LAST_CHAR) ?
											   charsToRemove.charAt(FIRST_CHAR) :
											   charsToRemove.charAt(LAST_CHAR);
					char biggerChar = charsToRemove.charAt(FIRST_CHAR) < charsToRemove.charAt(LAST_CHAR) ?
											  charsToRemove.charAt(LAST_CHAR) :
											  charsToRemove.charAt(FIRST_CHAR);
					for (char c = smallerChar; c <= biggerChar; c++) {
						this.subImgCharMatcher.removeChar(c);
					}
				} else {
					throw new RemoveFormatException();
				}
				break;
			case SPACE_LENGTH:
				if (charsToRemove.equals(SPACE_STRING)) {
					this.subImgCharMatcher.removeChar(SPACE_CHAR);
				} else {
					throw new RemoveFormatException();
				}
				break;
			default:
				throw new RemoveFormatException();
		}
	}
	
	private void changeRes(String direction) throws CustomException {
		if (direction.equals(UP) && this.currResolution < this.maxResolution) {
			this.currResolution *= RESOLUTION_FACTOR;
		} else if (direction.equals(DOWN) && this.currResolution > this.minResolution) {
			this.currResolution /= RESOLUTION_FACTOR;
		} else if (direction.equals(UP) || direction.equals(DOWN)) {
			throw new ResolutionBoundaryException();
		} else if (!direction.isEmpty()) {
			throw new ResolutionFormatException();
		}
		System.out.println(SET_RESOLUTION_MESSAGE + this.currResolution);
	}
}
