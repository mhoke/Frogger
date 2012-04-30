package edu.ycp.cs496.model.persist;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import edu.ycp.cs496.model.User;

public class Database implements IDatabase{
	static {
		try {
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("Could not load hsql driver");
		}
	}
	
	private static final String DB_NAME = "users";
	private static final String JDBC_URL ="jdbc:hsqldb:" + DB_NAME;
	// Maximum number of times to attempt a transaction before giving up.
	private static final int MAX_ATTEMPTS = 10;

	private static final Database instance = new Database();
	
	public static Database getInstance() {
		return instance;
	}
	
	public Integer getMaxLevelNumber(final User user) throws PersistenceException{		
		return databaseRun(new AbstractDatabaseRunnable<Integer>() {
			
			public Integer run(Connection conn) throws SQLException{
				PreparedStatement stmt = prepareStatement(conn,"select max_level from users where username = ? and password = ?");
				stmt.setString(1, user.getUsername());
				stmt.setString(2, user.getPassword());
				
				ResultSet resultSet = executeQuery(stmt);
				
				if(resultSet.next()){
					int max_level = resultSet.getInt(1);
					return max_level;
				}
				
				return null;
			}
		});
	}
	
	public Boolean incrementMaxLevel(final User user, final int NewLevel) throws PersistenceException
	{
		return databaseRun(new AbstractDatabaseRunnable<Boolean>()
		{
			public Boolean run(Connection conn) throws SQLException
			{
				PreparedStatement stmt = prepareStatement(conn, "update users set max_level=? where username =? and password=?");
				stmt.setInt(1, NewLevel);
				stmt.setString(2, user.getUsername());
				stmt.setString(3, user.getPassword());
				
				stmt.execute();
				
				return true;
			}
		});
		
	}
	
	public User getUser(final String username, final String password) throws PersistenceException{
		return databaseRun(new AbstractDatabaseRunnable<User>() {
			
			public User run(Connection conn) throws SQLException{
				PreparedStatement stmt = prepareStatement(conn,"select * from users where username = ? and password = ?");
				stmt.setString(1, username);
				stmt.setString(2, password);
				
				ResultSet resultSet = executeQuery(stmt);
				
				if(resultSet.next()){
					User user = new User(username, password);
					return user;
				}
				
				return null;
			}
		});
	}
	
	public void updateUser(final User user, final int max_level, final ArrayList<String> achievements, final int time) throws PersistenceException{
		databaseRun(new AbstractDatabaseRunnable<User>(){
			
			public User run(Connection conn) throws SQLException{
				PreparedStatement stmt = prepareStatement(conn, "update * from users " +
						"set max_level = ?, achievements = ?" +
						"where username = ? and password =?");
				stmt.setInt(1, max_level);
				stmt.setObject(2, achievements);
				stmt.setString(3, user.getUsername());
				stmt.setString(4, user.getPassword());
				
				executeQuery(stmt);
				
				return user;
			}
		});
	}
	
	@Override
	public Boolean changeData(final String name, final String password, final String cName) throws PersistenceException {
		return databaseRun(new AbstractDatabaseRunnable<Boolean>()
		{
			@Override
			public Boolean run(Connection conn) throws SQLException
			{
				PreparedStatement stmt = prepareStatement(conn, "update * from users set username = ? where username = ? and password = ?");
				stmt.setString(1, cName);
				stmt.setString(2, name);
				stmt.setString(3, password);
				
				ResultSet resultSet = executeQuery(stmt);
				
				if(resultSet.next())
				{
					return true;
				}
				else
				{
					return false;
				}
			}
		});
	}

	@Override
	public Boolean addUser(final User user) throws PersistenceException {
		return databaseRun(new AbstractDatabaseRunnable<Boolean>()
		{
			@Override
			public Boolean run(Connection conn) throws SQLException
			{
				PreparedStatement stmt = prepareStatement(conn, "insert into users values (NULL, ?, ?, ?)",
						PreparedStatement.RETURN_GENERATED_KEYS);
				
				//FIXME set id to a legitimate value
				stmt.setString(1, user.getUsername());
				stmt.setString(2, user.getPassword());
				stmt.setInt(3, 1);
				
				stmt.executeUpdate();
				
				ResultSet genKey = getGeneratedKeys(stmt);
				
				if (!genKey.next()){
					throw new SQLException("Couldn't get generated key");
				}
				
				
				user.setId(genKey.getInt(1));
				
				return true;
			}
		});
	}

	@Override
	public Boolean deleteUser(final String name, final String password) throws PersistenceException {
		return databaseRun(new AbstractDatabaseRunnable<Boolean>()
		{
			@Override
			public Boolean run(Connection conn) throws SQLException
			{
				PreparedStatement stmt = prepareStatement(conn, "delete * from users where username = ? and password = ?");
				stmt.setString(1, name);
				stmt.setString(2, password);
				
				executeQuery(stmt);
				//FIXME return a legitimate value
				return true;
			}
		});
	}
	
	@Override
	public Boolean userExists(final String username) throws PersistenceException {
		return databaseRun(new AbstractDatabaseRunnable<Boolean>()
		{
			public Boolean run(Connection conn) throws SQLException
			{
				PreparedStatement stmt = prepareStatement(conn, "select * from users where username = ?");
				stmt.setString(1, username);
				
				ResultSet resultSet = executeQuery(stmt);
				
				if(resultSet.next())
				{
					return true;
				}
				else
				{
					return false;
				}
			}
			
		});
	}
	
	private<E> E databaseRun(IDatabaseRunnable<E> dbRunnable) throws PersistenceException {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(JDBC_URL);
			conn.setAutoCommit(false);
			
			int numAttempts = 0;
			E result = null;
			boolean committed = false;
			
			while (!committed && numAttempts < MAX_ATTEMPTS) {
				try {
					// Attempt the transaction.
					E tmpResult = dbRunnable.run(conn);
					conn.commit();
					
					// Success!
					result = tmpResult;
					committed = true;
				} catch (SQLException e) {
					// Check to see if the transaction aborted due to deadlock.
					// If so, we can retry it.
					// See: http://dev.mysql.com/doc/refman/5.0/en/connector-j-usagenotes-troubleshooting.html
					String sqlState = e.getSQLState();
					if (sqlState != null && sqlState.equals("40001")) {
						// Deadlock detected.
						numAttempts++;
					} else {
						// Some other failure: just rethrow the exception.
						throw e;
					}
				}
			}
			
			if (numAttempts >= MAX_ATTEMPTS) {
				throw new PersistenceException("Transaction deadlock retry count exceeded");
			}
			
			return result;
		} catch (SQLException e) {
			throw new PersistenceException("MySQL error", e);
		} finally {
			dbRunnable.cleanup(); // ensure all resources are cleaned up
			DBUtil.closeQuietly(conn); // ensure database connection is closed
		}
	}
	
	protected void load(User user, ResultSet resultSet, int index) throws SQLException {
		user.setId(resultSet.getInt(index++));
		user.setUsername(resultSet.getString(index++));
	}

}
