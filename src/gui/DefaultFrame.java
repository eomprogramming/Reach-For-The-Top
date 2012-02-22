package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.UIManager;

/**
 * @author Aly
 *
 */
public class DefaultFrame extends JFrame{
	
	public DefaultFrame(String title){
		super("Reach for the Top - "+ title.trim());
		
		setMinimumSize(new Dimension(750,500));
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBackground(ColorScheme.DEFAULT_MAIN);
		UIManager.put("OptionPane.background",ColorScheme.DEFAULT_MAIN);
		UIManager.put("Panel.background", ColorScheme.DEFAULT_MAIN);
		UIManager.put("ComboBox.background", ColorScheme.DEFAULT_MAIN);
		UIManager.put("ComboBox.selectionBackground", ColorScheme.DEFAULT_ROLLOVER);  
		UIManager.put("OptionPane.opaque",false);  
		UIManager.put("ComboBox.font",new Font("Verdana",Font.BOLD,16)); 
		UIManager.put("Button.select", new Color(249,166,166));
		UIManager.put("Button.background", ColorScheme.DEFAULT_MAIN);
		UIManager.put("Button.focusable", false);
		UIManager.put("Button.foreground", ColorScheme.DEFAULT_SECONDARY);
	}

}
