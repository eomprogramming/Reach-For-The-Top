package gui;
import reachForTheTop.PlayerIO;
/**
 * Launches the game.
 */
public class Launcher {
	public static void main(String args[]){
		PlayerIO.makeScoreFolder();	   
		new PlayFrame();
		System.out.println("Launched");
	}
}
