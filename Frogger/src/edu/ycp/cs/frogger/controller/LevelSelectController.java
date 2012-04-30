package edu.ycp.cs.frogger.controller;

import edu.ycp.cs496.model.Game;
import edu.ycp.cs496.model.persist.Database;
import edu.ycp.cs496.model.persist.PersistenceException;

public class LevelSelectController 
{
	private Game model;
	
	public void setModel(Game model)
	{
		this.model = model;
	}
	
	public int getMaxLevel() throws PersistenceException
	{
		return Database.getInstance().getMaxLevelNumber(model.getUser());
	}
}
