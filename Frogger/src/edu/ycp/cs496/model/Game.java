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
			level.getCurrentMap().getMapArray()[e.getLocation().getX()][e.getLocation().getY()] = e.getPrevVal();
			Location temp = e.move();
			if(level.getCurrentMap().getMapArray()[temp.getX()][temp.getY()] == Terrain.COLLECTIBLE.getValue())
			{
				e.setPrevVal(Terrain.COLLECTIBLE.getValue());
			}
			else
			{
				e.setPrevVal(Terrain.PATH.getValue());
			}
			level.getCurrentMap().getMapArray()[temp.getX()][temp.getY()] = Terrain.ENEMY.getValue();
		}
		
		int tempMap[][] = level.getCurrentMap().getMapArray();
		Location playerLoc = level.getCurrentPlayer().getLocation();
		
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
		
		//TODO check for collisions between player and enemy and player and collectible
		//Return collision status - true for collision with enemy, false for not
		
		return returnVal;
	}
	
//	public static void main(String[] args) throws PersistenceException{
//
//		boolean gameDone = false;
//		int levelDone; // 0 when playing level, -1 if level is lost, +1 if level is won
//		
//		int levelNumber = 1;
//		int numDeaths = 0;
//		int time = 0; // this is the number of times the while loop executed
//		ArrayList<String> achievements = new ArrayList<String>();
//		User user = null;
//		
//
//		
//		// Prompt user to log in or create account
//		Scanner keyboard = new Scanner(System.in);
//		System.out.println("Type log in or create account.");
//		String choice = keyboard.nextLine();
//		while (!choice.equals("log in") && !choice.equals("create account")){
//			System.out.println("Invalid entry. Type log in or create account.");
//			choice = keyboard.nextLine();
//		}
//		
//		// user chose to log in
//		if (choice.equals("log in")){
//			System.out.println("Enter a username: ");
//		    String username = keyboard.next();
//		    System.out.println("Enter a password: ");
//		    String password = keyboard.next();
//			    
//		    // Not yet logged in (try again)
//		    while (Database.getInstance().getUser(username, password) == null){
//		    	System.out.println("Login failed. Try again.");
//
//		    	System.out.println("Enter a username: ");
//		    	username = keyboard.next();
//		    	System.out.println("Enter a password: ");
//		        password = keyboard.next();
//		    	
//		    }
//		    
//		    // Logged in
//		    System.out.println("Login Successful");
//		    user = new User(username, password);
//		    
//			// user enters a level they would like to play must be less than or equal to their max level
//			int maxLevel = Database.getInstance().getMaxLevelNumber(user);
//			System.out.println("Please enter the level number you would like to play");
//			levelNumber = keyboard.nextInt();
//			while (levelNumber < 1 || levelNumber > maxLevel){
//				System.out.println("Invalid level entry. Please enter a level number you have reached before: ");
//				levelNumber = keyboard.nextInt();
//			}	
//		}
//		
//		// user chose to create account
//		if (choice.equals("create account")){
//			System.out.println("Enter a username: ");
//		    String username = keyboard.next();
//		    System.out.println("Enter a password: ");
//		    String password = keyboard.next();
//		    // Username already taken
//		    while(Database.getInstance().userExists(username)){
//		    	System.out.println("Account already exists. Try again.");
//		    	System.out.println("Enter a username: ");
//			    username = keyboard.next();
//			    System.out.println("Enter a password: ");
//			    password = keyboard.next();
//		    }
//		    // Create account
//		    user = new User(username, password);
//		    Database.getInstance().addUser(user);
//		    
//		    // Log in
//		    user = new User(username, password);
//		    
//		    // Start at level 1
//		    levelNumber = 1;
//		}
//		
//		
//		
//
//		// create map and level objects
//		Map map = new Map(levelNumber);
//		Level level = new Level(map);
//		
//		while (!gameDone){
//			levelDone = 0;
//			while (levelDone == 0){		
//				// TODO: Get user key press if any (should only be arrow keys)
//				// can press two keys at same time but must be in horizontal and vertical
//				// It seems like this should be done on the GUI side
//				
//				// update player location
//				//level.getCurrentPlayer().move(key1, key2);
//				
//				// update enemy location
//				for (Enemy enemy : level.getEnemies()) {
//					enemy.move();
//				}
//				
//				// check if player collided with an enemy
//				for (Enemy enemy : level.getEnemies()){
//					if (level.getCurrentPlayer().getLocation().equals(enemy.getLocation())){
//						// player lost
//						levelDone = -1;
//					}
//				}
//				
//				//check if player collided with a collectible
//				for (Collectible collectible : level.getCollectibles()){
//					// check if player is at a collectible location 
//					if (level.getCurrentPlayer().getLocation().equals(collectible.getLocation())){
//						// remove it from the list of remaining collectibles
//						level.getCollectibles().remove(collectible);
//					}
//				}
//				
//				// check if player won
//				if (level.getCollectibles().isEmpty() && levelDone == 0){
//					// Check if player is at a finishing point
//					if (level.getCurrentMap().isFinishingPoint(level.getCurrentPlayer().getLocation())){
//						// player won
//						levelDone = 1;
//					}
//				}
//				time++;
//			}
//					
//			// Player won last game
//			if (levelDone == 1){
//				levelNumber++;
//				// Prepare next level
//				if (levelNumber < 10){
//					map = new Map(levelNumber+1);
//					level = new Level(map);
//				
//					// Check if user completed any achievements
//					achievements.addAll(checkForAchievements(levelNumber-1, time, numDeaths));
//					
//					// Reset variables
//					time = 0;
//					levelDone = 0;
//				}
//				// No more levels
//				else {
//					gameDone = true;
//					
//					// Check if user completed any achievements
//					achievements.addAll(checkForAchievements(levelNumber, time, numDeaths));
//				}
//			}
//			
//			// Player lost last game
//			else {
//				map = new Map(levelNumber);
//				level = new Level(map);
//				numDeaths++;
//			}
//			
//
//		}
//		// update database (achievements, maxLevel, time, etc.)
//		Database.getInstance().updateUser(user, levelNumber, achievements, time);
//	}
//	
//	private static ArrayList<String> checkForAchievements(int levelNumber, int time, int numDeaths){
//		ArrayList<String> list = new ArrayList<String>();
//		// level 1 achievements
//		if(levelNumber == 1){
//			// time achievement
//			if (time <= 200){
//				list.add("Time achievement for level" + levelNumber);
//			}
//		}
//		
//		// level 2 achievements
//		if (levelNumber == 2){
//			// time achievement
//			if (time <= 200){
//				list.add("Time achievement for level" + levelNumber);
//			}
//		}
//		
//		// etc...
//		
//		return list;
//	}
}
