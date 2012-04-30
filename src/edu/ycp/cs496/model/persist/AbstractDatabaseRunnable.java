package edu.ycp.cs496.model.persist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Stack;

/**
 * Abstract base class for database transactions.
 * Provides methods for creating resources (PreparedStatements, ResultSets)
 * that will be automatically cleaned up by the cleanup() method.
 *
 * @param <E> the type of object returned by the transaction
 */
public abstract class AbstractDatabaseRunnable<E> implements IDatabaseRunnable<E> {
	private Stack<Object> cleanupStack = new Stack<Object>();

	/**
	 * Create a PreparedStatement.
	 * The created PreparedStatement will be pushed on the stack
	 * of resources to be cleaned up.
	 * 
	 * @param conn  the database connection
	 * @param sql   the SQL statement
	 * @return a PreparedStatement
	 * @throws SQLException
	 */
	protected PreparedStatement prepareStatement(Connection conn, String sql) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement(sql);
		cleanupStack.push(stmt);
		return stmt;
	}
	
	/**
	 * Create a PreparedStatement.
	 * The created PreparedStatement will be pushed on the stack
	 * of resources to be cleaned up.
	 * 
	 * @param conn  the database connection
	 * @param sql   the SQL statement
	 * @param options prepared statement options
	 * @return a PreparedStatement
	 * @throws SQLException
	 */
	protected PreparedStatement prepareStatement(Connection conn, String sql, int options) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement(sql, options);
		cleanupStack.push(stmt);
		return stmt;
	}
	
	/**
	 * Execute a query.
	 * 
	 * @param stmt PreparedStatement to execute
	 * @return the ResultSet through which the results of the query may be retrieved
	 * @throws SQLException
	 */
	protected ResultSet executeQuery(PreparedStatement stmt) throws SQLException {
		ResultSet resultSet = stmt.executeQuery();
		cleanupStack.push(resultSet);
		return resultSet;
	}

	/**
	 * Get generated keys resulting from executing an insert statement.
	 * 
	 * @param stmt  the insert statement
	 * @return the ResultSet through which the generated keys will be returned
	 * @throws SQLException
	 */
	public ResultSet getGeneratedKeys(PreparedStatement stmt) throws SQLException {
		ResultSet resultSet = stmt.getGeneratedKeys();
		cleanupStack.push(resultSet);
		return resultSet;
	}
	
	@Override
	public void cleanup() {
		while (!cleanupStack.isEmpty()) {
			Object o = cleanupStack.pop();
			if (o instanceof PreparedStatement) {
				DBUtil.closeQuietly((PreparedStatement) o);
			} else if (o instanceof ResultSet) {
				DBUtil.closeQuietly((ResultSet) o);
			}
		}
	}
}
