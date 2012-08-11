package com.earlofmarch.reach.model;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class PlayerIO {
	
	
	public static LinkedList<String> getAllPlayers(){
		LinkedList<String> names = new LinkedList<String>();
		//new File("Scores").mkdirs();
		IO.openInputFile("Scores\\names.txt");
		while(true){
			try {
				String s = IO.readLine();
				if(s!=null)
					names.offer(s);
				else
					break;
			} catch (IOException e) {
				
			} catch (NullPointerException e){
				IO.createOutputFile("Scores\\names.txt");
				IO.closeOutputFile();
				break;
			}
		}
		try {
			IO.closeInputFile();
		} catch (IOException e) {}
		return names;
	}
	
	public static Player getPlayer(String name){		
		try {
			IO.openInputFile("Scores\\"+name+".reach");	
			Player p = new Player(name);
			String s = IO.readLine();		
			int times = Integer.parseInt(s.substring(s.indexOf(":")+1,s.length()).trim());
			for(int i=0;i<times;i++)
				p.addPlay();
			s = IO.readLine();
			int score = Integer.parseInt(s.substring(s.indexOf(":")+1,s.length()).trim());
			p.increaseScore(score);
			return p;
			
		} catch (IOException e) {
		} catch(NullPointerException e){
		}
		PlayerIO.addPlayer(name);
		PlayerIO.savePlayer(new Player(name));
		return new Player(name);
		
	}
	
	public static void addPlayer(String name){
		IO.createOutputFile("Scores\\"+name+".reach");
		IO.println("times played: 0");
		IO.println("total: 0");
		IO.closeOutputFile();
		IO.createOutputFile("Scores\\names.txt",true);
		IO.println(name);
		IO.closeOutputFile();
	}
	
	public static void savePlayer(Player player){
		IO.createOutputFile("Scores\\"+player.getName()+".reach");
		IO.println("times played: "+player.getTimesPlayed());
		IO.println("total: "+player.getScore());
		IO.closeOutputFile();
	}

	public static void makeScoreFolder(){
		File f = new File("Scores");
		f.mkdirs();
//		Process p;
//		try {
//			p = Runtime.getRuntime().exec("attrib +h " + f.getPath());
//			p.waitFor();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
	}
}
