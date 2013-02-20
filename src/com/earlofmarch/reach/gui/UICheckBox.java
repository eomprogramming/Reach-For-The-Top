package com.earlofmarch.reach.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class UICheckBox extends UIButton implements ActionListener{
	
	boolean selected = false;
	
	public UICheckBox(String text){
		super(text,false);
		this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(1, 15, 1, 1, getStateColor())
				,BorderFactory.createMatteBorder(0, 10, 0, 10, this.getBackground())));
		this.setHorizontalAlignment(JButton.LEFT);
		addActionListener(this);
	}
	
	private Color getStateColor(){
		if(selected)
			return new Color(156,238,82);
		return new Color(240,117,117);
	}
	
	public void setSelected(boolean s){
		selected = s;
		this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(1, 15, 1, 1, getStateColor())
				,BorderFactory.createMatteBorder(0, 10, 0, 10, this.getBackground())));
	}
	
	public boolean isSelected(){
		return selected;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		setSelected(!selected);
	}
}
