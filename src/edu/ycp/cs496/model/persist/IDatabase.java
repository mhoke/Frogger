package edu.ycp.cs496.model.persist;

import java.util.ArrayList;

import edu.ycp.cs496.model.User;

public interface IDatabase {
	public Integer getMaxLevelNumber(final User user) throws PersistenceException;
	public User getUser(String username, String password) throws PersistenceException;
	public void updateUser(final User user, final int max_level, final ArrayList<String> achievements, final int time) throws PersistenceException;
	public Boolean changeData(String name, String password, String cName) throws PersistenceException;
	public Boolean addUser(User user) throws PersistenceException;
	public Boolean deleteUser(String name, String password) throws PersistenceException;
	public Boolean userExists(String username) throws PersistenceException;
}
