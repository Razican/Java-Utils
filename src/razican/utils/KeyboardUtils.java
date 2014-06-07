package razican.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Utilities for Keyboard input
 * 
 * @author Razican (Iban Eguia)
 */
public final class KeyboardUtils {

	private static BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));

	private KeyboardUtils() {
	}

	/**
	 * Read an integer from console
	 * 
	 * @return The read integer
	 * @throws IOException
	 *             If an IO error occurs
	 */
	public static int readInt() throws IOException {
		return Integer.parseInt(buffer.readLine());
	}

	/**
	 * Reads a double from console
	 * 
	 * @return the read double
	 * @throws IOException
	 *             If an IO error occurs
	 */
	public static double readDouble() throws IOException {
		return Double.parseDouble(buffer.readLine());
	}

	/**
	 * Reads a float from console
	 * 
	 * @return the read float
	 * @throws IOException
	 *             If an IO error occurs
	 */
	public static float readFloat() throws IOException {
		return Float.parseFloat(buffer.readLine());
	}

	/**
	 * Reads a long from console
	 * 
	 * @return the read long
	 * @throws IOException
	 *             If an IO error occurs
	 */
	public static long readLong() throws IOException {
		return Long.parseLong(buffer.readLine());
	}

	/**
	 * Reads a short from console
	 * 
	 * @return the read short
	 * @throws IOException
	 *             If an IO error occurs
	 */
	public static short readShort() throws IOException {
		return Short.parseShort(buffer.readLine());
	}

	/**
	 * Reads a byte from console
	 * 
	 * @return the read byte
	 * @throws IOException
	 *             If an IO error occurs
	 */
	public static byte readByte() throws IOException {
		return Byte.parseByte(buffer.readLine());
	}

	/**
	 * Reads a long from console
	 * 
	 * @return the read long
	 * @throws IOException
	 *             If an IO error occurs
	 */
	public static boolean readBoolean() throws IOException {
		return Boolean.parseBoolean(buffer.readLine());
	}

	/**
	 * Reads a long from console
	 * 
	 * @return the read long
	 * @throws IOException
	 *             If an IO error occurs
	 */
	public static char readChar() throws IOException {
		return buffer.readLine().charAt(0);
	}

	/**
	 * Read a string from console
	 * 
	 * @return The read String
	 * @throws IOException
	 *             If an IO error occurs
	 */
	public static String readString() throws IOException {
		return buffer.readLine();
	}
}