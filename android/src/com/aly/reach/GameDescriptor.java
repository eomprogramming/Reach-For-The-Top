package com.aly.reach;

/**
 * Describe an open Reach network game. Immutable.
 * @author Ian Dewan
 */
public class GameDescriptor {
	private String name;
	private int[] open;

	/**
	 * Create a new GameDescriptor.
	 * @param name The name of the game.
	 * @param open An array; element <code>i</code> is the number of
	 * open spots on team <code>i</code>.
	 */
	public GameDescriptor(String name, int[] open) {
		if (name == null)
			this.name = "";
		else
			this.name = name;
		
		if (open == null)
			this.open = new int[] {};
		else
			this.open = open;
	}
	
	public String getName() {
		return name;
	}
	
	public int openSpots(int team) {
		return open[team];
	}
	
	public int numTeams() {
		return open.length;
	}
}
