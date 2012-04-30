package edu.ycp.cs496.model;

public class The_Superclass {
	private Location loc;
	private int speed;
	
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
}
