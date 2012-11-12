package com.earlofmarch.reach.gui;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.earlofmarch.reach.input.BuzzerBinding;
import com.earlofmarch.reach.input.BuzzerBindingFactory;
import com.earlofmarch.reach.input.Pair;

@SuppressWarnings("serial")
public class StartScreen extends JFrame implements ActionListener{
	
	private static Logger log;
	private JPanel panel;
	public JTextField input;
	private JComboBox combo;
	private UIButton go;
	
	public StartScreen(){
		super("Reach for the Top");		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((int)(dim.getWidth()/2)-240,(int)(dim.getHeight()/2)-255);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		setLayout(null);
		
		getContentPane().setBackground(UI.colour.BACKGROUND);
		createComponents();
		
		pack();
		setSize(480,360);	
	}
	
	private void createComponents() {
		JLabel label = new JLabel("REACH FOR THE TOP");
		label.setFont(UI.getFont(30));
		label.setForeground(UI.colour.SECONDARY);
		label.setBounds(20, 40, 440, 30);
		label.setHorizontalAlignment(JLabel.CENTER);		
		add(label);
		
		UIButton newGame = new UIButton("NEW GAME", true);
		newGame.invertColors();
		newGame.setFont(UI.getFont(16));
		newGame.setBounds(40, 110, 190, 60);
		newGame.addActionListener(this);
		newGame.setActionCommand("new");
		add(newGame);
		
		UIButton contGame = new UIButton("CONTINUE GAME", true);
		contGame.invertColors();
		contGame.setFont(UI.getFont(16));
		contGame.addActionListener(this);
		contGame.setActionCommand("cont");
		contGame.setBounds(40+newGame.getWidth()+10, 110, 190, 60);
		add(contGame);
		
		panel = new JPanel();
		panel.setBackground(null);
		panel.setLayout(null);
		panel.setBounds(40, 190, 400, 130);
		add(panel);
		
		input = new JTextField();
		input.setMargin(new Insets(0,20,0,0));
		input.setBackground(UI.colour.MAIN);
		input.setForeground(UI.colour.SECONDARY);
		input.setFont(UI.getFont(18));
		input.setText("Enter pack name...");
		input.setBounds(0, 10, 390, 60);
		input.addFocusListener(new FocusListener() {
		      public void focusGained(FocusEvent e) {
		          input.setText("");
		      }
		      public void focusLost(FocusEvent e) {
		    	  if(input.getText().isEmpty())
		    		  input.setText("Enter pack name...");
		      }
		});
		input.setVisible(false);
		panel.add(input);
		
		String[] temp = {"Sample Game 1","Sample Game 2","BAIOSFAds"};
		combo = new JComboBox(temp);
		combo.setFocusable(false);
		combo.setBounds(input.getBounds());
		combo.setVisible(false);
		panel.add(combo);
		
		go = new UIButton("GO",false);
		go.setBackground(UI.colour.BACKGROUND);
		go.setFont(UI.getFont(16));
		go.setBounds(330, 80, 60, 40);
		go.addActionListener(this);
		go.setActionCommand("go");
		go.setVisible(false);
		panel.add(go);
	}

	static {
		log = Logger.getLogger("reach.gui");
		log.setLevel(Level.ALL);
		log.addHandler(new ConsoleHandler());
	}	
	
	public static void main (String args[]){		
		UIManager.put("OptionPane.background",UI.colour.MAIN);
		UIManager.put("Panel.background", UI.colour.MAIN);
		UIManager.put("ComboBox.background", UI.colour.MAIN);
		UIManager.put("ComboBox.selectionBackground", UI.colour.ROLLOVER);  
		UIManager.put("OptionPane.opaque",false);  
		UIManager.put("ComboBox.font",UI.getFont(15)); 
		
		new StartScreen();
	}
	
	private static class Handler implements Runnable {
		private BuzzerBinding buzzers;
		private Main main;
		
		Handler(BuzzerBinding b, Main m) {
			StartScreen.log.log(Level.INFO, "Creating handler");
			buzzers = b;
			main = m;
		}

		@Override
		public void run() {
			StartScreen.log.log(Level.INFO, "Triggering buzz.");
			Pair<Integer, Integer> result = buzzers.getCurrentBuzzed();
			main.trigger((4 * result.getFirst()) + result.getSecond());
		}
		
	}
	
	public void startMain(boolean attemptBuzzers){	
		Main m;
		BuzzerBinding buzzers;
		
		if(attemptBuzzers){			
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
		}else
			m = new Main(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("new")){
			input.setVisible(true);
			combo.setVisible(false);
			go.setVisible(true);
		}else if(e.getActionCommand().equals("cont")){
			input.setVisible(false);
			combo.setVisible(true);
			go.setVisible(true);
		}else if(e.getActionCommand().equals("go")){
			startMain(false);
			dispose();
		}
	}
}