package edu.ycp.cs496.model;

public class Collectible extends The_Superclass{
	private Location loc;
	
	public Collectible(Location loc){
		this.loc = loc;
	}
	
	public void setLocation(Location loc){
		this.loc = loc;
	}
	
	public Location getLocation(){
		return loc;
	}
	
	public boolean equals(Collectible collectible){
		if (loc.equals(collectible.getLocation())){
			return true;
		}
		return false;
	}
}
