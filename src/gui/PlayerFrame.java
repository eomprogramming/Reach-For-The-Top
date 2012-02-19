package gui;

import java.awt.*;

import javax.swing.*;

import reachForTheTop.Player;

public class PlayerFrame extends DefaultFrame{
	
	Player player;
	
	public PlayerFrame(Point d){
		super("Choose a Player");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setLocation((int)d.getX()+30,(int)d.getY()+30);
	}
	
	public Player getPlayer(){
		return player;
	}

}
