package com.earlofmarch.reach.gui;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.UIManager;

/**
 * This is a custom button 
 * @author Aly
 *
 */
@SuppressWarnings("serial")
public class UIButton extends JButton{
	
	private boolean inverted = false;
	
	public UIButton(){
		this("");
	}
	
	public UIButton(String text){
		this("", false);
	}
	
	public UIButton(String text, boolean addRollover){
		super(text);
		
		setBackground(UI.colour.MAIN);
		setForeground(UI.colour.SECONDARY);
		setFocusable(false);
		setFont(new Font("Verdana",Font.PLAIN,20));
		setBorder(null);
		
		if(addRollover){
			addMouseListener(new java.awt.event.MouseAdapter() {
			    public void mouseEntered(java.awt.event.MouseEvent evt) {
			        setBackground(UI.colour.ROLLOVER);
			        setForeground(UI.colour.ROLLOVER_SECONDARY);
			    }
			    public void mouseExited(java.awt.event.MouseEvent evt) {
			    	setBackground(UI.colour.MAIN);
			    	setForeground(UI.colour.SECONDARY);
			    	if(inverted)
			    		invertColors();
			    }
			    public void mousePressed(java.awt.event.MouseEvent evt) {
					UIManager.put("Button.select", UI.colour.ROLLOVER);
			    }
			    public void mouseReleased(java.awt.event.MouseEvent evt) {
					UIManager.put("Button.select", UI.colour.SECONDARY);
			    }
			});
		}else{
			addMouseListener(new java.awt.event.MouseAdapter() {
			    public void mouseEntered(java.awt.event.MouseEvent evt) {
			    	int size = getFont().getSize();
			    	int tenp = (int) (size*0.1);
			        setFont(new Font("Verdana",Font.BOLD,size+tenp));
			    }
			    public void mouseExited(java.awt.event.MouseEvent evt) {
			    	int size = getFont().getSize();
			    	int tenp = (int)(size*0.1);
			    	setFont(new Font("Verdana",Font.PLAIN,size-tenp));
			    }
			    public void mousePressed(java.awt.event.MouseEvent evt) {
			    	if(inverted)
			    		UIManager.put("Button.select", UI.colour.ROLLOVER_SECONDARY);
			    	else
			    		UIManager.put("Button.select", UI.colour.ROLLOVER);
			    }
			});
			
		}
	}
	
	public void invertColors(){
		inverted = true;
		Color c = getBackground();
		setBackground(getForeground());
		setForeground(c);
	}
}
