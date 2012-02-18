package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * @author Aly
 *
 */
public class PlayFrame extends DefaultFrame implements ActionListener{
	
	private AlyButton scoreButtonRight[][];
	private GridLayout mainLayout = new GridLayout(6,1);
	
	public PlayFrame(){
		super("Game Time!");
		setLayout(new GridLayout(1,3));
		createComponents();
		pack();
		repaint();	
		
	}

	private void createComponents() {
		
		createLeftPanel();
		
		AlyButton a = new AlyButton("hhh");
		add(a);
		/*
		 *
		 * 
		 * 
		 * LOOK AWAY!
		 * PLEASE
		 * THIS WAS HAO'S IDEA!
		 * 
		 * 
		 * Yes, it was copy pasted :(
		 * 
		 * 
		 * 
		 */
		createRightPanel();
		
	}
	
	private void createRightPanel() {
		JPanel rightMainPanel = new JPanel();
		rightMainPanel.setLayout(mainLayout);
		add(rightMainPanel);
		
		JPanel rightSubPanel[] = new JPanel[mainLayout.getRows()];
		SpringLayout rightSubLayout[] = new SpringLayout[mainLayout.getRows()];
		for(int i=0;i<rightSubPanel.length;i++){
			rightSubPanel[i] = new JPanel();
			rightSubLayout[i] = new SpringLayout();
			
			rightSubPanel[i].setBackground(ColorScheme.DEFAULT_MAIN);			
			rightSubPanel[i].setLayout(rightSubLayout[i]);
			rightMainPanel.add(rightSubPanel[i]);
		}
		
		JLabel teamTwoLabel = new JLabel("TEAM TWO!");
		teamTwoLabel.setBackground(ColorScheme.DEFAULT_SECONDARY);
		teamTwoLabel.setForeground(ColorScheme.DEFAULT_MAIN);
		teamTwoLabel.setFont(new Font("Mangal",Font.BOLD,24));
		teamTwoLabel.setHorizontalAlignment(JLabel.CENTER);
		teamTwoLabel.setVerticalAlignment(JLabel.CENTER);
		teamTwoLabel.setOpaque(true);
		
		rightSubLayout[0].putConstraint(SpringLayout.WEST, teamTwoLabel,
				15, SpringLayout.WEST,rightSubPanel[0]);
		rightSubLayout[0].putConstraint(SpringLayout.NORTH, teamTwoLabel,
				0, SpringLayout.NORTH,rightSubPanel[0]);
		rightSubLayout[0].putConstraint(SpringLayout.EAST, teamTwoLabel,
				-15, SpringLayout.EAST,rightSubPanel[0]);
		rightSubLayout[0].putConstraint(SpringLayout.SOUTH, teamTwoLabel,
				-10, SpringLayout.SOUTH,rightSubPanel[0]);
	
		rightSubPanel[0].add(teamTwoLabel);
		
		scoreButtonRight = new AlyButton[4][4];
		JLabel[] playerNames = new JLabel[4];
		JPanel[] rightSuperSubPanel = new JPanel[4];		
		
		for(int i=0;i<scoreButtonRight.length;i++){
						
			playerNames[i] = new JLabel("   Player");
			playerNames[i].setBackground(ColorScheme.DEFAULT_SECONDARY);
			playerNames[i].setForeground(ColorScheme.DEFAULT_MAIN);
			playerNames[i].setOpaque(true);
			playerNames[i].setVerticalAlignment(JLabel.TOP);
			playerNames[i].setFont(new Font("Mangal",Font.PLAIN,16));
			
			rightSubLayout[i+1].putConstraint(SpringLayout.NORTH, playerNames[i],
					0, SpringLayout.NORTH,rightSubPanel[i+1]);
			rightSubLayout[i+1].putConstraint(SpringLayout.WEST, playerNames[i],
					15, SpringLayout.WEST,rightSubPanel[i+1]);
			rightSubLayout[i+1].putConstraint(SpringLayout.EAST, playerNames[i],
					-15, SpringLayout.EAST,rightSubPanel[i+1]);
			rightSubLayout[i+1].putConstraint(SpringLayout.SOUTH, playerNames[i],
					0, SpringLayout.SOUTH,rightSubPanel[i+1]);
						
			
			rightSuperSubPanel[i] = new JPanel();
			GridLayout g = new GridLayout(1,4);
			g.setHgap(5);
			rightSuperSubPanel[i].setLayout(g);
			rightSuperSubPanel[i].setBackground(ColorScheme.DEFAULT_SECONDARY);						
			
			rightSubLayout[i+1].putConstraint(SpringLayout.NORTH, rightSuperSubPanel[i],
				30, SpringLayout.NORTH,rightSubPanel[i+1]);
			rightSubLayout[i+1].putConstraint(SpringLayout.SOUTH, rightSuperSubPanel[i],
					-15, SpringLayout.SOUTH,rightSubPanel[i+1]);
			rightSubLayout[i+1].putConstraint(SpringLayout.WEST, rightSuperSubPanel[i],
					10, SpringLayout.WEST,playerNames[i]);
			rightSubLayout[i+1].putConstraint(SpringLayout.EAST, rightSuperSubPanel[i],
					-10, SpringLayout.EAST,playerNames[i]);
			
			rightSubPanel[i+1].add(rightSuperSubPanel[i]);
			rightSubPanel[i+1].add(playerNames[i]);
		}

		
		for(int i=0;i<scoreButtonRight.length;i++)
			for(int j=0;j<scoreButtonRight[0].length;j++){
				scoreButtonRight[i][j] = new AlyButton((j+1)*10+"");
				rightSuperSubPanel[i].add(scoreButtonRight[i][j]);
			}			
	}

	private void createLeftPanel(){
		JPanel leftMainPanel = new JPanel();
		leftMainPanel.setLayout(mainLayout);
		add(leftMainPanel);
		
		JPanel leftSubPanel[] = new JPanel[mainLayout.getRows()];
		SpringLayout leftSubLayout[] = new SpringLayout[mainLayout.getRows()];
		for(int i=0;i<leftSubPanel.length;i++){
			leftSubPanel[i] = new JPanel();
			leftSubLayout[i] = new SpringLayout();
			
			leftSubPanel[i].setBackground(ColorScheme.DEFAULT_MAIN);			
			leftSubPanel[i].setLayout(leftSubLayout[i]);
			leftMainPanel.add(leftSubPanel[i]);
		}
		
		JLabel teamOneLabel = new JLabel("TEAM ONE!");
		teamOneLabel.setBackground(ColorScheme.DEFAULT_SECONDARY);
		teamOneLabel.setForeground(ColorScheme.DEFAULT_MAIN);
		teamOneLabel.setFont(new Font("Mangal",Font.BOLD,24));
		teamOneLabel.setHorizontalAlignment(JLabel.CENTER);
		teamOneLabel.setVerticalAlignment(JLabel.CENTER);
		teamOneLabel.setOpaque(true);
		
		leftSubLayout[0].putConstraint(SpringLayout.WEST, teamOneLabel,
				15, SpringLayout.WEST,leftSubPanel[0]);
		leftSubLayout[0].putConstraint(SpringLayout.NORTH, teamOneLabel,
				0, SpringLayout.NORTH,leftSubPanel[0]);
		leftSubLayout[0].putConstraint(SpringLayout.EAST, teamOneLabel,
				-15, SpringLayout.EAST,leftSubPanel[0]);
		leftSubLayout[0].putConstraint(SpringLayout.SOUTH, teamOneLabel,
				-10, SpringLayout.SOUTH,leftSubPanel[0]);
	
		leftSubPanel[0].add(teamOneLabel);
		
		scoreButtonRight = new AlyButton[4][4];
		JLabel[] playerNames = new JLabel[4];
		JPanel[] leftSuperSubPanel = new JPanel[4];		
		
		for(int i=0;i<scoreButtonRight.length;i++){
						
			playerNames[i] = new JLabel("   Player");
			playerNames[i].setBackground(ColorScheme.DEFAULT_SECONDARY);
			playerNames[i].setForeground(ColorScheme.DEFAULT_MAIN);
			playerNames[i].setOpaque(true);
			playerNames[i].setVerticalAlignment(JLabel.TOP);
			playerNames[i].setFont(new Font("Mangal",Font.PLAIN,16));
			
			leftSubLayout[i+1].putConstraint(SpringLayout.NORTH, playerNames[i],
					0, SpringLayout.NORTH,leftSubPanel[i+1]);
			leftSubLayout[i+1].putConstraint(SpringLayout.WEST, playerNames[i],
					15, SpringLayout.WEST,leftSubPanel[i+1]);
			leftSubLayout[i+1].putConstraint(SpringLayout.EAST, playerNames[i],
					-15, SpringLayout.EAST,leftSubPanel[i+1]);
			leftSubLayout[i+1].putConstraint(SpringLayout.SOUTH, playerNames[i],
					0, SpringLayout.SOUTH,leftSubPanel[i+1]);
						
			
			leftSuperSubPanel[i] = new JPanel();
			GridLayout g = new GridLayout(1,4);
			g.setHgap(5);
			leftSuperSubPanel[i].setLayout(g);
			leftSuperSubPanel[i].setBackground(ColorScheme.DEFAULT_SECONDARY);						
			
			leftSubLayout[i+1].putConstraint(SpringLayout.NORTH, leftSuperSubPanel[i],
				30, SpringLayout.NORTH,leftSubPanel[i+1]);
			leftSubLayout[i+1].putConstraint(SpringLayout.SOUTH, leftSuperSubPanel[i],
					-15, SpringLayout.SOUTH,leftSubPanel[i+1]);
			leftSubLayout[i+1].putConstraint(SpringLayout.WEST, leftSuperSubPanel[i],
					10, SpringLayout.WEST,playerNames[i]);
			leftSubLayout[i+1].putConstraint(SpringLayout.EAST, leftSuperSubPanel[i],
					-10, SpringLayout.EAST,playerNames[i]);
			
			leftSubPanel[i+1].add(leftSuperSubPanel[i]);
			leftSubPanel[i+1].add(playerNames[i]);
		}

		
		for(int i=0;i<scoreButtonRight.length;i++)
			for(int j=0;j<scoreButtonRight[0].length;j++){
				scoreButtonRight[i][j] = new AlyButton((j+1)*10+"");
				leftSuperSubPanel[i].add(scoreButtonRight[i][j]);
			}			
	}

	public void actionPerformed(ActionEvent ev) {
		
	}

}
