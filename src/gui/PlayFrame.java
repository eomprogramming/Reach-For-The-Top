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
	
	private AlyButton scoreButtonLeft[][];
	
	public PlayFrame(){
		super("Game Time!");
		setLayout(new GridLayout(1,3));
		createComponents();
		pack();
		repaint();	
		
	}

	private void createComponents() {
		GridLayout mainLayout = new GridLayout(6,1);
				
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
		
		scoreButtonLeft = new AlyButton[4][4];
		JLabel[] playerNames = new JLabel[4];
		JPanel[] leftSuperSubPanel = new JPanel[4];		
		
		for(int i=0;i<scoreButtonLeft.length;i++){
						
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

		
		for(int i=0;i<scoreButtonLeft.length;i++)
			for(int j=0;j<scoreButtonLeft[0].length;j++){
				scoreButtonLeft[i][j] = new AlyButton((j+1)*10+"");
		//		scoreButtonLeft[i][j].flipColors();
				leftSuperSubPanel[i].add(scoreButtonLeft[i][j]);
			}
			
		
		AlyButton a = new AlyButton("hhh");
		a.flipColors();
		add(a);
		add(new AlyButton("ooo"));
	}

	public void actionPerformed(ActionEvent ev) {
		
	}

}
