package com.earlofmarch.reach.gui;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Spring;
import javax.swing.SpringLayout;
import javax.swing.border.Border;
/**
 * The PlayerCell manages the GUI needed for a single player
 * @author Aly
 *
 */

@SuppressWarnings("serial")
public class PlayerCell extends JPanel implements ActionListener{
	
	private JTextField playerName;
	private UIButton button;
	private boolean showPlayer = false;
	private SpringLayout layout;
	private Border defaultBorder;
		
	public PlayerCell(){
		super();
		setBackground(UI.colour.SECONDARY);		
		layout = new SpringLayout();
		createComponents();	
		setLayout(layout);	
	}
	
	private void createComponents() {
		playerName = new JTextField();
		playerName.setBackground(this.getBackground());
		playerName.setForeground(UI.colour.MAIN);
		playerName.setHorizontalAlignment(JTextField.CENTER);
		playerName.setFont(UI.getFont(15));
		playerName.addActionListener(this);
		playerName.setVisible(false);
		playerName.setCaretColor(playerName.getForeground());
		
		defaultBorder = BorderFactory.createLineBorder(UI.colour.MAIN, 1);
		playerName.setBorder(defaultBorder);
		
		layout.putConstraint(SpringLayout.WEST, playerName, 10, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, playerName, 0, SpringLayout.VERTICAL_CENTER, this);
		layout.putConstraint(SpringLayout.EAST, playerName, -10, SpringLayout.EAST, this);
				
		add(playerName);
		createButton(true);		
	}
	
	private void createButton(boolean addButton){	
		if(button != null)
			reset();	
		
		if(addButton){	
			if(button!=null)
				remove(button);
			button = new UIButton("+",true);
			button.setFont(UI.getFont(25));
			button.addActionListener(this);
			button.invertColors();
			
			layout.putConstraint(SpringLayout.WEST, button, 0, SpringLayout.WEST, this);
			layout.putConstraint(SpringLayout.NORTH, button, 0, SpringLayout.NORTH, this);
			layout.putConstraint(SpringLayout.EAST, button, 0, SpringLayout.EAST, this);
			layout.putConstraint(SpringLayout.SOUTH, button, 0, SpringLayout.SOUTH, this);
			add(button);
		}else{		
			button = new UIButton("x",false);
			button.addActionListener(this);
			button.setFont(UI.getFont(18));
			button.invertColors();
			
			layout.putConstraint(SpringLayout.NORTH, button, 5, SpringLayout.NORTH, this);
			layout.putConstraint(SpringLayout.EAST, button, -10, SpringLayout.EAST, this);;
			add(button);	
		}
		this.revalidate();
		button.setVisible(true);		
	}

	private void reset(){
		layout.getConstraint(SpringLayout.WEST, button).setValue(Spring.UNSET);
		layout.getConstraint(SpringLayout.EAST, button).setValue(Spring.UNSET);
		layout.getConstraint(SpringLayout.NORTH, button).setValue(Spring.UNSET);
		layout.getConstraint(SpringLayout.SOUTH, button).setValue(Spring.UNSET);			
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == button && button.getText().equals("+")){
			
			button.setVisible(false);
			playerName.setVisible(true);
			playerName.requestFocus();
			
		}else if(e.getSource() == button && button.getText().equals("x")){
			
			playerName.setVisible(false);
			playerName.setEditable(true);
			playerName.setFocusable(true);
			playerName.setText("");
			playerName.setBorder(defaultBorder);
			createButton(true);
			
		}else if(e.getSource() == playerName && !playerName.getText().trim().isEmpty()){
			
			playerName.setVisible(true);
			playerName.setBorder(null);
			playerName.setEditable(false);
			playerName.setFocusable(false);
			createButton(false);
		}
	}

	
}
