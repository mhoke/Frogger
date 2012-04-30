package edu.ycp.cs496.model;

import java.util.ArrayList;
import java.util.List;

public class Map {
	// 0 is blank space, 1 is player starting location, 2 is collectible location, 3 is starting location for an enemy, 4 is a finishing point
	private int [][] mapArray;
	private int GRID_SIZE = 10;
	
	public int getGrid_Size()
	{
		return GRID_SIZE;
	}
	
	public void setGrid_Size(int i)
	{
		GRID_SIZE = i;
	}
	
	public Map (int levelNumber, ArrayList<Enemy> EnemiesList){
		mapArray = loadMap(levelNumber, EnemiesList);
		EnemiesList = new ArrayList<Enemy>();
	}
	
	public void setMapArray(int [][] mapArray){
		this.mapArray = mapArray;
	}
	
	public int[][] getMapArray(){
		return mapArray;
	}
	
	public Location getPlayerStartingLocation(){
		for(int i = 0; i < mapArray.length; i++){
			for(int j = 0; j < mapArray[i].length; j++){
				if (mapArray[i][j] == Terrain.PLAYER.getValue()){
					return new Location(i, j);
				}
			}
		}
		// this shouldn't happen because every map should have a starting location for a player
		return null;
	}
	
	public ArrayList<Collectible> getCollectibleList(){
		ArrayList<Collectible> list = new ArrayList<Collectible>();
		for(int i = 0; i < mapArray.length; i++){
			for(int j = 0; j < mapArray[i].length; j++){
				if (mapArray[i][j] == Terrain.COLLECTIBLE.getValue()){
					list.add(new Collectible(new Location(i, j)));
				}
			}
		}
		return list;
	}		
	
	public boolean isFinishingPoint(Location loc){
		if (mapArray[loc.getX()][loc.getY()] == Terrain.FINISH.getValue()){
			return true;
		}
		return false;
	}
	
	private int[][] loadMap(int levelNumber, ArrayList<Enemy> EnemiesList) {
		//TODO
		Location startLoc;		//Keep track of enemy starting location and move locations
		List<Location> moveLoc = new ArrayList<Location>();
		mapArray = new int[GRID_SIZE][GRID_SIZE];	//Initialize the map
		
		for(int i = 0; i < GRID_SIZE; i ++)
		{
			for(int j = 0; j < GRID_SIZE; j ++)
			{
				mapArray[i][j] = Terrain.BLANK.getValue();
			}
		}
		
		//load appropriate array based on levelNumber
		
		//The map creation will have to be hard code - they are set for a 10x10 grid_size
		//(0,0) is the bottom left corner - Cartesian
		switch(levelNumber){
		case 1:
			for(int i = 0; i < 2; i ++)
			{
				for(int j = 0; j < GRID_SIZE; j ++)
				{
					mapArray[i][j] = Terrain.PATH.getValue();
				}
			}
			for(int i = 0; i < 2; i ++)
			{
				for(int j = 2; j < GRID_SIZE; j ++)
				{
					mapArray[j][i] = Terrain.PATH.getValue();
				}
			}
			mapArray[0][9] = Terrain.START.getValue();
			mapArray[0][8] = Terrain.START.getValue();
			mapArray[1][9] = Terrain.START.getValue();
			mapArray[1][8] = Terrain.START.getValue();
			
			mapArray[0][9] = Terrain.PLAYER.getValue();
			
			mapArray[0][5] = Terrain.ENEMY.getValue();
			startLoc = new Location(0, 5);
			moveLoc.add(startLoc);
			moveLoc.add(new Location(1, 5));
			EnemiesList.add(new Enemy(startLoc, moveLoc));
			
			mapArray[3][1] = Terrain.ENEMY.getValue();
			startLoc = new Location(3, 1);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(3, 0));
			EnemiesList.add(new Enemy(startLoc, moveLoc));
			
			mapArray[9][0] = Terrain.FINISH.getValue();
			mapArray[8][0] = Terrain.FINISH.getValue();
			mapArray[9][1] = Terrain.FINISH.getValue();
			mapArray[8][1] = Terrain.FINISH.getValue();
			
			break;
			
		case 2:
			// Path
			for (int i = 0; i < 6; i++){
				for (int j = 9; j > 6; j--){
					mapArray[i][j] = Terrain.PATH.getValue();
				}
			}
			for (int i = 3; i < 10; i++){
				for (int j = 6; j > 2; j--){
					mapArray[i][j] = Terrain.PATH.getValue();
				}
			}
			mapArray[8][2] = Terrain.PATH.getValue();
			mapArray[9][2] = Terrain.PATH.getValue();
			
			// Start
			mapArray[0][9] = Terrain.START.getValue();
			mapArray[1][9] = Terrain.START.getValue();
			mapArray[0][8] = Terrain.START.getValue();
			mapArray[1][8] = Terrain.START.getValue();
			
			// Finish 
			mapArray[8][1] = Terrain.FINISH.getValue();
			mapArray[8][0] = Terrain.FINISH.getValue();
			mapArray[9][1] = Terrain.FINISH.getValue();
			mapArray[9][0] = Terrain.FINISH.getValue();
			
			// Player
			mapArray[0][9] = Terrain.PLAYER.getValue();
			
			// Enemies
			mapArray[3][7] = Terrain.ENEMY.getValue();
			startLoc = new Location(3, 7);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(4, 7));
			moveLoc.add(new Location(5, 7));
			moveLoc.add(new Location(4, 7));
			
			EnemiesList.add(new Enemy(startLoc, moveLoc));
			
			mapArray[6][3] = Terrain.ENEMY.getValue();
			startLoc = new Location(6, 3);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(6, 4));
			moveLoc.add(new Location(6, 5));
			moveLoc.add(new Location(6, 6));
			moveLoc.add(new Location(6, 5));
			moveLoc.add(new Location(6, 4));
			EnemiesList.add(new Enemy(startLoc, moveLoc));
			
			mapArray[8][3] = Terrain.ENEMY.getValue();
			startLoc = new Location(8, 3);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(8, 4));
			moveLoc.add(new Location(8, 5));
			moveLoc.add(new Location(8, 6));
			moveLoc.add(new Location(8, 5));
			moveLoc.add(new Location(8, 4));
			EnemiesList.add(new Enemy(startLoc, moveLoc));
			
			break;
		case 3:
			// Path
			for (int i = 0; i < 10; i++){
				for (int j = 9; j > 7; j--){
					mapArray[i][j] = Terrain.PATH.getValue();
				}
			}
			for (int i = 2; i < 4; i++){
				for (int j = 9; j > 1; j--){
					mapArray[i][j] = Terrain.PATH.getValue();
				}
			}
			for (int i = 5; i > 1; i--){
				for (int j = 2; j < 10; j++){
					mapArray[j][i] = Terrain.PATH.getValue();
				}
			}
			
			// Start
			mapArray[0][9] = Terrain.START.getValue();
			mapArray[1][9] = Terrain.START.getValue();
			mapArray[0][8] = Terrain.START.getValue();
			mapArray[1][8] = Terrain.START.getValue();
			
			// Finish
			mapArray[8][3] = Terrain.FINISH.getValue();
			mapArray[9][3] = Terrain.FINISH.getValue();
			mapArray[8][2] = Terrain.FINISH.getValue();
			mapArray[9][2] = Terrain.FINISH.getValue();
			
			// Player
			mapArray[0][9] = Terrain.PLAYER.getValue();
			
			//Enemies
			mapArray[6][9] = Terrain.ENEMY.getValue();
			startLoc = new Location(6, 9);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(6, 8));
			EnemiesList.add(new Enemy(startLoc, moveLoc));
			
			mapArray[2][7] = Terrain.ENEMY.getValue();
			startLoc = new Location(2, 7);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(3, 7));
			EnemiesList.add(new Enemy(startLoc, moveLoc));
			
			
			mapArray[4][5] = Terrain.ENEMY.getValue();
			startLoc = new Location(4, 5);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(4, 4));
			moveLoc.add(new Location(4, 3));
			moveLoc.add(new Location(4, 2));
			moveLoc.add(new Location(4, 3));
			moveLoc.add(new Location(4, 4));
			EnemiesList.add(new Enemy(startLoc, moveLoc));
			
			mapArray[6][2] = Terrain.ENEMY.getValue();
			startLoc = new Location(6, 2);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(6, 3));
			moveLoc.add(new Location(6, 4));
			moveLoc.add(new Location(6, 5));
			moveLoc.add(new Location(6, 4));
			moveLoc.add(new Location(6, 3));
			EnemiesList.add(new Enemy(startLoc, moveLoc));
			
			// Collectible
			mapArray[9][8] = Terrain.COLLECTIBLE.getValue();
			break;
			
		case 4:
			// Path
			for (int i = 4; i < 10; i++){
				for (int j = 8; j > 6; j--){
					mapArray[i][j] = Terrain.PATH.getValue();
				}
			}
			for (int i = 4; i < 6; i++){
				for (int j = 0; j < 9; j++){
					mapArray[i][j] = Terrain.PATH.getValue();
				}
			}
			for (int i = 2; i < 4; i++){
				for (int j = 4; j < 6; j++){
					mapArray[i][j] = Terrain.PATH.getValue();
				}
			}
			for (int i = 2; i < 4; i++){
				for (int j = 0; j < 2; j++){
					mapArray[i][j] = Terrain.PATH.getValue();
				}
			}
			
			// Start
			for (int i = 0; i < 2; i++){
				for (int j = 0; j < 2; j++){
					mapArray[i][j] = Terrain.START.getValue();
				}
			}
			
			// Finish
			for (int i = 0; i < 2; i++){
				for (int j = 4; j < 6; j++){
					mapArray[i][j] = Terrain.FINISH.getValue();
				}
			}
			
			// Player
			mapArray[0][5] = Terrain.PLAYER.getValue();
			
			// Enemies
			mapArray[3][5] = Terrain.ENEMY.getValue();
			startLoc = new Location(3, 5);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(3, 4));
			EnemiesList.add(new Enemy(startLoc, moveLoc));
			
			mapArray[4][8] = Terrain.ENEMY.getValue();
			startLoc = new Location(4, 8);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(4, 7));
			moveLoc.add(new Location(4, 6));
			moveLoc.add(new Location(4, 5));
			moveLoc.add(new Location(4, 4));
			moveLoc.add(new Location(4, 3));
			moveLoc.add(new Location(4, 2));
			moveLoc.add(new Location(4, 1));
			moveLoc.add(new Location(4, 0));
			moveLoc.add(new Location(4, 1));
			moveLoc.add(new Location(4, 2));
			moveLoc.add(new Location(4, 3));
			moveLoc.add(new Location(4, 4));
			moveLoc.add(new Location(4, 5));
			moveLoc.add(new Location(4, 6));
			moveLoc.add(new Location(4, 7));
			EnemiesList.add(new Enemy(startLoc, moveLoc));
			
			mapArray[5][0] = Terrain.ENEMY.getValue();
			startLoc = new Location(5, 0);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(5, 1));
			moveLoc.add(new Location(5, 2));
			moveLoc.add(new Location(5, 3));
			moveLoc.add(new Location(5, 4));
			moveLoc.add(new Location(5, 5));
			moveLoc.add(new Location(5, 6));
			moveLoc.add(new Location(5, 7));
			moveLoc.add(new Location(5, 8));
			moveLoc.add(new Location(5, 7));
			moveLoc.add(new Location(5, 6));
			moveLoc.add(new Location(5, 5));
			moveLoc.add(new Location(5, 4));
			moveLoc.add(new Location(5, 3));
			moveLoc.add(new Location(5, 2));
			moveLoc.add(new Location(5, 1));
			EnemiesList.add(new Enemy(startLoc, moveLoc));
			
			mapArray[6][7] = Terrain.ENEMY.getValue();
			startLoc = new Location(6, 7);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(6, 8));
			EnemiesList.add(new Enemy(startLoc, moveLoc));
			
			mapArray[8][7] = Terrain.ENEMY.getValue();
			startLoc = new Location(8, 7);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(8, 8));
			EnemiesList.add(new Enemy(startLoc, moveLoc));
			
			// Collectible
			mapArray[9][7] = Terrain.COLLECTIBLE.getValue();				
			break;
			
		case 5:
			// Path
			for (int i = 2; i < 7; i++){
				for (int j = 7; j < 9; j++){
					mapArray[i][j] = Terrain.PATH.getValue();
				}
			}
			for (int i = 4; i < 7; i++){
				for (int j = 0; j < 7; j++){
					mapArray[i][j] = Terrain.PATH.getValue();
				}
			}
			for (int i = 2; i < 4; i++){
				for (int j = 0; j < 2; j++){
					mapArray[i][j] = Terrain.PATH.getValue();
				}
			}
			for (int i = 7; i < 10; i++){
				for (int j = 4; j < 6; j++){
					mapArray[i][j] = Terrain.PATH.getValue();
				}
			}
			mapArray[7][0] = Terrain.PATH.getValue();
			
			// Start
			for (int i = 0; i < 2; i++){
				for (int j = 7; j < 9; j++){
					mapArray[i][j] = Terrain.START.getValue();
				}
			}
			
			// Finish
			for (int i = 8; i < 10; i++){
				for (int j = 0; j < 2; j++){
					mapArray[i][j] = Terrain.FINISH.getValue();
				}
			}
			
			// Player
			mapArray[0][8] = Terrain.PLAYER.getValue();
			
			// Enemies
			mapArray[4][8] = Terrain.ENEMY.getValue();
			startLoc = new Location(4, 8);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(4, 7));
			moveLoc.add(new Location(4, 6));
			moveLoc.add(new Location(4, 5));
			moveLoc.add(new Location(4, 4));
			moveLoc.add(new Location(4, 3));
			moveLoc.add(new Location(4, 2));
			moveLoc.add(new Location(4, 1));
			moveLoc.add(new Location(4, 0));
			moveLoc.add(new Location(4, 1));
			moveLoc.add(new Location(4, 2));
			moveLoc.add(new Location(4, 3));
			moveLoc.add(new Location(4, 4));
			moveLoc.add(new Location(4, 5));
			moveLoc.add(new Location(4, 6));
			moveLoc.add(new Location(4, 7));
			EnemiesList.add(new Enemy(startLoc, moveLoc));
			
			mapArray[5][0] = Terrain.ENEMY.getValue();
			startLoc = new Location(5, 0);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(5, 1));
			moveLoc.add(new Location(5, 2));
			moveLoc.add(new Location(5, 3));
			moveLoc.add(new Location(5, 4));
			moveLoc.add(new Location(5, 5));
			moveLoc.add(new Location(5, 6));
			moveLoc.add(new Location(5, 7));
			moveLoc.add(new Location(5, 8));
			moveLoc.add(new Location(5, 7));
			moveLoc.add(new Location(5, 6));
			moveLoc.add(new Location(5, 5));
			moveLoc.add(new Location(5, 4));
			moveLoc.add(new Location(5, 3));
			moveLoc.add(new Location(5, 2));
			moveLoc.add(new Location(5, 1));
			EnemiesList.add(new Enemy(startLoc, moveLoc));
			
			mapArray[6][8] = Terrain.ENEMY.getValue();
			startLoc = new Location(6, 8);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(6, 7));
			moveLoc.add(new Location(6, 6));
			moveLoc.add(new Location(6, 5));
			moveLoc.add(new Location(6, 4));
			moveLoc.add(new Location(6, 3));
			moveLoc.add(new Location(6, 2));
			moveLoc.add(new Location(6, 1));
			moveLoc.add(new Location(6, 0));
			moveLoc.add(new Location(6, 1));
			moveLoc.add(new Location(6, 2));
			moveLoc.add(new Location(6, 3));
			moveLoc.add(new Location(6, 4));
			moveLoc.add(new Location(6, 5));
			moveLoc.add(new Location(6, 6));
			moveLoc.add(new Location(6, 7));
			EnemiesList.add(new Enemy(startLoc, moveLoc));
			
			mapArray[3][1] = Terrain.ENEMY.getValue();
			startLoc = new Location(3, 1);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(3, 0));
			EnemiesList.add(new Enemy(startLoc, moveLoc));
			
			mapArray[7][5] = Terrain.ENEMY.getValue();
			startLoc = new Location(7, 5);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(7, 4));
			EnemiesList.add(new Enemy(startLoc, moveLoc));
			
			mapArray[7][1] = Terrain.ENEMY.getValue();
			startLoc = new Location(7, 1);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(7, 0));
			EnemiesList.add(new Enemy(startLoc, moveLoc));
			
			// Collectibles
			mapArray[2][1] = Terrain.COLLECTIBLE.getValue();
			mapArray[9][4] = Terrain.COLLECTIBLE.getValue();				
			break;	
		case 6:
			// Path
			for (int i = 0; i < 2; i++){
				for (int j = 0; j < 8; j++){
					mapArray[i][j] = Terrain.PATH.getValue();
				}
			}
			for (int i = 4; i < 6; i++){
				for (int j = 0; j < 10; j++){
					mapArray[i][j] = Terrain.PATH.getValue();
				}
			}
			for (int i = 6; i < 10; i++){
				for (int j = 0; j < 10; j++){
					mapArray[i][j] = Terrain.PATH.getValue();
				}
			}
			mapArray[2][1] = Terrain.PATH.getValue();
			mapArray[2][0] = Terrain.PATH.getValue();
			mapArray[5][1] = Terrain.PATH.getValue();
			mapArray[5][0] = Terrain.PATH.getValue();
			
			// Start
			for (int i = 0; i < 2; i++){
				for (int j = 0; j < 2; j++){
					mapArray[i][j] = Terrain.START.getValue();
				}
			}
			
			// Finish
			for (int i = 3; i < 5; i++){
				for (int j = 8; j < 10; j++){
					mapArray[i][j] = Terrain.FINISH.getValue();
				}
			}
			
			// Player
			mapArray[0][0] = Terrain.PLAYER.getValue();
			
			// Enemies
			mapArray[0][5] = Terrain.ENEMY.getValue();
			startLoc = new Location(0, 5);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(1, 5));
			EnemiesList.add(new Enemy(startLoc, moveLoc));
			
			mapArray[0][3] = Terrain.ENEMY.getValue();
			startLoc = new Location(0, 3);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(1, 3));
			EnemiesList.add(new Enemy(startLoc, moveLoc));
			
			mapArray[3][3] = Terrain.ENEMY.getValue();
			startLoc = new Location(3, 3);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(4, 3));
			moveLoc.add(new Location(4, 4));
			moveLoc.add(new Location(3, 4));
			EnemiesList.add(new Enemy(startLoc, moveLoc));
			
			mapArray[3][6] = Terrain.ENEMY.getValue();
			startLoc = new Location(3, 6);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(4, 6));
			moveLoc.add(new Location(4, 7));
			moveLoc.add(new Location(3, 7));
			EnemiesList.add(new Enemy(startLoc, moveLoc));
			
			mapArray[6][0] = Terrain.ENEMY.getValue();
			startLoc = new Location(6, 0);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(6, 1));
			moveLoc.add(new Location(6, 2));
			moveLoc.add(new Location(6, 3));
			moveLoc.add(new Location(6, 4));
			moveLoc.add(new Location(6, 5));
			moveLoc.add(new Location(6, 6));
			moveLoc.add(new Location(6, 7));
			moveLoc.add(new Location(6, 8));
			moveLoc.add(new Location(6, 9));
			moveLoc.add(new Location(6, 8));
			moveLoc.add(new Location(6, 7));
			moveLoc.add(new Location(6, 6));
			moveLoc.add(new Location(6, 5));
			moveLoc.add(new Location(6, 4));
			moveLoc.add(new Location(6, 3));
			moveLoc.add(new Location(6, 2));
			moveLoc.add(new Location(6, 1));
			EnemiesList.add(new Enemy(startLoc, moveLoc));
			
			mapArray[7][9] = Terrain.ENEMY.getValue();
			startLoc = new Location(7, 9);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(7, 8));
			moveLoc.add(new Location(7, 7));
			moveLoc.add(new Location(7, 6));
			moveLoc.add(new Location(7, 5));
			moveLoc.add(new Location(7, 4));
			moveLoc.add(new Location(7, 3));
			moveLoc.add(new Location(7, 2));
			moveLoc.add(new Location(7, 1));
			moveLoc.add(new Location(7, 0));
			moveLoc.add(new Location(7, 1));
			moveLoc.add(new Location(7, 2));
			moveLoc.add(new Location(7, 3));
			moveLoc.add(new Location(7, 4));
			moveLoc.add(new Location(7, 5));
			moveLoc.add(new Location(7, 6));
			moveLoc.add(new Location(7, 7));
			moveLoc.add(new Location(7, 8));
			EnemiesList.add(new Enemy(startLoc, moveLoc));
			
			mapArray[8][0] = Terrain.ENEMY.getValue();
			startLoc = new Location(8, 0);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(8, 1));
			moveLoc.add(new Location(8, 2));
			moveLoc.add(new Location(8, 3));
			moveLoc.add(new Location(8, 4));
			moveLoc.add(new Location(8, 5));
			moveLoc.add(new Location(8, 6));
			moveLoc.add(new Location(8, 7));
			moveLoc.add(new Location(8, 8));
			moveLoc.add(new Location(8, 9));
			moveLoc.add(new Location(8, 8));
			moveLoc.add(new Location(8, 7));
			moveLoc.add(new Location(8, 6));
			moveLoc.add(new Location(8, 5));
			moveLoc.add(new Location(8, 4));
			moveLoc.add(new Location(8, 3));
			moveLoc.add(new Location(8, 2));
			moveLoc.add(new Location(8, 1));
			EnemiesList.add(new Enemy(startLoc, moveLoc));
			
			// Collectibles
			mapArray[0][7] = Terrain.COLLECTIBLE.getValue();
			mapArray[9][0] = Terrain.COLLECTIBLE.getValue();
			break;
		case 7:
			// Path
			for (int i = 0; i < 2; i++){
				for (int j = 0; j < 10; j++){
					mapArray[i][j] = Terrain.PATH.getValue();
				}
			}
			for (int i = 0; i < 10; i++){
				for (int j = 0; j < 3; j++){
					mapArray[i][j] = Terrain.PATH.getValue();
				}
			}
			for (int i = 4; i < 6; i++){
				for (int j = 4; j < 6; j++){
					mapArray[i][j] = Terrain.PATH.getValue();
				}
			}
			for (int i = 0; i < 6; i++){
				for (int j = 4; j < 6; j++){
					mapArray[i][j] = Terrain.PATH.getValue();
				}
			}
			for (int i = 6; i < 8; i++){
				for (int j = 6; j < 8; j++){
					mapArray[i][j] = Terrain.PATH.getValue();
				}
			}
			for (int i = 4; i < 6; i++){
				for (int j = 4; j < 10; j++){
					mapArray[i][j] = Terrain.PATH.getValue();
				}
			}
			
			// Start
			for (int i = 0; i < 2; i++){
				for (int j = 8; j < 10; j++){
					mapArray[i][j] = Terrain.START.getValue();
				}
			}
			
			// Finish
			for (int i = 8; i < 10; i++){
				for (int j = 6; j < 8; j++){
					mapArray[i][j] = Terrain.FINISH.getValue();
				}
			}
			
			// Player 
			mapArray[0][9] = Terrain.PLAYER.getValue();
			
			// Enemies
			mapArray[0][5] = Terrain.ENEMY.getValue();
			startLoc = new Location(0, 5);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(1, 5));
			moveLoc.add(new Location(2, 5));
			moveLoc.add(new Location(3, 5));
			moveLoc.add(new Location(4, 5));
			moveLoc.add(new Location(5, 5));
			moveLoc.add(new Location(4, 5));
			moveLoc.add(new Location(3, 5));
			moveLoc.add(new Location(2, 5));
			moveLoc.add(new Location(1, 5));
			EnemiesList.add(new Enemy(startLoc, moveLoc));
			
			mapArray[5][4] = Terrain.ENEMY.getValue();
			startLoc = new Location(5, 4);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(4, 4));
			moveLoc.add(new Location(3, 4));
			moveLoc.add(new Location(2, 4));
			moveLoc.add(new Location(1, 4));
			moveLoc.add(new Location(0, 4));
			moveLoc.add(new Location(1, 4));
			moveLoc.add(new Location(2, 4));
			moveLoc.add(new Location(3, 4));
			EnemiesList.add(new Enemy(startLoc, moveLoc));
			
			mapArray[1][3] = Terrain.ENEMY.getValue();
			startLoc = new Location(1, 3);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(0, 3));
			EnemiesList.add(new Enemy(startLoc, moveLoc));
			
			mapArray[4][8] = Terrain.ENEMY.getValue();
			startLoc = new Location(4, 8);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(5, 8));
			EnemiesList.add(new Enemy(startLoc, moveLoc));
			
			mapArray[6][7] = Terrain.ENEMY.getValue();
			startLoc = new Location(6, 7);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(6, 6));
			EnemiesList.add(new Enemy(startLoc, moveLoc));
			
			mapArray[3][2] = Terrain.ENEMY.getValue();
			startLoc = new Location(3, 2);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(3, 1));
			moveLoc.add(new Location(3, 0));
			moveLoc.add(new Location(3, 1));
			EnemiesList.add(new Enemy(startLoc, moveLoc));
			
			mapArray[5][2] = Terrain.ENEMY.getValue();
			startLoc = new Location(5, 2);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(5, 1));
			moveLoc.add(new Location(5, 0));
			moveLoc.add(new Location(5, 1));
			EnemiesList.add(new Enemy(startLoc, moveLoc));
			
			mapArray[7][2] = Terrain.ENEMY.getValue();
			startLoc = new Location(7, 2);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(7, 1));
			moveLoc.add(new Location(7, 0));
			moveLoc.add(new Location(7, 1));
			EnemiesList.add(new Enemy(startLoc, moveLoc));

			// Collectibles
			mapArray[4][9] = Terrain.COLLECTIBLE.getValue();
			mapArray[9][2] = Terrain.COLLECTIBLE.getValue();
			break;
			
		case 8:
			// Path
			for (int i = 0; i < 2; i++){
				for (int j = 2; j < 4; j++){
					mapArray[i][j] = Terrain.PATH.getValue();
				}
			}
			for (int i = 1; i < 3; i++){
				for (int j = 4; j < 6; j++){
					mapArray[i][j] = Terrain.PATH.getValue();
				}
			}
			for (int i = 2; i < 4; i++){
				for (int j = 6; j < 8; j++){
					mapArray[i][j] = Terrain.PATH.getValue();
				}
			}
			for (int i = 3; i < 10; i++){
				for (int j = 8; j < 10; j++){
					mapArray[i][j] = Terrain.PATH.getValue();
				}
			}
			for (int i = 5; i < 7; i++){
				for (int j = 0; j < 10; j++){
					mapArray[i][j] = Terrain.PATH.getValue();
				}
			}
			for (int i = 8; i < 10; i++){
				for (int j = 0; j < 10; j++){
					mapArray[i][j] = Terrain.PATH.getValue();
				}
			}
			
			// Start
			for (int i = 0; i < 2; i++){
				for (int j = 0; j < 2; j++){
					mapArray[i][j] = Terrain.START.getValue();
				}
			}
			
			// Finish
			for (int i = 8; i < 10; i++){
				for (int j = 0; j < 2; j++){
					mapArray[i][j] = Terrain.FINISH.getValue();
				}
			}
			
			// Player 
			mapArray[0][0] = Terrain.PLAYER.getValue();
			
			// Enemies
			mapArray[0][3] = Terrain.ENEMY.getValue();
			startLoc = new Location(0, 3);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(1, 3));
			EnemiesList.add(new Enemy(startLoc, moveLoc));
			
			mapArray[2][5] = Terrain.ENEMY.getValue();
			startLoc = new Location(2, 5);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(1, 5));
			EnemiesList.add(new Enemy(startLoc, moveLoc));
			
			mapArray[2][7] = Terrain.ENEMY.getValue();
			startLoc = new Location(2, 7);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(3, 7));
			EnemiesList.add(new Enemy(startLoc, moveLoc));
			
			mapArray[5][9] = Terrain.ENEMY.getValue();
			startLoc = new Location(5, 9);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(5, 8));
			moveLoc.add(new Location(5, 7));
			moveLoc.add(new Location(5, 6));
			moveLoc.add(new Location(5, 5));
			moveLoc.add(new Location(5, 4));
			moveLoc.add(new Location(5, 3));
			moveLoc.add(new Location(5, 2));
			moveLoc.add(new Location(5, 1));
			moveLoc.add(new Location(5, 2));
			moveLoc.add(new Location(5, 3));
			moveLoc.add(new Location(5, 4));
			moveLoc.add(new Location(5, 5));
			moveLoc.add(new Location(5, 6));
			moveLoc.add(new Location(5, 7));
			moveLoc.add(new Location(5, 8));
			EnemiesList.add(new Enemy(startLoc, moveLoc));
			
			mapArray[6][1] = Terrain.ENEMY.getValue();
			startLoc = new Location(6, 1);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(6, 2));
			moveLoc.add(new Location(6, 3));
			moveLoc.add(new Location(6, 4));
			moveLoc.add(new Location(6, 5));
			moveLoc.add(new Location(6, 6));
			moveLoc.add(new Location(6, 7));
			moveLoc.add(new Location(6, 8));
			moveLoc.add(new Location(6, 9));
			moveLoc.add(new Location(6, 8));
			moveLoc.add(new Location(6, 7));
			moveLoc.add(new Location(6, 6));
			moveLoc.add(new Location(6, 5));
			moveLoc.add(new Location(6, 4));
			moveLoc.add(new Location(6, 3));
			moveLoc.add(new Location(6, 2));
			EnemiesList.add(new Enemy(startLoc, moveLoc));
			
			mapArray[7][7] = Terrain.ENEMY.getValue();
			startLoc = new Location(7, 7);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(7, 8));
			moveLoc.add(new Location(7, 9));
			moveLoc.add(new Location(7, 8));
			EnemiesList.add(new Enemy(startLoc, moveLoc));
			
			mapArray[8][6] = Terrain.ENEMY.getValue();
			startLoc = new Location(8, 6);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(9, 6));
			EnemiesList.add(new Enemy(startLoc, moveLoc));
			
			mapArray[9][4] = Terrain.ENEMY.getValue();
			startLoc = new Location(9, 4);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(8, 4));
			EnemiesList.add(new Enemy(startLoc, moveLoc));
			
			mapArray[8][2] = Terrain.ENEMY.getValue();
			startLoc = new Location(8, 2);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(9, 2));
			EnemiesList.add(new Enemy(startLoc, moveLoc));
			
			//Collectibles
			mapArray[5][0] = Terrain.COLLECTIBLE.getValue();
			
			break;
		case 9:
			// Path
			for (int i = 0; i < 2; i++){
				for (int j = 0; j < 10; j++){
					mapArray[i][j] = Terrain.PATH.getValue();
				}
			}
			for (int i = 0; i < 6; i++){
				for (int j = 0; j < 3; j++){
					mapArray[i][j] = Terrain.PATH.getValue();
				}
			}
			for (int i = 6; i < 8; i++){
				for (int j = 0; j < 2; j++){
					mapArray[i][j] = Terrain.PATH.getValue();
				}
			}
			for (int i = 3; i < 6; i++){
				for (int j = 0; j < 10; j++){
					mapArray[i][j] = Terrain.PATH.getValue();
				}
			}
			for (int i = 6; i < 8; i++){
				for (int j = 8; j < 10; j++){
					mapArray[i][j] = Terrain.PATH.getValue();
				}
			}
			for (int i = 8; i < 10; i++){
				for (int j = 3; j < 10; j++){
					mapArray[i][j] = Terrain.PATH.getValue();
				}
			}
			
			// Start
			for (int i = 0; i < 2; i++){
				for (int j = 8; j < 10; j++){
					mapArray[i][j] = Terrain.START.getValue();
				}
			}
			
			// Finish
			for (int i = 8; i < 10; i++){
				for (int j = 0; j < 2; j++){
					mapArray[i][j] = Terrain.FINISH.getValue();
				}
			}
			
			// Player 
			mapArray[0][9] = Terrain.PLAYER.getValue();
			
			// Enemies
			mapArray[0][6] = Terrain.ENEMY.getValue();
			startLoc = new Location(0, 6);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(1, 6));
			EnemiesList.add(new Enemy(startLoc, moveLoc));
			
			mapArray[0][4] = Terrain.ENEMY.getValue();
			startLoc = new Location(0, 4);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(1, 4));
			EnemiesList.add(new Enemy(startLoc, moveLoc));	

			mapArray[0][2] = Terrain.ENEMY.getValue();
			startLoc = new Location(0, 2);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(1, 2));
			moveLoc.add(new Location(2, 2));
			moveLoc.add(new Location(3, 2));
			moveLoc.add(new Location(4, 2));
			moveLoc.add(new Location(5, 2));
			moveLoc.add(new Location(4, 2));
			moveLoc.add(new Location(3, 2));
			moveLoc.add(new Location(2, 2));
			moveLoc.add(new Location(1, 2));
			EnemiesList.add(new Enemy(startLoc, moveLoc));

			mapArray[1][1] = Terrain.ENEMY.getValue();
			startLoc = new Location(1, 1);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(1, 0));
			EnemiesList.add(new Enemy(startLoc, moveLoc));
			
			mapArray[3][0] = Terrain.ENEMY.getValue();
			startLoc = new Location(3, 0);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(3, 1));
			EnemiesList.add(new Enemy(startLoc, moveLoc));

			mapArray[7][1] = Terrain.ENEMY.getValue();
			startLoc = new Location(7, 1);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(7, 0));
			EnemiesList.add(new Enemy(startLoc, moveLoc));

			mapArray[3][4] = Terrain.ENEMY.getValue();
			startLoc = new Location(3, 4);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(4, 4));
			moveLoc.add(new Location(5, 4));
			moveLoc.add(new Location(5, 5));
			moveLoc.add(new Location(5, 6));
			moveLoc.add(new Location(4, 6));
			moveLoc.add(new Location(3, 6));
			moveLoc.add(new Location(3, 5));
			EnemiesList.add(new Enemy(startLoc, moveLoc));
			
			mapArray[3][7] = Terrain.ENEMY.getValue();
			startLoc = new Location(3, 7);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(4, 7));
			moveLoc.add(new Location(5, 7));
			moveLoc.add(new Location(5, 8));
			moveLoc.add(new Location(5, 9));
			moveLoc.add(new Location(4, 9));
			moveLoc.add(new Location(3, 9));
			moveLoc.add(new Location(3, 8));
			EnemiesList.add(new Enemy(startLoc, moveLoc));
			
			mapArray[8][9] = Terrain.ENEMY.getValue();
			startLoc = new Location(8, 9);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(8, 8));
			moveLoc.add(new Location(8, 7));
			moveLoc.add(new Location(8, 6));
			moveLoc.add(new Location(8, 5));
			moveLoc.add(new Location(8, 4));
			moveLoc.add(new Location(8, 5));
			moveLoc.add(new Location(8, 6));
			moveLoc.add(new Location(8, 7));
			moveLoc.add(new Location(8, 8));
			EnemiesList.add(new Enemy(startLoc, moveLoc));
			
			mapArray[8][9] = Terrain.ENEMY.getValue();
			startLoc = new Location(9, 4);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(9, 5));
			moveLoc.add(new Location(9, 6));
			moveLoc.add(new Location(9, 7));
			moveLoc.add(new Location(9, 8));
			moveLoc.add(new Location(9, 9));
			moveLoc.add(new Location(9, 8));
			moveLoc.add(new Location(9, 7));
			moveLoc.add(new Location(9, 6));
			moveLoc.add(new Location(9, 5));
			EnemiesList.add(new Enemy(startLoc, moveLoc));
			
			// Collectibles
			mapArray[8][3] = Terrain.COLLECTIBLE.getValue();
			
			break;
		case 10:
			// Path
			for (int i = 0; i < 10; i++){
				for (int j = 0; j < 10; j++){
					mapArray[i][j] = Terrain.PATH.getValue();
				}
			}
			
			// Start
			for (int i = 0; i < 2; i++){
				for (int j = 8; j < 10; j++){
					mapArray[i][j] = Terrain.START.getValue();
				}
			}
			
			// Finish
			for (int i = 8; i < 10; i++){
				for (int j = 0; j < 2; j++){
					mapArray[i][j] = Terrain.FINISH.getValue();
				}
			}
			
			// Player 
			mapArray[0][9] = Terrain.PLAYER.getValue();
			
			// Enemies
			mapArray[0][7] = Terrain.ENEMY.getValue();
			startLoc = new Location(0, 7);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(1, 7));
			moveLoc.add(new Location(2, 7));
			moveLoc.add(new Location(3, 7));
			moveLoc.add(new Location(4, 7));
			moveLoc.add(new Location(5, 7));
			moveLoc.add(new Location(6, 7));
			moveLoc.add(new Location(7, 7));
			moveLoc.add(new Location(8, 7));
			moveLoc.add(new Location(9, 7));
			moveLoc.add(new Location(8, 7));
			moveLoc.add(new Location(7, 7));
			moveLoc.add(new Location(6, 7));
			moveLoc.add(new Location(5, 7));
			moveLoc.add(new Location(4, 7));
			moveLoc.add(new Location(3, 7));
			moveLoc.add(new Location(2, 7));
			moveLoc.add(new Location(1, 7));
			EnemiesList.add(new Enemy(startLoc, moveLoc));
			
			mapArray[0][5] = Terrain.ENEMY.getValue();
			startLoc = new Location(0, 5);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(1, 5));
			moveLoc.add(new Location(2, 5));
			moveLoc.add(new Location(3, 5));
			moveLoc.add(new Location(4, 5));
			moveLoc.add(new Location(5, 5));
			moveLoc.add(new Location(6, 5));
			moveLoc.add(new Location(7, 5));
			moveLoc.add(new Location(8, 5));
			moveLoc.add(new Location(9, 5));
			moveLoc.add(new Location(8, 5));
			moveLoc.add(new Location(7, 5));
			moveLoc.add(new Location(6, 5));
			moveLoc.add(new Location(5, 5));
			moveLoc.add(new Location(4, 5));
			moveLoc.add(new Location(3, 5));
			moveLoc.add(new Location(2, 5));
			moveLoc.add(new Location(1, 5));
			EnemiesList.add(new Enemy(startLoc, moveLoc));
			
			mapArray[0][3] = Terrain.ENEMY.getValue();
			startLoc = new Location(0, 3);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(1, 3));
			moveLoc.add(new Location(2, 3));
			moveLoc.add(new Location(3, 3));
			moveLoc.add(new Location(4, 3));
			moveLoc.add(new Location(5, 3));
			moveLoc.add(new Location(6, 3));
			moveLoc.add(new Location(7, 3));
			moveLoc.add(new Location(8, 3));
			moveLoc.add(new Location(9, 3));
			moveLoc.add(new Location(8, 3));
			moveLoc.add(new Location(7, 3));
			moveLoc.add(new Location(6, 3));
			moveLoc.add(new Location(5, 3));
			moveLoc.add(new Location(4, 3));
			moveLoc.add(new Location(3, 3));
			moveLoc.add(new Location(2, 3));
			moveLoc.add(new Location(1, 3));
			EnemiesList.add(new Enemy(startLoc, moveLoc));
			
			mapArray[0][1] = Terrain.ENEMY.getValue();
			startLoc = new Location(0, 1);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(1, 1));
			moveLoc.add(new Location(2, 1));
			moveLoc.add(new Location(3, 1));
			moveLoc.add(new Location(4, 1));
			moveLoc.add(new Location(5, 1));
			moveLoc.add(new Location(6, 1));
			moveLoc.add(new Location(7, 1));
			moveLoc.add(new Location(6, 1));
			moveLoc.add(new Location(5, 1));
			moveLoc.add(new Location(4, 1));
			moveLoc.add(new Location(3, 1));
			moveLoc.add(new Location(2, 1));
			moveLoc.add(new Location(1, 1));
			EnemiesList.add(new Enemy(startLoc, moveLoc));
			
			mapArray[3][9] = Terrain.ENEMY.getValue();
			startLoc = new Location(3, 9);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(3, 8));
			moveLoc.add(new Location(3, 7));
			moveLoc.add(new Location(3, 6));
			moveLoc.add(new Location(3, 5));
			moveLoc.add(new Location(3, 4));
			moveLoc.add(new Location(3, 3));
			moveLoc.add(new Location(3, 2));
			moveLoc.add(new Location(3, 1));
			moveLoc.add(new Location(3, 0));
			moveLoc.add(new Location(3, 1));
			moveLoc.add(new Location(3, 2));
			moveLoc.add(new Location(3, 3));
			moveLoc.add(new Location(3, 4));
			moveLoc.add(new Location(3, 5));
			moveLoc.add(new Location(3, 6));
			moveLoc.add(new Location(3, 7));
			moveLoc.add(new Location(3, 8));
			EnemiesList.add(new Enemy(startLoc, moveLoc));
			
			mapArray[5][9] = Terrain.ENEMY.getValue();
			startLoc = new Location(5, 9);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(5, 8));
			moveLoc.add(new Location(5, 7));
			moveLoc.add(new Location(5, 6));
			moveLoc.add(new Location(5, 5));
			moveLoc.add(new Location(5, 4));
			moveLoc.add(new Location(5, 3));
			moveLoc.add(new Location(5, 2));
			moveLoc.add(new Location(5, 1));
			moveLoc.add(new Location(5, 0));
			moveLoc.add(new Location(5, 1));
			moveLoc.add(new Location(5, 2));
			moveLoc.add(new Location(5, 3));
			moveLoc.add(new Location(5, 4));
			moveLoc.add(new Location(5, 5));
			moveLoc.add(new Location(5, 6));
			moveLoc.add(new Location(5, 7));
			moveLoc.add(new Location(5, 8));
			EnemiesList.add(new Enemy(startLoc, moveLoc));
			
			mapArray[7][9] = Terrain.ENEMY.getValue();
			startLoc = new Location(7, 9);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(7, 8));
			moveLoc.add(new Location(7, 7));
			moveLoc.add(new Location(7, 6));
			moveLoc.add(new Location(7, 5));
			moveLoc.add(new Location(7, 4));
			moveLoc.add(new Location(7, 3));
			moveLoc.add(new Location(7, 2));
			moveLoc.add(new Location(7, 1));
			moveLoc.add(new Location(7, 0));
			moveLoc.add(new Location(7, 1));
			moveLoc.add(new Location(7, 2));
			moveLoc.add(new Location(7, 3));
			moveLoc.add(new Location(7, 4));
			moveLoc.add(new Location(7, 5));
			moveLoc.add(new Location(7, 6));
			moveLoc.add(new Location(7, 7));
			moveLoc.add(new Location(7, 8));
			EnemiesList.add(new Enemy(startLoc, moveLoc));
			
			mapArray[9][9] = Terrain.ENEMY.getValue();
			startLoc = new Location(9, 9);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(9, 8));
			moveLoc.add(new Location(9, 7));
			moveLoc.add(new Location(9, 6));
			moveLoc.add(new Location(9, 5));
			moveLoc.add(new Location(9, 4));
			moveLoc.add(new Location(9, 3));
			moveLoc.add(new Location(9, 2));
			moveLoc.add(new Location(9, 2));
			moveLoc.add(new Location(9, 3));
			moveLoc.add(new Location(9, 4));
			moveLoc.add(new Location(9, 5));
			moveLoc.add(new Location(9, 6));
			moveLoc.add(new Location(9, 7));
			moveLoc.add(new Location(9, 8));
			EnemiesList.add(new Enemy(startLoc, moveLoc));
			
			mapArray[2][8] = Terrain.ENEMY.getValue();
			startLoc = new Location(2, 8);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(3, 8));
			moveLoc.add(new Location(4, 8));
			moveLoc.add(new Location(4, 7));
			moveLoc.add(new Location(4, 6));
			moveLoc.add(new Location(3, 6));
			moveLoc.add(new Location(2, 6));
			moveLoc.add(new Location(2, 7));
			EnemiesList.add(new Enemy(startLoc, moveLoc));
			
			mapArray[2][6] = Terrain.ENEMY.getValue();
			startLoc = new Location(2, 6);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(3, 6));
			moveLoc.add(new Location(4, 6));
			moveLoc.add(new Location(4, 5));
			moveLoc.add(new Location(4, 4));
			moveLoc.add(new Location(3, 4));
			moveLoc.add(new Location(2, 4));
			moveLoc.add(new Location(2, 5));
			EnemiesList.add(new Enemy(startLoc, moveLoc));
			
			mapArray[2][4] = Terrain.ENEMY.getValue();
			startLoc = new Location(2, 4);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(3, 4));
			moveLoc.add(new Location(4, 4));
			moveLoc.add(new Location(4, 3));
			moveLoc.add(new Location(4, 2));
			moveLoc.add(new Location(3, 2));
			moveLoc.add(new Location(2, 2));
			moveLoc.add(new Location(2, 3));
			EnemiesList.add(new Enemy(startLoc, moveLoc));
			
			mapArray[2][2] = Terrain.ENEMY.getValue();
			startLoc = new Location(2, 2);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(3, 2));
			moveLoc.add(new Location(4, 2));
			moveLoc.add(new Location(4, 1));
			moveLoc.add(new Location(4, 0));
			moveLoc.add(new Location(3, 0));
			moveLoc.add(new Location(2, 0));
			moveLoc.add(new Location(2, 1));
			EnemiesList.add(new Enemy(startLoc, moveLoc));
			
			mapArray[6][8] = Terrain.ENEMY.getValue();
			startLoc = new Location(6, 8);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(7, 8));
			moveLoc.add(new Location(8, 8));
			moveLoc.add(new Location(8, 7));
			moveLoc.add(new Location(8, 6));
			moveLoc.add(new Location(7, 6));
			moveLoc.add(new Location(6, 6));
			moveLoc.add(new Location(6, 7));
			EnemiesList.add(new Enemy(startLoc, moveLoc));
			
			mapArray[6][6] = Terrain.ENEMY.getValue();
			startLoc = new Location(6, 6);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(7, 6));
			moveLoc.add(new Location(8, 6));
			moveLoc.add(new Location(8, 5));
			moveLoc.add(new Location(8, 4));
			moveLoc.add(new Location(7, 4));
			moveLoc.add(new Location(6, 4));
			moveLoc.add(new Location(6, 5));
			EnemiesList.add(new Enemy(startLoc, moveLoc));
			
			mapArray[6][4] = Terrain.ENEMY.getValue();
			startLoc = new Location(6, 4);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(7, 4));
			moveLoc.add(new Location(8, 4));
			moveLoc.add(new Location(8, 3));
			moveLoc.add(new Location(8, 2));
			moveLoc.add(new Location(7, 2));
			moveLoc.add(new Location(6, 2));
			moveLoc.add(new Location(6, 3));
			EnemiesList.add(new Enemy(startLoc, moveLoc));
			
			mapArray[6][2] = Terrain.ENEMY.getValue();
			startLoc = new Location(6, 2);
			moveLoc = new ArrayList<Location>();
			moveLoc.add(startLoc);
			moveLoc.add(new Location(7, 2));
			moveLoc.add(new Location(8, 2));
			moveLoc.add(new Location(9, 2));
			moveLoc.add(new Location(8, 2));
			moveLoc.add(new Location(7, 2));
			EnemiesList.add(new Enemy(startLoc, moveLoc));
			
			// Collectibles
			mapArray[4][7] = Terrain.COLLECTIBLE.getValue();
			mapArray[6][7] = Terrain.COLLECTIBLE.getValue();
			mapArray[5][5] = Terrain.COLLECTIBLE.getValue();
			mapArray[3][3] = Terrain.COLLECTIBLE.getValue();
			mapArray[7][3] = Terrain.COLLECTIBLE.getValue();
			mapArray[0][0] = Terrain.COLLECTIBLE.getValue();
			
			break;
		default:
			break;			
		}
		return mapArray;
		
	}
	
	//               encodedRepresentation
	public String getEncodedRepresentation() {
		Terrain[] values = Terrain.values();
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < GRID_SIZE; i++) {
			for (int j = 0; j < GRID_SIZE; j++) {
				Terrain t = values[mapArray[i][j]];
				buf.append(t.toChar());
			}
		}
		return buf.toString();
	}
}
