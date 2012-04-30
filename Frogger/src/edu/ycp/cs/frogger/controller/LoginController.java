package edu.ycp.cs.frogger.controller;

import edu.ycp.cs496.model.Game;
import edu.ycp.cs496.model.persist.PersistenceException;

public class LoginController {
	private Game model;
	
	public void setModel(Game model){
		this.model = model;
	}
	
	public Boolean createAccount(String username, String password) throws PersistenceException{
		return model.createAccount(username, password);
	}
	
	public Boolean login(String username, String password) throws PersistenceException{
		return model.login(username, password);
	}
}
