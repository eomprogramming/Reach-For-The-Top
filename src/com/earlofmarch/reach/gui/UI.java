package com.earlofmarch.reach.gui;
import java.awt.Color;
import java.awt.Font;

/**
 * Handles the colours
 * @author Aly
 *
 */
public class UI {
	
	static class colour {
	
		public final static Color MAIN = new Color(179,232,255);	
		
		public final static Color SECONDARY = new Color(0,116,166);
		
		public final static Color ROLLOVER =  new Color(83,203,255);
		
		public final static Color ROLLOVER_SECONDARY =  SECONDARY;
		
		public static final Color BACKGROUND = new Color(223,245,255);
	
	}
	
	public static Font getFont(int size){
		return new Font("Verdana",Font.PLAIN,size);
	}
}
