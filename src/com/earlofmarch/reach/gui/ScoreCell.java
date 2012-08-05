package com.earlofmarch.reach.gui;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.SpringLayout;
/**
 * A grid of score buttons for each player
 * @author Aly
 *
 */
@SuppressWarnings("serial")
public class ScoreCell extends JPanel implements ActionListener{

	private UIButton[] button;
	private GroupedCell parent;
	private JPanel mainPanel;
	
	public ScoreCell(GroupedCell g){
		super();
		setBackground(UI.colour.MAIN);	
		
		mainPanel = new JPanel();
		mainPanel.setBackground(null);
				
		int[] scores = {5,10,20,40,-5,-10,-20,-40};
		button = new UIButton[scores.length];
		
		GridLayout grid = new GridLayout(scores.length/2,2,5,15);
		mainPanel.setLayout(grid);
		
		for(int i=0;i<button.length;i++){
			button[i] = new UIButton("",false);
//			button[i].invertColors();
			button[i].setText((scores[i]>0?"+":"")+scores[i]);
			button[i].setActionCommand(scores[i]+"");
			button[i].addActionListener(this);
			button[i].setFont(new Font("Verdana",Font.PLAIN,14));
			mainPanel.add(button[i]);			
		}
		
		parent = g;
		
		SpringLayout layout = new SpringLayout();
		layout.putConstraint(SpringLayout.EAST, mainPanel, -15, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.NORTH, mainPanel, 15, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, mainPanel, 15, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.SOUTH, mainPanel, -15, SpringLayout.SOUTH, this);
		setLayout(layout);
		
		add(mainPanel);
		
	}
	
	public void actionPerformed(ActionEvent e) {
		parent.giveScore(Integer.parseInt(e.getActionCommand()));
	}
	
	public void setShowing(boolean visible){
		setBackground(visible?UI.colour.MAIN:UI.colour.BACKGROUND);
		for(int i=0;i<button.length;i++){			
			button[i].setVisible(visible);
		}
	}

}
