package com.earlofmarch.reach.model;

public class Player {
	private String name;
	private int score, timesPlayed;
	
	public Player(String name)
	{
		setName(name);
		resetScore();
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void resetScore()
	{
		score = 0;
	}
	
	public void increaseScore(int a)
	{
		score += a;
	}
	
	public int getScore()
	{
		return score;
	}
	
	public void addPlay(){
		timesPlayed++;
		
	}
	
	public int getTimesPlayed(){
		return timesPlayed;		
	}
	/**
	 * if file with student name does not exist
	 * {
	 * 		create a new name.reach files and open it
	 * }
	 * else
	 * {
	 * 		open existing name.reach files
	 * }
	 * 
	 * write out the score and update the number or rounds played and the total score for the student
	 * include the data
	 * 
	 * follow the format in the example .reach files
	 * close file
	 */
	public void saveScore()
	{
		
	}
}
