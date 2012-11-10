/*
 * Game.java - (c) 2012 Ian Dewan
 * Licensed under the GNU GPL v3
 * A copy may be found at http://www.gnu.org/licenses/gpl.html
 * This software comes with NO WARRANTY, even the implied
 * warranty of blah, blah, blah.
 */
/**
 * 
 */
package com.earlofmarch.reach.model;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/**
 * Represents one reach game.
 * @author Ian Dewan
 *
 */
public class Game {
	public static final int TEAMA = 0;
	public static final int TEAMB = 1;
	
	private String pack;
	private GregorianCalendar date;
	private List<Player> teama;
	private List<Player> teamb;
	private int scorea;
	private int scoreb;
	
	/**
	 * Create a new game with the given parameters.
	 * @param pack The name of the pack played.
	 * @param date The date of the game.
	 * @param teama A list of players in team A; elements are allowed to be null.
	 * @param teamb A list of players in team B; elements are allowed to be null.
	 * @param scorea The score of team A.
	 * @param scoreb The score of team B.
	 * @throws IllegalArgumentException if there's a tab or period in the pack name, or
	 * not four players in each team.
	 */
	protected Game(String pack, GregorianCalendar date, List<Player> teama,
			List<Player> teamb, int scorea, int scoreb) {
		if (pack.contains("\t") || pack.contains("."))
			throw new IllegalArgumentException("Invalid character in pack name.");
		this.pack = pack;
		this.date = date;
		if ((teama.size() != 4) || (teamb.size() != 4))
			throw new IllegalArgumentException("Must be four players on each team");
		this.teama = teama;
		this.teamb = teamb;
		this.scorea = scorea;
		this.scoreb = scoreb;
	}
	
	/**
	 * Create a new game from the given String, formatted as the output from
	 * machineName().
	 * @param s The String to parse.
	 * @param teama A list of players in team A; elements are allowed to be null.
	 * @param teamb A list of players in team B; elements are allowed to be null.
	 * @param scorea The score of team A.
	 * @param scoreb The score of team B.
	 * @throws IllegalArgumentException if there's a tab in the pack name or not four
	 * players in each team.
	 * @throws ArrayIndexOutOfBoundsException malformed String
	 */
	protected Game(String s, List<Player> teama, List<Player> teamb, int scorea,
		int scoreb) {
		String parts[] = s.split("\\.");
		pack = parts[0];
		if (pack.contains("\t"))
			throw new IllegalArgumentException("No tabs in pack names.");
		date = parseISO(parts[1]);
		if ((teama.size() != 4) || (teamb.size() != 4))
			throw new IllegalArgumentException("Must be four players on each team");
		this.teama = teama;
		this.teamb = teamb;
		this.scorea = scorea;
		this.scoreb = scoreb;
	}
	
	/**
	 * Human readable name for a game.
	 * @return The name
	 */
	public String humanName() {
		return "\"" + pack + "\" (" +
			date.getDisplayName(Calendar.DAY_OF_MONTH, Calendar.SHORT, Locale.CANADA) +
			" " +
			date.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.CANADA) + " " +
			date.getDisplayName(Calendar.YEAR, Calendar.SHORT, Locale.CANADA) + ")";
	}
	
	/**
	 * Return a machine-readable name for the game.
	 * @return The game name.
	 */
	public String machineName() {
		return pack + "." + isoDate(date);
	}
	
	/**
	 * Return an ISO formatted date from a calendar (ignores time component).
	 * @param c The calendar to format.
	 * @return The ISO formatted date.
	 */
	private static String isoDate(Calendar c) {
		return c.get(Calendar.YEAR) + "-" + c.get(Calendar.MONTH) + "-" +
				c.get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * Turn an ISO formatted date (no time portion) into a GregorianCalendar.
	 * @param s The String to parse.
	 * @return The corresponding date.
	 * @throws ArrayIndexOutofBoundsException malformed input
	 */
	private static GregorianCalendar parseISO(String s) {
		String[] parts = s.split("-");
		return new GregorianCalendar(Integer.parseInt(parts[0]),
				Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
	}
}