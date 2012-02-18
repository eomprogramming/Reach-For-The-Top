package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Calendar;

import javax.swing.JFrame;

public class DefaultFrame extends JFrame{
	
	public DefaultFrame(String title){
		super("Reach for the Top - "+ title.trim());
		
		setMinimumSize(new Dimension(600,500));
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBackground(new Color(181,45,45));
		
	}

}
