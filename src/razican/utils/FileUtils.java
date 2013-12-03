package razican.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * File utilities
 * 
 * @author Razican (Iban Eguia)
 */
public final class FileUtils {

	private FileUtils()
	{
	}

	/**
	 * Converts the contents of a file to a String
	 * 
	 * @param filename - The name of the file to get as a string
	 * @return String with the contents of the file, null if the file does not
	 *         exist
	 * @throws IOException If an IO error occurs
	 */
	public static String toString(String filename) throws IOException
	{
		return toString(new File(filename));
	}

	/**
	 * Converts the contents of a file to a String
	 * 
	 * @param file - The file to get as a string
	 * @return String with the contents of the file, null if the file does not
	 *         exist
	 * @throws IOException If an IO error occurs
	 */
	public static String toString(File file) throws IOException
	{
		if (file.exists())
		{
			FileInputStream stream = null;
			try
			{
				stream = new FileInputStream(file);
			}
			catch (FileNotFoundException e)
			{}

			FileChannel fc = stream.getChannel();
			MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0,
			fc.size());

			String content = Charset.defaultCharset().decode(bb).toString();

			stream.close();

			return content;
		}
		return null;
	}

	/**
	 * Gets the LineIterator for the given file
	 * 
	 * @param filename - Name of the file
	 * @return a LineIterator of the lines of the document
	 * @throws FileNotFoundException if the file is not found
	 */
	public static LineIterator getLineIterator(String filename)
	throws FileNotFoundException
	{
		return getLineIterator(new File(filename));
	}

	/**
	 * Gets the LineIterator for the given file
	 * 
	 * @param file - The file
	 * @return a LineIterator of the lines of the document
	 * @throws FileNotFoundException if the file is not found
	 */
	public static LineIterator getLineIterator(File file)
	throws FileNotFoundException
	{
		return new LineIterator(new BufferedReader(new FileReader(file)));
	}

	/**
	 * Counts the number of lines in the given file
	 * 
	 * @param filename - The name of the file
	 * @return The number of lines
	 */
	public static int getLines(String filename)
	{
		return getLines(new File(filename));
	}

	/**
	 * Counts the number of lines in the given file
	 * 
	 * @param file - The file
	 * @return The number of lines
	 */
	public static int getLines(File file)
	{
		LineNumberReader reader = null;
		if (file.exists())
		{
			try
			{
				reader = new LineNumberReader(new FileReader(file));
				while ((reader.readLine()) != null)
				{
					;
				}

				return reader.getLineNumber();
			}
			catch (Exception e)
			{
				return - 1;
			}
			finally
			{
				if (reader != null)
				{
					try
					{
						reader.close();
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
				}
			}
		}
		else
		{
			return - 1;
		}
	}
}