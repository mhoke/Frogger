package edu.ycp.cs496.model;

import java.util.ArrayList;
import java.util.Scanner;

import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;

import edu.ycp.cs496.model.persist.Database;
import edu.ycp.cs496.model.persist.PersistenceException;

public class Game 
{
	Level level;
	User user;
		
	public Boolean createAccount(String username, String password) throws PersistenceException {
		 // Username already taken
	    if (!Database.getInstance().userExists(username)){
		    // Create account
		    User user = new User(username, password);
		    Database.getInstance().addUser(user);
		    return true;
	    }
	    else {
	    	return false;
	    }
	}
	
	public Boolean login(String username, String password) throws PersistenceException {
		if (Database.getInstance().getUser(username, password) == null){
	    	return false;
	    }
		else {
			user = new User(username, password);
			return true;
		}
	}
	
	public void setUser(User user)
	{
		this.user = user;
	}
	
	public User getUser()
	{
		return user;
	}
	
	public void setLevel(Level level)
	{
		this.level = level;
	}
	
	public Level getLevel()
	{
		return level;
	}
	
	public boolean step(int keypress)
	{
		boolean returnVal = false;
		for(Enemy e : level.getEnemies())
		{
			level.getCurrentMap().getMapArray()[e.getLocation().getX()][e.getLocation().getY()] = Terrain.PATH.getValue();
			Location temp = e.move();
			level.getCurrentMap().getMapArray()[temp.getX()][temp.getY()] = Terrain.ENEMY.getValue();
		}
		
		int tempMap[][] = level.getCurrentMap().getMapArray();
		Location playerLoc = level.getCurrentPlayer().getLocation();
		
		for(Collectible c : level.getCollectibles())
		{
			if(c.getLocation().equals(playerLoc))
			{
				c.setVisible(false);
			}
		}
		
		switch(keypress)
		{
			//TODO handle player movement here
			case 37:
				if(playerLoc.getX() > 0 && tempMap[playerLoc.getX() - 1][playerLoc.getY()] != Terrain.BLANK.getValue())
				{
					tempMap[playerLoc.getX()][playerLoc.getY()] = level.getCurrentPlayer().getPrevVal();
					level.getCurrentPlayer().setPrevVal(tempMap[playerLoc.getX() - 1][playerLoc.getY()]);
					tempMap[playerLoc.getX() - 1][playerLoc.getY()] = Terrain.PLAYER.getValue();
					playerLoc.setX(playerLoc.getX() - 1);
				}
				break;
			case 38:
				if(playerLoc.getY() < 9 && tempMap[playerLoc.getX()][playerLoc.getY() + 1] != Terrain.BLANK.getValue())
				{
					tempMap[playerLoc.getX()][playerLoc.getY()] = level.getCurrentPlayer().getPrevVal();
					level.getCurrentPlayer().setPrevVal(tempMap[playerLoc.getX()][playerLoc.getY() + 1]);
					tempMap[playerLoc.getX()][playerLoc.getY() + 1] = Terrain.PLAYER.getValue();
					playerLoc.setY(playerLoc.getY() + 1);
				}
				break;
			case 39:
				if(playerLoc.getX() < 9 && tempMap[playerLoc.getX() + 1][playerLoc.getY()] != Terrain.BLANK.getValue())
				{
					tempMap[playerLoc.getX()][playerLoc.getY()] = level.getCurrentPlayer().getPrevVal();
					level.getCurrentPlayer().setPrevVal(tempMap[playerLoc.getX() + 1][playerLoc.getY()]);
					tempMap[playerLoc.getX() + 1][playerLoc.getY()] = Terrain.PLAYER.getValue();
					playerLoc.setX(playerLoc.getX() + 1);
				}
				break;
			case 40:
				if(playerLoc.getY() > 0 && tempMap[playerLoc.getX()][playerLoc.getY() - 1] != Terrain.BLANK.getValue())
				{
					tempMap[playerLoc.getX()][playerLoc.getY()] = level.getCurrentPlayer().getPrevVal();
					level.getCurrentPlayer().setPrevVal(tempMap[playerLoc.getX()][playerLoc.getY() - 1]);
					tempMap[playerLoc.getX()][playerLoc.getY() - 1] = Terrain.PLAYER.getValue();
					playerLoc.setY(playerLoc.getY() - 1);
				}
				break;
			default:
				break;
		}
		
		if(level.getCurrentPlayer().getPrevVal() == Terrain.COLLECTIBLE.getValue())
		{
			level.getCurrentPlayer().setPrevVal(Terrain.PATH.getValue());
		}
		
		for(Enemy e : level.getEnemies())
		{
			if(e.getLocation().equals(level.getCurrentPlayer().getLocation()))
			{
				returnVal = true;
			}
			level.getCurrentMap().getMapArray()[e.getLocation().getX()][e.getLocation().getY()] = Terrain.ENEMY.getValue();
		}
		
		for(Collectible c : level.getCollectibles())
		{
			if(c.getVisible() && level.getCurrentMap().getMapArray()[c.getLocation().getX()][c.getLocation().getY()] == Terrain.PATH.getValue())
			{
				level.getCurrentMap().getMapArray()[c.getLocation().getX()][c.getLocation().getY()] = Terrain.COLLECTIBLE.getValue();
			}
		}
		
		//Return collision status - true for collision with enemy, false for not
		
		return returnVal;
	}
}
