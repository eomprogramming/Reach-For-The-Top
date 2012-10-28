package com.earlofmarch.reach.gui;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.UIManager;

import com.earlofmarch.reach.input.BuzzerBinding;
import com.earlofmarch.reach.input.BuzzerBindingFactory;
import com.earlofmarch.reach.input.Pair;
import com.earlofmarch.reach.model.Music;
import com.earlofmarch.reach.model.Player;
import com.earlofmarch.reach.model.PlayerIO;
/**
 * The Main class... Manages the GroupedCells and triggers
 * @author Aly
 *
 */
@SuppressWarnings("serial")
public class Main extends JFrame implements ActionListener, KeyListener{
	
	private GroupedCell[] cells;
	private JLabel scoreLabel;
	private int rightScore = 0, leftScore = 0;
	private static final int RIGHT = 1, LEFT = 2;
	public static LinkedList<Player> players;
	private static Logger log;
	
	static {
		log = Logger.getLogger("reach.gui");
		log.setLevel(Level.ALL);
		log.addHandler(new ConsoleHandler());
	}
	
	public Main(BuzzerBinding b){
		super("Reach for the Top");		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((int)(dim.getWidth()/2)-525,(int)(dim.getHeight()/2)-250);
		setMinimumSize(new Dimension(800,480));
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		
		getContentPane().setBackground(UI.colour.BACKGROUND);
		createComponents(b);
		
		pack();
		setSize(1050,500);	
		players = PlayerIO.getAllPlayers(false);
		if(players == null)
			players = new LinkedList<Player>();
		
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
		        public void run() {
		        	if(players!=null && players.size()!=0)
		        		PlayerIO.updateAll(players);
		        }
		    }, "Shutdown-thread"));
	}
	
	private void createComponents(BuzzerBinding b){
		SpringLayout layout = new SpringLayout();
		setLayout(layout);		
		
		scoreLabel = new JLabel("0          0");
		scoreLabel.setFont(UI.getFont(48));
		scoreLabel.setBackground(null);
		scoreLabel.setHorizontalAlignment(JLabel.CENTER);
		scoreLabel.setForeground(UI.colour.SECONDARY);	
		
		addKeyListener(this);
				
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
			cells[i] = new GroupedCell(this,LEFT, b);
			cells[i].setVisible(false);
			cells[i+4] = new GroupedCell(this,RIGHT, b);
			cells[i+4].setVisible(false);
			leftPanel.add(cells[i]);
			rightPanel.add(cells[i+4]);
		}
		animateCells();
		
		add(scoreLabel);	
		cellsPanel.add(leftPanel);		
		cellsPanel.add(rightPanel);
		
		add(cellsPanel);	
		
		JPanel bonusPanel = new JPanel(new FlowLayout());
		bonusPanel.setBackground(UI.colour.SECONDARY);
		
		String bonuses[] = {" 5 "," 10 "," 15 "," 20 ",};
		short numberOfPlaceHolders = 3;
		
		for(int i=0;i<(bonuses.length*2)+numberOfPlaceHolders;i++){
			UIButton bonusButton = new UIButton("",true);
			bonusButton.addActionListener(this);
			bonusButton.invertColors();
			if(i<4){
				bonusButton.setText(bonuses[i]);
				bonusButton.setActionCommand("left");
			}else if (i > 6){
				bonusButton.setText(bonuses[i-7]);
				bonusButton.setActionCommand("right");
			}else{
				JLabel placeholder = new JLabel(i==5?"BONUS":"       ");
				placeholder.setFont(UI.getFont(16));
				placeholder.setForeground(UI.colour.MAIN);
				bonusPanel.add(placeholder);
			}
			bonusPanel.add(bonusButton);
		}
		
		add(bonusPanel);
		
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, scoreLabel, 0, SpringLayout.HORIZONTAL_CENTER, getContentPane());
		layout.putConstraint(SpringLayout.NORTH, scoreLabel, 30, SpringLayout.NORTH, getContentPane());
		layout.putConstraint(SpringLayout.NORTH, cellsPanel, 10, SpringLayout.SOUTH, scoreLabel);
		layout.putConstraint(SpringLayout.SOUTH, cellsPanel, -10, SpringLayout.NORTH, bonusPanel);
		layout.putConstraint(SpringLayout.WEST, cellsPanel, 10, SpringLayout.WEST, getContentPane());
		layout.putConstraint(SpringLayout.EAST, cellsPanel, -10, SpringLayout.EAST, getContentPane());
		layout.putConstraint(SpringLayout.EAST, bonusPanel, 0, SpringLayout.EAST, getContentPane());
		layout.putConstraint(SpringLayout.WEST, bonusPanel, 0, SpringLayout.WEST, getContentPane());
		layout.putConstraint(SpringLayout.SOUTH, bonusPanel, -10, SpringLayout.SOUTH, getContentPane());		
	}
	
	private void animateCells() {
		Timer timer = new Timer();	
		timer.scheduleAtFixedRate(new TimerTask() {
			int time = 4;
			public void run()
			{					
				if(time > 0){
					//System.out.println((4-time)+" and "+(time+3));
					cells[4-time].setVisible(true);
					cells[time+3].setVisible(true);
					time--;					
				}else{
					this.cancel();
				}				
			}
		}, 100, 100);
	}

	/*
	 *  0 to 3 is the left side, 4-7 is the right side.
	 */
	public void trigger(int cell){
		for(GroupedCell gc : cells)
			if(gc.isTriggered())
				return;

		Music.playMusic(getClass().getClassLoader().getResource("assets/buzz.wav"));
		cells[cell].trigger();
	}
	
	public void giveScore(int score,int side){
		//Play music depending on score
		if(score > 0)
			Music.playMusic(getClass().getClassLoader().getResource("assets/good.wav"));
//		else if (score < 0)
//			Music.playMusic(getClass().getClassLoader().getResource("assets/fail.wav"));
		
		if(side == RIGHT)
			rightScore += score;
		else
			leftScore += score;
		
		scoreLabel.setText(leftScore+"          "+rightScore);
	}	
	
	public static void main(String args[]){
		Main m;
		BuzzerBinding buzzers;
		Scanner s = new Scanner(System.in);
		
		UIManager.put("OptionPane.background",UI.colour.MAIN);
		UIManager.put("Panel.background", UI.colour.MAIN);
		UIManager.put("ComboBox.background", UI.colour.MAIN);
		UIManager.put("ComboBox.selectionBackground", UI.colour.ROLLOVER);  
		UIManager.put("OptionPane.opaque",false);  
		UIManager.put("ComboBox.font",UI.getFont(15)); 
		
		try {
			buzzers = BuzzerBindingFactory.getBinding();
			buzzers.setButtonSensitivity(true, false, false, false, false);
			m = new Main(buzzers);
			buzzers.registerBuzzHandler(new Handler(buzzers, m));
		} catch (IOException e) {
			log.log(Level.WARNING, "An error occurred binding to the buzzers.", e);
			e.printStackTrace();
			log.log(Level.WARNING, "Buzzers will not function properly.");
			m = new Main(null);
		}
		
		System.out.println("||Animation Testing||\nTrigger from 1 to 8. Type 'exit' to quit.");		
		while(true){
			String input = s.nextLine().trim();
			if(!input.isEmpty() && input.charAt(0) >= '1' && input.charAt(0) <= '8'){
				m.trigger(Integer.parseInt(input.substring(0,1))-1);
			}else if(input.equalsIgnoreCase("exit")){
				System.exit(0);
			}else{
				System.out.println("Invalid, trigger from 1 to 8. Type 'exit' to quit.");
			}
		}		
	}
	
	private static class Handler implements Runnable {
		private BuzzerBinding buzzers;
		private Main main;
		
		Handler(BuzzerBinding b, Main m) {
			Main.log.log(Level.INFO, "Creating handler");
			buzzers = b;
			main = m;
		}

		@Override
		public void run() {
			Main.log.log(Level.INFO, "Triggering buzz.");
			Pair<Integer, Integer> result = buzzers.getCurrentBuzzed();
			main.trigger((4 * result.getFirst()) + result.getSecond());
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		UIButton in = (UIButton)e.getSource();
		giveScore(Integer.parseInt(in.getText().trim()),e.getActionCommand().startsWith("l")?Main.LEFT:Main.RIGHT);
	}

	
	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
	

	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyChar()==' '){
			for(GroupedCell gc : cells)
				if(gc.isTriggered()){
					gc.giveScore(0);
				}
		}
	}	
}
