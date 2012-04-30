package edu.ycp.cs496.model.persist;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Interface for database transactions.
 *
 * @param <E> the type of object returned by the transaction
 */
public interface IDatabaseRunnable<E> {
	/**
	 * Execute a transaction.
	 * 
	 * @param conn the database connection
	 * @return the result of the transaction
	 * @throws SQLException
	 */
	public E run(Connection conn) throws SQLException;
	
	/**
	 * Clean up any resources created by this transaction.
	 */
	public void cleanup();
}
