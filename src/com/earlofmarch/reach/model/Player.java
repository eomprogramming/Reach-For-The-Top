package com.earlofmarch.reach.model;

import java.util.LinkedList;

import com.earlofmarch.reach.input.Pair;
/**
 * A reach for the top player!
 * @author Aly
 *
 */
public class Player {
	
	private String name;
	private LinkedList<Pair<Integer,String>> scoreHistory;
	private int score;
	public static final String PLACEHOLDER = "DAMNBUGGYAPP";
	
	/**
	 * Creates a new Player with the name provided, default score is 0.
	 * @param name	The player's name
	 */
	public Player(String name)
	{
		scoreHistory = new LinkedList<Pair<Integer,String>>();
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
		scoreHistory.add(new Pair<Integer,String>(s, null));
	}
	
	/**
	 * Adds a score received in a given game to a Player's score.
	 * @param s The score to be added.
	 * @param g The game in which it was added (machine readable name).
	 */
	public void addToHistory(int s, String g) {
		scoreHistory.add(new Pair<Integer,String>(s, g));
	}
	
	/**
	 * Returns the player's full history of scores.
	 * @return	LinkedList of scores
	 */
	public LinkedList<Integer> getScoreHistory()
	{
		LinkedList<Integer> res = new LinkedList<Integer>();
		for (Pair<Integer,String> p: scoreHistory)
			res.add(p.getFirst());
		return res;
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