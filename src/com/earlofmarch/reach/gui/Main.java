package com.earlofmarch.reach.gui;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
/**
 * The Main class... Manages the GroupedCells and triggers
 * @author Aly
 *
 */
@SuppressWarnings("serial")
public class Main extends JFrame{
	
	private GroupedCell[] cells;
	private JLabel scoreLabel;
	private int rightScore = 0, leftScore = 0;
	private static final int RIGHT = 1, LEFT = 2;
	
	public Main(){
		super("Reach for the Top");		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((int)(dim.getWidth()/2)-525,(int)(dim.getHeight()/2)-270);
		setMinimumSize(new Dimension(800,400));
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		
		getContentPane().setBackground(UI.colour.BACKGROUND);
		createComponents();
		
		pack();
		setSize(1050,540);	
		repaint();	
		
		trigger(3);
	}
	
	private void createComponents(){
		SpringLayout layout = new SpringLayout();
		setLayout(layout);		
		
		scoreLabel = new JLabel("0          0");
		scoreLabel.setFont(UI.getFont(48));
		scoreLabel.setBackground(null);
		scoreLabel.setHorizontalAlignment(JLabel.CENTER);
		scoreLabel.setForeground(UI.colour.SECONDARY);		
				
		GridLayout cellsLayout = new GridLayout(1,2,50,0);
		JPanel cellsPanel = new JPanel(cellsLayout);
		cellsPanel.setOpaque(false);
		
		GridLayout right = new GridLayout(1,4,15,0);		
		JPanel leftPanel = new JPanel(right);
		leftPanel.setOpaque(false);
		
		GridLayout left = new GridLayout(1,4,15,0);
		JPanel rightPanel = new JPanel(left);
		rightPanel.setOpaque(false);
				
		cells = new GroupedCell[8];		
		for(int i=0;i<4;i++){
			cells[i] = new GroupedCell(this,LEFT);
			cells[i+4] = new GroupedCell(this,RIGHT);
			leftPanel.add(cells[i]);
			rightPanel.add(cells[i+4]);
		}			

		add(scoreLabel);	
		cellsPanel.add(leftPanel);		
		cellsPanel.add(rightPanel);
		
		add(cellsPanel);	
		
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, scoreLabel, 0, SpringLayout.HORIZONTAL_CENTER, getContentPane());
		layout.putConstraint(SpringLayout.NORTH, scoreLabel, 30, SpringLayout.NORTH, getContentPane());
		layout.putConstraint(SpringLayout.NORTH, cellsPanel, 40, SpringLayout.SOUTH, scoreLabel);
		layout.putConstraint(SpringLayout.SOUTH, cellsPanel, 10, SpringLayout.SOUTH, getContentPane());
		layout.putConstraint(SpringLayout.WEST, cellsPanel, 10, SpringLayout.WEST, getContentPane());
		layout.putConstraint(SpringLayout.EAST, cellsPanel, -10, SpringLayout.EAST, getContentPane());
				
	}
	
	/*
	 *  0 to 3 is the left side, 4-7 is the right side.
	 */
	public void trigger(int cell){
		//Buzzer sound to come!
		cells[cell].trigger();
	}
	
	public void giveScore(int score,int side){
		if(side == RIGHT)
			rightScore += score;
		else
			leftScore += score;

		scoreLabel.setText(leftScore+"          "+rightScore);
	}	
	
	public static void main(String args[]){
		new Main();		
	}
}
