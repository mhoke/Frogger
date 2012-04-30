package edu.ycp.cs496.model;

public class Player extends The_Superclass{
	private Location loc;
	private int speed;
	private int prevVal;
	
	public Player(Location loc){
		this.loc = loc;
	}
	
	public void setLocation(Location loc){
		this.loc = loc;
	}
	
	public void setSpeed(int speed){
		this.speed = speed;
	}
	
	public Location getLocation(){
		return loc;
	}
	
	public int getSpeed(){
		return speed;
	}
	
	public void setPrevVal(int i)
	{
		prevVal = i;
	}
	
	public int getPrevVal()
	{
		return prevVal;
	}
	
	public Location move(int key1,int key2){
		if ((key1 == 37 && key2 == 39) || (key1 == 39 && key2 == 37)) {
			// user hit both left and right
			// do nothing
		}
		else if ((key1 == 38 && key2 == 40) || (key1 == 40 && key2 == 38)){
			// user hit both up and down
			// do nothing
		}
		else {
			processKey(key1);
			processKey(key2);
		}
		return loc;
	}
	
	public boolean equals(Player player){
		if (loc.equals(player.getLocation())){
			return true;
		}
		return false;
	}
	
	private void processKey(int key) {
		// update player location based on keys pressed
		// left = 37, up = 38, right = 39, and down = 40
		// no key was pressed
		if (key == 0){
			// do nothing
		}
		// up
		else if (key == 38){
			loc.setY(loc.getY() - speed);
		}
		// down
		else if (key == 40){
			loc.setY(loc.getY() + speed);
		}
		// left
		else if (key == 37){
			loc.setX(loc.getX() - speed);
		}
		// right
		else if (key == 39){
			loc.setX(loc.getX() + speed);
		}

	}
}
