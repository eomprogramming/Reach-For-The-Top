package reachForTheTop;

import java.io.IOException;
import java.util.LinkedList;

public class PlayerIO {
	
	
	public static LinkedList<String> getAllPlayers(){
		LinkedList<String> names = new LinkedList<String>();
		IO.openInputFile("Scores\\names.txt");
		while(true){
			try {
				String s = IO.readLine();
				if(s!=null)
					names.offer(s);
				else
					break;
			} catch (IOException e) {}				
		}
		return names;
	}
	
	public static Player getPlayer(String name){
		IO.openInputFile("Scores\\"+name+".reach");	
		Player p = new Player(name);
		try {
			String s = IO.readLine();		
			int times = Integer.parseInt(s.substring(s.indexOf(":")+1,s.length()).trim());
			for(int i=0;i<times;i++)
				p.addPlay();
			s = IO.readLine();
			int score = Integer.parseInt(s.substring(s.indexOf(":")+1,s.length()).trim());
			p.increaseScore(score);
			
		} catch (IOException e) {}
		return p;
	}
	
	public static void addPlayer(String name){
		IO.createOutputFile("Scores\\"+name+".reach");
		IO.println("times played: 0");
		IO.println("total: 0");
		IO.closeOutputFile();
		IO.createOutputFile("Scores\\names.txt",true);
		IO.print("");
		IO.println(name);
		IO.closeOutputFile();
	}

}
