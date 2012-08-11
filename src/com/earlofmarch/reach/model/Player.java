package com.earlofmarch.reach.model;

import java.util.LinkedList;
/**
 * A reach for the top player!
 * @author Aly
 *
 */
public class Player {
	
	private String name;
	private LinkedList<Integer> scoreHistory;
	private int score;
	
	/**
	 * Creates a new Player with the name provided, default score is 0.
	 * @param name	The player's name
	 */
	public Player(String name)
	{
		scoreHistory = new LinkedList<Integer>();
		score = 0;
		setName(name);
	}
	
	/**
	 * Returns the player's name
	 * @return The name
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Sets the player's name
	 * @param name	The player's new name
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	
	/**
	 * Adds points to the player's current score. 
	 * To add to a player's history of scores, use the {@link #addToHistory(int s) addToHistory} method.
	 * @param s	The points to be added to a player's score.
	 */
	public void addScore(int s){
		score += s;
	}
	
	/**
	 * Returns the player's current score
	 * @return	The player's score
	 */
	public int getScore(){
		return score;
	}
	
	/**
	 * Adds a score to a player's history of scores.
	 * @param s	The score to be added
	 */
	public void addToHistory(int s)
	{
		scoreHistory.add(s);
	}
	
	/**
	 * Returns the player's full history of scores.
	 * @return	LinkedList of scores
	 */
	public LinkedList<Integer> getScoreHistory()
	{
		return scoreHistory;
	}
	
	@Override
	public boolean equals(Object o){
		if(o instanceof Player){
			Player p = (Player) o;
			return this.name.equalsIgnoreCase(p.getName());
		}
		return false;
	}
}
