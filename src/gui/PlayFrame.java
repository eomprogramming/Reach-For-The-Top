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
		teamOneLabel.setFont(new Font("Mangal",Font.BOLD,20));
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
				-15, SpringLayout.SOUTH,leftSubPanel[0]);
	
		leftSubPanel[0].add(teamOneLabel);
		
		scoreButtonLeft = new AlyButton[4][4];
		JLabel[] playerNames = new JLabel[4];
		JPanel[] leftSuperSubPanel = new JPanel[4];		
		
		for(int i=0;i<scoreButtonLeft.length;i++){
						
			playerNames[i] = new JLabel("   Player");
			playerNames[i].setBackground(ColorScheme.DEFAULT_SECONDARY);
			playerNames[i].setForeground(ColorScheme.DEFAULT_MAIN);
			playerNames[i].setOpaque(true);
			playerNames[i].setFont(new Font("Mangal",Font.PLAIN,16));
			
			leftSubLayout[i].putConstraint(SpringLayout.NORTH, playerNames[i],
					0, SpringLayout.NORTH,leftSubPanel[i]);
			leftSubLayout[i].putConstraint(SpringLayout.WEST, playerNames[i],
					15, SpringLayout.WEST,leftSubPanel[i]);
			leftSubLayout[i].putConstraint(SpringLayout.EAST, playerNames[i],
					-15, SpringLayout.EAST,leftSubPanel[i]);
			leftSubPanel[i].add(playerNames[i]);
			
			
			leftSuperSubPanel[i] = new JPanel();
			leftSuperSubPanel[i].setBackground(ColorScheme.DEFAULT_MAIN);						
			
			leftSubLayout[i].putConstraint(SpringLayout.SOUTH, teamOneLabel,
				20, SpringLayout.SOUTH,leftSubPanel[0]);
		}

		
		for(int i=0;i<scoreButtonLeft.length;i++)
			for(int j=0;j<scoreButtonLeft[0].length;j++){
				scoreButtonLeft[i][j] = new AlyButton((j+1)*10+"");
				
			}
			
		
		AlyButton a = new AlyButton("hhh");
		a.flipColors();
		add(a);
		add(new AlyButton("ooo"));
	}

	public void actionPerformed(ActionEvent ev) {
		
	}

}
