package com.razican.utils;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * String Utilities
 * 
 * @author Razican (Iban Eguia)
 */
public final class StringUtils {

	private StringUtils() {
	}

	/**
	 * Converts data to an hexadecimal string
	 * 
	 * @param data
	 *            - Data to convert to hexadecimal
	 * @return String in hexadecimal
	 */
	private static String toHex(final byte[] data) {
		final StringBuffer buf = new StringBuffer();
		for (final byte element : data) {
			int halfbyte = (element >>> 4) & 0x0F;
			int two_halfs = 0;
			do {
				if ((0 <= halfbyte) && (halfbyte <= 9)) {
					buf.append((char) ('0' + halfbyte));
				} else {
					buf.append((char) ('a' + (halfbyte - 10)));
				}
				halfbyte = element & 0x0F;
			} while (two_halfs++ < 1);
		}
		return buf.toString();
	}

	/**
	 * Converts a character array to a byte array
	 * 
	 * @param charArray
	 *            - Character array in ASCII
	 * @return Byte array representing chars
	 */
	public static byte[] toByte(final char[] charArray) {
		final byte[] byteArray = new byte[charArray.length << 1];

		int bpos = 0;
		for (final char element : charArray) {
			if ((byte) ((element & 0xFF00) >> 8) != 0) {
				byteArray[bpos] = (byte) ((element & 0xFF00) >> 8);
				bpos++;
			}

			byteArray[bpos] = (byte) (element & 0x00FF);
			bpos++;
		}

		return Arrays.copyOf(byteArray, bpos);
	}

	/**
	 * Converts a character array to a byte array
	 * 
	 * @param charArray
	 *            - Character array
	 * @param enc
	 *            - Codification of the characters
	 * @return Byte array representing characters, password safe
	 * @throws CharacterCodingException
	 *             Error when coding
	 */
	public static byte[] toByte(final char[] charArray, final String enc) throws CharacterCodingException {
		final Charset charset = Charset.forName(enc);
		final CharsetEncoder encoder = charset.newEncoder();

		final CharBuffer charB = CharBuffer.wrap(charArray);
		final ByteBuffer byteB = encoder.encode(charB);
		
		byte[] ba1 = new byte[byteB.limit()];
		byteB.get(ba1);

		return ba1;
	}

	/**
	 * Converts a byte array to a char array
	 * 
	 * @param byteArray		Assert.assertEquals(StringUtils.firstToUpper("123abc"), "123abc");
	 *            - Byte array containing characters Shouldn't be used with
	 *            passwords, it uses intermediate strings
	 * @param enc
	 *            - Encoding to use
	 * @return Character array
	 * @throws UnsupportedEncodingException
	 *             if the encoding is not supported
	 */
	public static char[] toChar(final byte[] byteArray, final String enc) throws UnsupportedEncodingException {
		return (new String(byteArray, enc)).toCharArray();
	}

	/**
	 * Creates the Sha1 representation of a given string
	 * 
	 * @param str
	 *            - Text to crypt in Sha1
	 * @return Generated Sha1 hash
	 */
	public static String sha1(final String str) {
		byte[] sha1hash = new byte[40];
		try {
			final MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(str.getBytes("UTF-8"), 0, str.length());
			sha1hash = md.digest();
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return toHex(sha1hash);
	}

	/**
	 * Generates the Sha1 representation for the empty string
	 * 
	 * @return empty string coded in sha1
	 */
	public static String sha1() {
		return sha1("");
	}

	/**
	 * Generates the Sha1 representation of a character array
	 * 
	 * @param charArray
	 *            - character array to crypt in sha1
	 * @return Generated sha1 hash
	 */
	public static String sha1(final char[] charArray) {
		byte[] sha1hash = new byte[40];

		try {
			final MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(toByte(charArray, "UTF-8"), 0, charArray.length);
			sha1hash = md.digest();
		} catch (NoSuchAlgorithmException | CharacterCodingException e) {
			e.printStackTrace();
		}

		return toHex(sha1hash);
	}

	/**
	 * Converts the first letter of a given string to capital
	 * 
	 * @param s
	 *            - String to convert
	 * @return The string with the first letter in capital
	 */
	public static String firstToUpper(final String s) {
		if (s.length() > 0)
			return s.substring(0, 1).toUpperCase() + s.substring(1);
		else
			return s;
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException, CharacterCodingException {
		
		char[] result1 = {'h', 'j', '6', '¬'};
		System.out.println(Arrays.toString(StringUtils.toByte(result1, "UTF-8")));
		
		
		byte[] test1 = {104, 106, 54, -62, -84};
		System.out.println(Arrays.toString(StringUtils.toChar(test1, "UTF-8")));
	}
}