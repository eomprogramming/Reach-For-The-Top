/*
 * GameIO.java - (c) 2012 Ian Dewan
 * Licensed under the GNU GPL v3
 * A copy may be found at http://www.gnu.org/licenses/gpl.html
 * This software comes with NO WARRANTY, even the implied
 * warranty of blah, blah, blah.
 */
package com.earlofmarch.reach.model;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Get games from the saved data.
 * The game files should be in the format:
 * <ul>
 * <li><strong>Line 1:</strong> scorea:scoreb</li>
 * <li><strong>Line 2:</strong> comma separated list of players on team a</li>
 * <li><strong>Line 3:</strong> comma separated list of players on team b</li>
 * </ul>
 * @author Ian Dewan
 *
 */
public class GameIO {
	private static final String ROOT = System.getProperty("user.home") +
			"/.reach/games";
	
	/**
	 * Return human readable names and machine readable names for all stored games.
	 * @return A Map, the keys of which are human names and the values machine names.
	 */
	public static Map<String,String> getGameNames() {
		String tmp;
		File dir = new File(ROOT);
		HashMap<String,String> names = new HashMap<String,String>();
		
		if (dir.isDirectory()) {
			File[] files = dir.listFiles();
			
			for (File f: files) {
				tmp = f.getName();
				names.put(Game.machineNameToHuman(tmp), tmp);
			}
			
			return names;
		} else {
			System.err.println("The games archive " + ROOT + " is not a directory.");
			return names; // empty: no files.
		}
	}
	
	/**
	 * Get a Game based on its machine readable name.
	 * @param name the name
	 * @return the game
	 * @throws IOException Something goes wrong
	 * @throws ArrayIndexOutOfBoundsException invalid format.
	 * @throws lotsofotherstuff 
	 */
	public static Game getGameByName(String name) throws IOException {
		IO.openInputFile(ROOT + "/" + name);
		String[] parts = IO.readLine().split(":");
		int scorea = Integer.parseInt(parts[0]);
		int scoreb = Integer.parseInt(parts[1]);
		LinkedList<Player> teama = new LinkedList<Player>();
		parts = IO.readLine().split(",");
		for (String player: parts) {
			teama.add(PlayerIO.getPlayerFull(player));
		}
		LinkedList<Player> teamb = new LinkedList<Player>();
		parts = IO.readLine().split(",");
		for (String player: parts) {
			teamb.add(PlayerIO.getPlayerFull(player));
		}
		IO.closeInputFile();
		return new Game(name, teama, teamb, scorea, scoreb);
	}
	
	/**
	 * Record a game.
	 * @param g the game
	 */
	public static void saveGame(Game g) {
		IO.createOutputFile(ROOT + "/" + g.machineName());
		IO.println(g.getScoreA() + ":" + g.getScoreB());
		for (Player p: g.getTeamA()) {
			IO.print(p.getName() + ",");
		}
		IO.print("\n");
		for (Player p: g.getTeamB()) {
			IO.print(p.getName() + ",");
		}
		IO.print("\n");
		IO.closeOutputFile();
	}
}
