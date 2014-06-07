package razican.db;

import java.sql.ResultSet;
import java.util.Map;

/**
 * Query builder for database drivers.
 * 
 * @author Razican (Iban Eguia)
 */
public interface QueryBuilder {

    public QueryBuilder select(String[] columns);

    public QueryBuilder select(String selection);

    public QueryBuilder from(String[] tables);

    public QueryBuilder from(String from);

    public QueryBuilder where(Map<String, String> conditions);

    public QueryBuilder where(String where);

    public ResultSet execute();

    public QueryBuilder set(Map<String, String> values);

    public int update();
}