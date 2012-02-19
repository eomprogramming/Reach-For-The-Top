package gui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.LinkedList;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;

import reachForTheTop.*;

@Deprecated
public class PlayerFrame extends DefaultFrame{
	
	private Player player;
	private Player players[];
	private JList playerList;
	
	public PlayerFrame(Point d){
		super("Choose a Player");
		
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {}
		
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setLocation((int)d.getX()+20,(int)d.getY()+20);
		setMinimumSize(new Dimension(300,400));
		createComponents();
		pack();
		
		System.out.println("Players Found: ");
		for(String s : PlayerIO.getAllPlayers())
			System.out.println(s+" -- SCORE: "+PlayerIO.getPlayer(s).getScore());
	}
	
	private void createComponents() {		
		SpringLayout layout = new SpringLayout();
		setLayout(layout);
		
		LinkedList<String> temp = PlayerIO.getAllPlayers();
		players = new Player[temp.size()];
		for(int i=0;i<players.length;i++)
			players[i] = PlayerIO.getPlayer(temp.get(i));
		
		String list[] = new String[temp.size()]; 
		for(int i=0;i<list.length;i++)
			list[i] = temp.get(i);
				
		playerList = new JList(list); 
		playerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		playerList.setLayoutOrientation(JList.VERTICAL);
		playerList.setSelectedIndex(-1);
		playerList.setForeground(ColorScheme.DEFAULT_MAIN);
		playerList.setBackground(ColorScheme.DEFAULT_SECONDARY);
		playerList.setFont(new Font("Mangal",Font.PLAIN,20));
		playerList.setSelectionBackground(ColorScheme.DEFAULT_ROLLOVER);
		playerList.setSelectionForeground(ColorScheme.DEFAULT_ROLLOVER_TEXT);
		playerList.setBorder(null);
		MouseListener mouseListener = new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        if (e.getClickCount() == 2) {
		            int index = playerList.locationToIndex(e.getPoint());
		            player = players[index];
		            dispose();
		         }
		    }
		};
		playerList.addMouseListener(mouseListener);
		add(playerList);
		
		layout.putConstraint(SpringLayout.NORTH, playerList,
				20, SpringLayout.NORTH,getContentPane());
		layout.putConstraint(SpringLayout.EAST, playerList,
				-20, SpringLayout.EAST,getContentPane());
		layout.putConstraint(SpringLayout.WEST, playerList,
				20, SpringLayout.WEST,getContentPane());
		layout.putConstraint(SpringLayout.SOUTH, playerList,
				-20, SpringLayout.SOUTH,getContentPane());
		
		JScrollPane scroll = new JScrollPane(playerList);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		add(scroll);
		
		layout.putConstraint(SpringLayout.NORTH, scroll,
				20, SpringLayout.NORTH,getContentPane());
		layout.putConstraint(SpringLayout.EAST, scroll,
				-20, SpringLayout.EAST,getContentPane());
		layout.putConstraint(SpringLayout.WEST, scroll,
				20, SpringLayout.WEST,getContentPane());
		layout.putConstraint(SpringLayout.SOUTH, scroll,
				-20, SpringLayout.SOUTH,getContentPane());
		
		addWindowListener(this.closeMonitor);		
		
	}


	@Deprecated
	public Player getPlayer(){
		return player;
	}
	
	public WindowListener closeMonitor = new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
        	if(playerList.getSelectedIndex()>0)
        		player = players[playerList.getSelectedIndex()];
        }
    };

}
