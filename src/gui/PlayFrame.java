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
	
	public PlayFrame(){
		super("Game Time!");
		setLayout(new GridLayout(1,3));
		createComponents();
		pack();
		repaint();		
	}

	private void createComponents() {
		GridLayout teamLayout = new GridLayout(6,1);
		teamLayout.setHgap(20);
				
		JPanel leftTeam = new JPanel();
		leftTeam.setLayout(teamLayout);
		add(leftTeam);
		
		JPanel leftPanel[] = new JPanel[teamLayout.getRows()];
		SpringLayout leftLayout[] = new SpringLayout[teamLayout.getRows()];
		for(int i=0;i<leftPanel.length;i++){
			leftPanel[i] = new JPanel();
			leftLayout[i] = new SpringLayout();
			
			leftPanel[i].setBackground(ColorScheme.DEFAULT_MAIN);			
			leftPanel[i].setLayout(leftLayout[i]);
			leftTeam.add(leftPanel[i]);
		}
		
		
		AlyButton a = new AlyButton("hhh");
		a.flipColors();
		add(a);
		add(new AlyButton("ooo"));
	}

	public void actionPerformed(ActionEvent ev) {
		
	}

}
