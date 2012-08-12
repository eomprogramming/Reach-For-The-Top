package com.earlofmarch.reach.gui;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Spring;
import javax.swing.SpringLayout;
import javax.swing.border.Border;

import com.earlofmarch.reach.model.Player;
import com.earlofmarch.reach.model.PlayerIO;
/**
 * The PlayerCell manages the GUI needed for a single player
 * @author Aly
 *
 */

@SuppressWarnings("serial")
public class PlayerCell extends JPanel implements ActionListener{
	
	private JLabel playerName;
	private UIButton button;
	private SpringLayout layout;
	private Main parent;
	private Player player;
		
	public PlayerCell(Main m){
		super();
		parent = m;
		setBackground(UI.colour.SECONDARY);		
		layout = new SpringLayout();
		createComponents();	
		setLayout(layout);	
	}
	
	private void createComponents() {
		playerName = new JLabel();
		playerName.setBackground(this.getBackground());
		playerName.setForeground(UI.colour.MAIN);
		playerName.setHorizontalAlignment(JTextField.CENTER);
		playerName.setFont(UI.getFont(15));
		playerName.setVisible(false);
				
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
			
			player = getPlayerFromUser();
			button.setVisible(false);
			if(player != null){
				playerName.setVisible(true);
				playerName.setText(player.getName());
				createButton(false);
			}else{
				button.setVisible(true);
			}
			
		}else if(e.getSource() == button && button.getText().equals("x")){
			
			playerName.setVisible(false);
			playerName.setFocusable(true);
			playerName.setText("");
			createButton(true);
			
		}
	}
	
	private Player getPlayerFromUser() {
		
		LinkedList<Player> temp = PlayerIO.getAllPlayers(false);
		
		//Create an array of String from all the players' names.
		String list[] = new String[(temp==null?1:temp.size()+1)];
		list[0] = "Create new player";
		for(int i=1;i<list.length;i++)
			list[i] = temp.get(i-1).getName();
		
		String result = (String) JOptionPane.showInputDialog(parent.getContentPane(),null,"Choose Player",JOptionPane.PLAIN_MESSAGE,null,list,0);
		if(result == null)
			return null;
		
		//If they chose to make a new player, go through that process
		if(result.equals("Create new player")){
			JLabel label = new JLabel("Who's the new player?");
			label.setFont(UI.getFont(14));
			label.setHorizontalAlignment(JLabel.CENTER);
			String name = (String) JOptionPane.showInputDialog(parent.getContentPane(),label,"Add Player",JOptionPane.PLAIN_MESSAGE,null,null,null);
			
			//If they cancel, just quit
			if(name == null)
				return null;			
			
			//If they don't enter a name for the new guy, show error
			if(name.isEmpty()||name.equalsIgnoreCase("Create new Player")){
				label.setText("Invalid name!");
				JOptionPane.showMessageDialog(parent.getContentPane(),label,"Error",JOptionPane.PLAIN_MESSAGE,null);				
			}
			
			Arrays.sort(list);
			
			//Check if player already exists
			if(Arrays.binarySearch(list, name)<0){				
				Main.players.add(new Player(name));
				PlayerIO.updatePlayer(new Player(name));
				return new Player(name);
			}else{
				label.setText("Already exists!");
				JOptionPane.showMessageDialog(parent.getContentPane(),label,"Error",JOptionPane.PLAIN_MESSAGE,null);
				return null;
			}
		}else{
			if(!Main.players.contains(new Player(result)))
				Main.players.add(new Player(result));
			return new Player(result);		
		}		
	}	
	
	public Player getPlayer(){
		return player;
	}
}
