package gui;

import java.awt.*;

import javax.swing.*;

import reachForTheTop.*;

public class PlayerFrame extends DefaultFrame{
	
	Player player;
	
	public PlayerFrame(Point d){
		super("Choose a Player");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setLocation((int)d.getX()+20,(int)d.getY()+20);
		setMinimumSize(new Dimension(400,400));
		pack();
		
		System.out.println("Players Found: ");
		for(String s : PlayerIO.getAllPlayers())
			System.out.println(s);
	}
	
	public Player getPlayer(){
		return player;
	}

}
