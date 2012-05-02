package edu.ycp.cs496.model;

public class Collectible extends The_Superclass{
	private Location loc;
	private boolean visible;
	
	public Collectible(Location loc){
		this.loc = loc;
		visible = true;
	}
	
	public void setLocation(Location loc){
		this.loc = loc;
	}
	
	public Location getLocation(){
		return loc;
	}
	
	public void setVisible(boolean val)
	{
		visible = val;
	}
	
	public boolean getVisible()
	{
		return visible;
	}
	
	public boolean equals(Collectible collectible){
		if (loc.equals(collectible.getLocation())){
			return true;
		}
		return false;
	}
}
