package com.razican.utils;

/**
 * @author Razican (Iban Eguia)
 */
public class MathUtils {

	/**
	 * Converts an unsigned byte to integer
	 * 
	 * @param b
	 *            - Unsigned byte
	 * @return integer representing the byte. For example, 0xFF would be 255,
	 *         and not -1
	 */
	public static int uByteToInt(final byte b) {
		return b & 0x000000FF;
	}

	/**
	 * Converts an unsigned byte to short
	 * 
	 * @param b
	 *            - Unsigned byte
	 * @return short representing the byte, so for example, 0xFF would be 255,
	 *         and not -1
	 */
	public static short uByteToShort(final byte b) {
		return (short) (b & 0x00FF);
	}

	/**
	 * Creates a short with two bytes
	 * 
	 * @param b1
	 *            - left byte for the short
	 * @param b2
	 *            - right byte for the short
	 * @return Short representing the unsigned number contained by the two
	 *         bytes.
	 */
	public static short twoByteToShort(final byte b1, final byte b2) {
		return (short) ((uByteToShort(b1) << 8) + uByteToShort(b2));
	}

	/**
	 * Converts a byte to hexadecimal
	 * 
	 * @param b
	 *            - The byte to convert
	 * @return String with hexadecimal representation of the byte
	 */
	public static String toHex(final byte b) {
		return String.format("%02X", b);
	}

	/**
	 * Gets the n'th byte of an integer, starting from right
	 * 
	 * @param j
	 *            - The int to convert
	 * @param n
	 *            - The number of the byte. From 0 to 3.
	 * @return The byte in that position
	 */
	public static byte getByte(final int j, final int n) {
		if (n < 4) {
			return (byte) (j >> n * 8);
		} else {
			return 0;
		}
	}
}