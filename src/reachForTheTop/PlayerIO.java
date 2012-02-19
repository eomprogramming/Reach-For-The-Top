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

}
