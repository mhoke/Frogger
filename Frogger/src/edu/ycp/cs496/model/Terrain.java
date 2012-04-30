package edu.ycp.cs496.model;

public enum Terrain 
{
	BLANK (0), PLAYER (1), COLLECTIBLE (2), ENEMY (3), START (4), FINISH (5), PATH (6);
	
	private static String ENCODED_TERRAIN = "abcdefg"; 
	
	private int value;
	
	private Terrain(int value)
	{
		this.value = value;
	}
	
	public int getValue()
	{
		return value;
	}
	
	public char toChar() {
		return ENCODED_TERRAIN.charAt(this.ordinal());
	}
	
	public Terrain fromChar(char c) {
		Terrain[] values = Terrain.values();
		for (int i = 0; i < ENCODED_TERRAIN.length(); i++) {
			if (c == ENCODED_TERRAIN.charAt(i)) {
				return values[i];
			}
		}
		throw new IllegalArgumentException("Unknown terrain character: " + c);
	}
}
