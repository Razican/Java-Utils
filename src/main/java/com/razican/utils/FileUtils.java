package com.razican.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.ObjectOutputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * File utilities
 *
 * @author Razican (Iban Eguia)
 * @author Jordan Aranda Tejada
 */
public final class FileUtils {

	private FileUtils() {
	}

	/**
	 * Converts the contents of a file to a String
	 *
	 * @param filename
	 *            - The name of the file to get as a string
	 * @return String with the contents of the file, null if the file does not
	 *         exist
	 * @throws IOException
	 *             If an IO error occurs
	 */
	public static String toString(final String filename) throws IOException {
		return toString(new File(filename));
	}

	/**
	 * Converts the contents of a file to a String
	 *
	 * @param file
	 *            - The file to get as a string
	 * @return String with the contents of the file, null if the file does not
	 *         exist
	 * @throws IOException
	 *             If an IO error occurs
	 */
	public static String toString(final File file) throws IOException {
		if (file.exists()) {
			FileInputStream stream = null;
			try {
				stream = new FileInputStream(file);
			} catch (final FileNotFoundException e) {
			}

			String content = null;

			if (stream != null) {
				final FileChannel fc = stream.getChannel();
				final MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());

				content = Charset.defaultCharset().decode(bb).toString();
				stream.close();
			}

			return content;
		}
		return null;
	}

	/**
	 * Gets the LineIterator for the given file
	 *
	 * @param filename
	 *            - Name of the file
	 * @return a LineIterator of the lines of the document
	 * @throws FileNotFoundException
	 *             if the file is not found
	 */
	public static LineIterator getLineIterator(final String filename) throws FileNotFoundException {
		return getLineIterator(new File(filename));
	}

	/**
	 * Gets the LineIterator for the given file
	 *
	 * @param file
	 *            - The file
	 * @return a LineIterator of the lines of the document
	 * @throws FileNotFoundException
	 *             if the file is not found
	 */
	public static LineIterator getLineIterator(final File file) throws FileNotFoundException {
		return new LineIterator(new BufferedReader(new FileReader(file)));
	}

	/**
	 * Counts the number of lines in the given file
	 *
	 * @param filename
	 *            - The name of the file
	 * @return The number of lines
	 */
	public static int getLines(final String filename) {
		return getLines(new File(filename));
	}

	/**
	 * Counts the number of lines in the given file
	 *
	 * @param file
	 *            - The file
	 * @return The number of lines
	 */
	public static int getLines(final File file) {
		LineNumberReader reader = null;
		if (file.exists()) {
			try {
				reader = new LineNumberReader(new FileReader(file));
				while ((reader.readLine()) != null) {
					;
				}

				return reader.getLineNumber();
			} catch (final Exception e) {
				return -1;
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (final IOException e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			return -1;
		}
	}

	/**
	 * Opens a file with a file chooser
	 *
	 * @param description
	 *            - The description of the file
	 * @param extensions
	 *            - The extension of the file. Could be multiple extensions.
	 * @return The file loaded
	 */
	public static File open(final String description, final String... extensions) {
		final JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileNameExtensionFilter(description, extensions));
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		String path = "";
		File file = null;
		try {
			if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				path = fileChooser.getSelectedFile().getAbsolutePath();
				file = new File(path);
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return file;
	}

	/**
	 * Saves a file with a file chooser
	 *
	 * @param content
	 *            - The object to save
	 * @param description
	 *            - The description of the file
	 * @param extension
	 *            - The extension of the file
	 * @param file
	 *            - The file in where to save
	 * @return The path of the saved file
	 */
	public static String saveObject(final Object content, final String description,
			final String extension, final File file) {
		final JFileChooser fileChooser = new JFileChooser();
		final FileNameExtensionFilter langFilter =
				new FileNameExtensionFilter(description, extension);
		fileChooser.setFileFilter(langFilter);
		fileChooser.setSelectedFile(file);
		String path = "";
		try {
			if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				path = fileChooser.getSelectedFile().getAbsolutePath();
				if (!path.endsWith("." + extension)) {
					path += "." + extension;
				}
				final File file2 = new File(path);
				if ((file2.exists() && JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(null,
						"The file exists, do you want to replace it?", "File Exists",
						JOptionPane.YES_NO_OPTION))
						|| !file2.exists()) {
					final ObjectOutputStream oos =
							new ObjectOutputStream(new FileOutputStream(path));
					oos.writeObject(content);
					oos.close();
				}
			}
			return path;
		} catch (final Exception e) {
			e.printStackTrace();
			return path;
		}
	}

	/**
	 * Saves a file with a file chooser
	 *
	 * @param content
	 *            - The object to save
	 * @param description
	 *            - The description of the file
	 * @param extension
	 *            - The extension of the file
	 *
	 * @return The path of the saved file
	 */
	public static String saveByteArray(final ByteArrayOutputStream content,
			final String description, final String extension) {

		final JFileChooser fileChooser = new JFileChooser();
		final FileNameExtensionFilter langFilter =
				new FileNameExtensionFilter(description, extension);
		fileChooser.setFileFilter(langFilter);
		String path = "";
		try {
			if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				path = fileChooser.getSelectedFile().getAbsolutePath();
				if (!path.endsWith("." + extension)) {
					path += "." + extension;
				}
				final File file2 = new File(path);
				if ((file2.exists() && JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(null,
						"The file exists, do you want to replace it?", "File Exists",
						JOptionPane.YES_NO_OPTION))
						|| !file2.exists()) {
					final FileOutputStream fos = new FileOutputStream(path);
					fos.write(content.toByteArray());
					fos.close();
				}
			}
			return path;
		} catch (final Exception e) {
			e.printStackTrace();
			return path;
		}
	}
}