package razican.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Iterator;

/**
 * @author Razican (Iban Eguia)
 */
public class LineIterator implements Iterator<String> {

    private final BufferedReader reader;
    private String lastLine;

    /**
     * @param reader
     *            Reader for the iterator
     */
    public LineIterator(final BufferedReader reader) {
	this.reader = reader;
    }

    @Override
    public boolean hasNext() {
	try {
	    return (lastLine = reader.readLine()) != null;
	} catch (final IOException e) {
	    e.printStackTrace();
	}

	return false;
    }

    @Override
    public String next() {
	String line = null;
	if (lastLine != null) {
	    line = lastLine;
	} else {
	    try {
		line = reader.readLine();
	    } catch (final IOException e) {
		e.printStackTrace();
	    }
	}
	lastLine = null;

	return line;
    }

    @Override
    public void remove() {
    }
}