package razican.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import razican.utils.FileUtils;
import razican.utils.LineIterator;

/**
 * SQLite 3 Database manager. It requires xerial's SQLite JDBC library:
 * https://bitbucket.org/xerial/sqlite-jdbc It contains a query builder for
 * proper query building functions.
 * 
 * @author Razican (Iban Eguia)
 */
public class SQLite3db {

	private Connection connection;
	private File file;
	private String lastError;
	private final QueryBuilder qb;

	/**
	 * Creates a new connection to a database. By default assumes the file has
	 * the extension "sqlite3", that it has UTF-8 encoding and enables foreign
	 * keys
	 * 
	 * @param dbname
	 *            - The database name
	 * @throws NoDatabaseException
	 *             If the database does not exist
	 */
	public SQLite3db(final String dbname) throws NoDatabaseException {
		this(dbname, "sqlite3", "UTF-8", true);
	}

	/**
	 * Creates a new connection to a database. By default it assumes UTF-8
	 * encoding and it enables foreign keys
	 * 
	 * @param dbname
	 *            - The database name
	 * @param ext
	 *            - The extension of the file
	 * @throws NoDatabaseException
	 *             If the database does not exist
	 */
	public SQLite3db(final String dbname, final String ext) throws NoDatabaseException {
		this(dbname, ext, "UTF-8", true);
	}

	/**
	 * Creates a new connection to a database. By default it enables foreign
	 * keys
	 * 
	 * @param dbname
	 *            - The database name
	 * @param ext
	 *            - The extension of the file
	 * @param encoding
	 *            - The encoding of the database
	 * @throws NoDatabaseException
	 *             If the database does not exist
	 */
	public SQLite3db(final String dbname, final String ext, final String encoding) throws NoDatabaseException {
		this(dbname, ext, encoding, true);
	}

	/**
	 * Creates a new connection to a database. By default it enables foreign
	 * keys
	 * 
	 * @param dbname
	 *            - The database name
	 * @param ext
	 *            - The extension of the file
	 * @param encoding
	 *            - The encoding of the database
	 * @param foreignKeys
	 *            - Whether to enable foreign keys or not in the database
	 * @throws NoDatabaseException
	 *             If the database does not exist
	 */
	public SQLite3db(final String dbname, String ext, final String encoding, final boolean foreignKeys) throws NoDatabaseException {
		int i = 0;
		for (; i < ext.length(); i++) {
			if (ext.charAt(i) != '.') {
				break;
			}
		}

		if (i > 1) {
			ext = ext.substring(i - 1);
		} else if (i != 1 && ext.length() > 0) {
			ext = "." + ext;
		}

		this.file = new File(dbname + ext);
		this.load(encoding, foreignKeys);

		this.qb = new SQLite3qb();
	}

	/**
	 * Loads the database
	 * 
	 * @param encoding
	 *            - The encoding of the database
	 * @param foreignKeys
	 *            - Whether to use foreign keys or not
	 * 
	 * @throws NoDatabaseException
	 *             If the database does not exist
	 */
	private void load(final String encoding, final boolean foreignKeys) throws NoDatabaseException {
		if (this.file.exists()) {
			try {
				Class.forName("org.sqlite.JDBC");
			} catch (final ClassNotFoundException e) {
				e.printStackTrace();
			}

			this.lastError = null;

			this.connect(encoding, foreignKeys);
		} else {
			throw new NoDatabaseException();
		}
	}

	/**
	 * Creates the connection with the database
	 * 
	 * @param encoding
	 *            - The encoding of the database
	 * @param foreignKeys
	 *            - Whether to use foreign keys or not
	 */
	private void connect(final String encoding, final boolean foreignKeys) {
		try {
			this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.file.getCanonicalPath());
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}

		this.rawModify("PRAGMA encoding = \"" + encoding + "\";");
		if (foreignKeys) {
			this.rawModify("PRAGMA foreign_keys = ON;");
		}
	}

	// /**
	// * @return Número de tablas en la base de datos
	// */
	// private int num_tablas()
	// {
	// return contar("sqlite_master", "type='table'");
	// }

	/**
	 * Sets the database up from the given file. By default it does not delete
	 * the given file.
	 * 
	 * @param filename
	 *            - The file with the setup SQL for the database
	 */
	public void setup(final String filename) {
		setup(filename, false);
	}

	/**
	 * Sets the database up from the given file.
	 * 
	 * @param filename
	 *            - The file with the setup SQL for the database
	 * @param delete
	 *            - Whether to delete or not the file after populating the
	 *            database
	 */
	public void setup(final String filename, final boolean delete) {
		setup(new File(filename), delete);
	}

	/**
	 * Sets the database up from the given file. By default it does not delete
	 * the given file.
	 * 
	 * @param file
	 *            - The file with the setup SQL for the database
	 */
	public void setup(final File file) {
		setup(file, false);
	}

	/**
	 * Sets the database up from the given file.
	 * 
	 * @param file
	 *            - The file with the setup SQL for the database
	 * @param delete
	 *            - Whether to delete or not the file after populating the
	 *            database
	 */
	public void setup(final File file, final boolean delete) {
		try {
			this.rawModify(FileUtils.toString(file));
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Populates the database with the given SQL file. By default it does not
	 * delete the given file.
	 * 
	 * @param filename
	 *            - The file with the SQL for the population of the database
	 */
	public void populate(final String filename) {
		populate(filename, false);
	}

	/**
	 * Populates the database with the given SQL file.
	 * 
	 * @param filename
	 *            - The file with the SQL for the population of the database
	 * @param delete
	 *            - Whether to delete or not the file after populating the
	 *            database
	 */
	public void populate(final String filename, final boolean delete) {
		populate(new File(filename), delete);
	}

	/**
	 * Populates the database with the given SQL file. By default it does not
	 * delete the given file.
	 * 
	 * @param file
	 *            - The file with the SQL for the population of the database
	 */
	public void populate(final File file) {
		populate(file, false);
	}

	/**
	 * Populates the database with the given SQL file.
	 * 
	 * @param file
	 *            - The file with the SQL for the population of the database
	 * @param delete
	 *            - Whether to delete or not the file after populating the
	 *            database
	 */
	public void populate(final File file, final boolean delete) {
		LineIterator li = null;

		try {
			li = FileUtils.getLineIterator(file);
		} catch (final FileNotFoundException e1) {
			e1.printStackTrace();
		}

		while (li.hasNext()) {
			this.rawModify(li.next());
		}

		if (delete) {
			file.delete();
		}
	}

	private Statement createSentence() {
		Statement sentence = null;
		try {
			sentence = this.connection.createStatement();
			sentence.setQueryTimeout(10);
		} catch (final SQLException e) {
			System.err.println("An error occurred when creating the sentence:");
			System.err.println(e.getMessage());
			this.lastError = e.getMessage();
		}

		return sentence;
	}

	/**
	 * Executes a raw query in the database. Use with caution, it should only be
	 * used with non-modifying statements.
	 * 
	 * @param query
	 *            - Raw query
	 * @return The result of the query
	 */
	public ResultSet rawQuery(final String query) {
		final Statement sentence = this.createSentence();
		ResultSet result = null;

		try {
			result = sentence.executeQuery(query);
		} catch (final SQLException e) {
			System.err.println("An error occurred when trying to execute the query:");
			System.err.println(e.getMessage());
			System.err.println("Query: " + query);
			this.lastError = e.getMessage();
		}

		return result;
	}

	/**
	 * Executes a raw modification sentence in the database. Use with caution.
	 * 
	 * @param query
	 *            - The modification statement
	 * @return The result of the modification
	 */
	public int rawModify(final String query) {
		final Statement sentence = this.createSentence();
		int result = 0;

		try {
			result = sentence.executeUpdate(query);
		} catch (final SQLException e) {
			System.err.println("An error occurred when trying to execute the modification:");
			System.err.println(e.getMessage());
			System.err.println("Sentence: " + query);
			this.lastError = e.getMessage();
		}

		return result;
	}

	// /**
	// * @param tabla Tabla que contar
	// * @param donde Cláusula WHERE
	// * @return Número contado
	// */
	// public int contar(String tabla, String donde)
	// {
	// int número = 0;
	// String where = donde == null ? "" : " WHERE " + donde;
	// String consulta = "SELECT COUNT(*) as número FROM " + tabla + where
	// + ";";
	//
	// ResultSet resultado = rawQuery(consulta);
	//
	// try
	// {
	// while (resultado.next())
	// {
	// número = resultado.getInt("número");
	// }
	// }
	// catch (SQLException e)
	// {
	// e.printStackTrace();
	// }
	//
	// return número;
	// }
	//
	// /**
	// * @param tabla Tabla que contar
	// * @return Número contado
	// */
	// public int contar(String tabla)
	// {
	// return contar(tabla, null);
	// }

	/**
	 * Closes the connection with the database.
	 */
	public void close() {
		try {
			this.connection.close();
		} catch (final SQLException e) {
			System.err.println("An erro occurred when trying to close the connection:");
			System.err.println(e.getMessage());
			this.lastError = e.getMessage();
		}

		this.file = null;
	}

	/**
	 * Gets the last error of the database
	 * 
	 * @return Last error
	 */
	public String getLastError() {
		return lastError;
	}

	/**
	 * Gets the Query Builder for the database
	 * 
	 * @return The Query Builder for the database
	 */
	public QueryBuilder qb() {
		return qb;
	}

	private class SQLite3qb implements QueryBuilder {

		private final String select;
		private String from;
		private String where;
		private String set;

		public SQLite3qb() {
			select = null;
		}

		@Override
		public QueryBuilder select(final String[] columns) {
			// TODO Auto-generated method stub
			return this;
		}

		@Override
		public QueryBuilder select(final String selection) {
			return this;
		}

		@Override
		public QueryBuilder from(final String[] tables) {
			// TODO Auto-generated method stub
			return this;
		}

		@Override
		public QueryBuilder from(final String from) {
			// TODO Auto-generated method stub
			return this;
		}

		@Override
		public QueryBuilder where(final Map<String, String> conditions) {
			// TODO Auto-generated method stub
			return this;
		}

		@Override
		public QueryBuilder where(final String where) {
			// TODO Auto-generated method stub
			return this;
		}

		@Override
		public ResultSet execute() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public QueryBuilder set(final Map<String, String> values) {
			// TODO Auto-generated method stub
			return this;
		}

		@Override
		public int update() {
			// TODO Auto-generated method stub
			return 0;
		}
	}
}