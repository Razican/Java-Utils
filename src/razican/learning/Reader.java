package razican.learning;

import java.io.IOException;

import razican.utils.KeyboardUtils;

/**
 * Utilities for reading from keyboard
 * 
 * @author Razican (Iban Eguia)
 */
public final class Reader {

	private Reader() {
	}

	/**
	 * Read an integer from console
	 * 
	 * @return The read integer
	 */
	public static int readInt() {
		try {
			return KeyboardUtils.readInt();
		} catch (final IOException e) {
			System.err.println("An error occured when reading the input");
		}

		return 0;
	}

	/**
	 * Reads a double from console
	 * 
	 * @return the read double
	 */
	public static double readDouble() {
		try {
			return KeyboardUtils.readDouble();
		} catch (final IOException e) {
			System.err.println("An error occured when reading the input");
		}
		return 0;
	}

	/**
	 * Reads a double from console
	 * 
	 * @return the read double
	 */
	public static double readChar() {
		try {
			return KeyboardUtils.readChar();
		} catch (final IOException e) {
			System.err.println("An error occured when reading the input");
		}
		return 0;
	}

	/**
	 * Read a string from console
	 * 
	 * @return The read String
	 */
	public static String readString() {
		try {
			return KeyboardUtils.readString();
		} catch (final IOException e) {
			System.err.println("An error occured when reading the input");
		}
		return null;
	}
}