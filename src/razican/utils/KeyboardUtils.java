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

    private static BufferedReader buffer = new BufferedReader(
	    new InputStreamReader(System.in));

    private KeyboardUtils() {
    }

    /**
     * Read an integer from console
     * 
     * @return The read integer
     * @throws IOException
     *             If an IO error occurs
     * @throws NumberFormatException
     *             If the number is not actually a number
     */
    public static int readInt() throws NumberFormatException, IOException {
	return Integer.parseInt(buffer.readLine());
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