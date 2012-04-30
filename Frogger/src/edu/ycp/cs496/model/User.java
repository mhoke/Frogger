package edu.ycp.cs496.model;

import edu.ycp.cs496.model.persist.Database;
import edu.ycp.cs496.model.persist.PersistenceException;

public class User {
	private int id;
	private String username;
	private String password;
	
	public User(String username, String password){
		this.username = username;
		this.password = password;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public void setUsername(String username){
		this.username = username;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
	public int getId(){
		return id;
	}
	
	public String getUsername(){
		return username;
	}
	
	public String getPassword(){
		return password;
	}
	
	public boolean equals(User user){
		if (username.equals(user.getUsername()) && password.equals(user.getPassword())){
			return true;
		}
		return false;
	}
	
	public int getMaxLevel() throws PersistenceException
	{
		return Database.getInstance().getMaxLevelNumber(new User(username, password));
	}
}
