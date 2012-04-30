package edu.ycp.cs.frogger.controller;

import edu.ycp.cs496.model.Game;
import edu.ycp.cs496.model.Location;
import edu.ycp.cs496.model.Terrain;
import edu.ycp.cs496.model.persist.Database;
import edu.ycp.cs496.model.persist.PersistenceException;

public class GameController 
{
	private Game model;
	
	public void setModel(Game model)
	{
		this.model = model;
	}
	
	public boolean isDone(Location playerLoc)
	{
		return model.getLevel().getCurrentMap().isFinishingPoint(playerLoc);
	}
	
	public void updateMaxLevel(int LevelNum) throws PersistenceException
	{
		if(LevelNum == Database.getInstance().getMaxLevelNumber(model.getUser()))
		{			//Interesting Note: Increments (Var++) pass the variable and *then* increment it
			Database.getInstance().incrementMaxLevel(model.getUser(), ++LevelNum);
		}
	}
	
	public boolean step(int keypress)
	{
		return model.step(keypress);
	}
}
