package com.earlofmarch.reach.gui;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.earlofmarch.reach.input.BuzzerBinding;
import com.earlofmarch.reach.input.BuzzerBindingFactory;
import com.earlofmarch.reach.input.Pair;
import com.earlofmarch.reach.model.CacheIO;
import com.earlofmarch.reach.model.GameIO;

@SuppressWarnings("serial")
public class StartScreen extends JFrame implements ActionListener{
	
	private static Logger log;
	private JPanel panel, optionsPanel;
	public JTextField input;
	private JLabel label;
	private JComboBox combo;
	private UIButton go, newGame, contGame, options;
	private UICheckBox debug, failSound, beepSound, successSound, autoUp;
	
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
				
		animateTitle();
	}
	
	private void animateTitle() {
		Timer timer = new Timer();	
		timer.scheduleAtFixedRate(new TimerTask() {
			int time = -2;
			public void run()
			{					
				if(time < 17){
					if(time < 15 && time > 0)
						label.setText(getColoredText(time,time+3));
					else if(time < 17 && time > 0)
						label.setText(getColoredText(time,time+(time-15)));
					else
						label.setText(getColoredText(0,time+3));
					time++;					
				}else{
					time = -2;
				}				
			}
		}, 100, 100);
	}
		
	private String getColoredText(int start, int end){
		String text = "REACH FOR THE TOP";
		if(end>text.length())
			end = text.length();
		String coloredRegion = text.substring(start,end);
		if(end == text.length())
			return "<html><font color=#20B2AA>"+text.subSequence(0, start)+"</font>" +
					"<font color=#4682B4>"+coloredRegion+"</font></html>";
		
		return "<html><font color=#20B2AA>"+text.subSequence(0, start)+"</font>" +
				"<font color=#4682B4>"+coloredRegion+"</font>" +
				"<font color=#20B2AA>"+text.subSequence(end, text.length())+"</font></html>";
	}

	private void createComponents() {
		label = new JLabel(getColoredText(0,0));
		label.setFont(UI.getFont(30));
		label.setForeground(UI.colour.SECONDARY);
		label.setBounds(20, 40, 440, 30);
		label.setHorizontalAlignment(JLabel.CENTER);		
		add(label);
		
		newGame = new UIButton("NEW GAME", true);
		newGame.invertColors();
		newGame.setFont(UI.getFont(16));
		newGame.setBounds(40, 110, 190, 60);
		newGame.addActionListener(this);
		newGame.setActionCommand("new");
		newGame.setBorder(BorderFactory.createMatteBorder(0, 0, 8, 0, UI.colour.ROLLOVER));
		add(newGame);
		
		contGame = new UIButton("CONTINUE GAME", true);
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
		input.setMargin(new Insets(0,40,0,0));
		input.setBackground(UI.colour.MAIN);
		input.setForeground(UI.colour.SECONDARY);
		input.setFont(UI.getFont(18));
		input.setText("Enter pack name...");
		input.setBounds(0, 10, 390, 60);
		input.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 5, 0, 5, UI.colour.SECONDARY),BorderFactory.createMatteBorder(0, 10, 0, 10, UI.colour.MAIN)));
		input.addFocusListener(new FocusListener() {
		      public void focusGained(FocusEvent e) {
		          input.setText("");
		      }
		      public void focusLost(FocusEvent e) {
		    	  if(input.getText().isEmpty())
		    		  input.setText("Enter pack name...");
		      }
		});
		panel.add(input);
		
		Object[] temp = GameIO.getGameNames().keySet().toArray();
		if(temp.length!=0)
			combo = new JComboBox(temp);
		else{
			String[] model = {"No games available"};
			combo = new JComboBox(model);
		}
		combo.setFocusable(false);
		combo.setForeground(UI.colour.SECONDARY);
		combo.setFont(input.getFont());
		combo.setBounds(input.getBounds());
		combo.setVisible(false);
		combo.setBorder(BorderFactory.createMatteBorder(0, 5, 0, 5, UI.colour.SECONDARY));
		panel.add(combo);
		
		go = new UIButton("GO",false);
		go.setFont(UI.getFont(16));
		go.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 5, UI.colour.SECONDARY));
		go.setBounds(195, 80, 195, 40);
		go.addActionListener(this);
		go.setActionCommand("go");
		panel.add(go);
		
		options = new UIButton("  Options", false);
		options.setBorder(BorderFactory.createMatteBorder(0, 5, 0, 0, UI.colour.SECONDARY));
		options.setHorizontalAlignment(JButton.LEFT);
		options.setFont(UI.getFont(16));
		options.addActionListener(this);
		options.setActionCommand("options");
		options.setBounds(0, 80, 195, 40);
		panel.add(options);
		
		optionsPanel = new JPanel();
		optionsPanel.setBackground(UI.colour.BACKGROUND);
		optionsPanel.setLayout(null);
		optionsPanel.setVisible(false);
		optionsPanel.setBorder(BorderFactory.createMatteBorder(0, 5, 0, 5, UI.colour.SECONDARY));
		optionsPanel.setBounds(40, 330, 390, 130);
		add(optionsPanel);
		
		debug = new UICheckBox("Debug Mode");
		System.out.println(Main.debug);
		debug.setSelected(Main.debug);
		debug.setBounds(15, 0, 175, 40);
		debug.setFont(UI.getFont(14));
		debug.setFocusable(false);	
		optionsPanel.add(debug);
		
		failSound = new UICheckBox("Fail Sound");
		failSound.setSelected(Main.failSound);
		failSound.setBounds(15, 45, 175, 40);
		failSound.setFont(UI.getFont(14));
		failSound.setFocusable(false);	
		optionsPanel.add(failSound);
		
		beepSound = new UICheckBox("Timer Sound");
		beepSound.setSelected(Main.timerSound);
		beepSound.setBounds(15, 90, 175, 40);
		beepSound.setFont(UI.getFont(14));
		beepSound.setFocusable(false);	
		optionsPanel.add(beepSound);
		
		successSound = new UICheckBox("Success Sound");
		successSound.setSelected(Main.successSound);
		successSound.setBounds(200, 0, 175, 40);
		successSound.setFont(UI.getFont(14));
		successSound.setFocusable(false);	
		optionsPanel.add(successSound);
		
		autoUp = new UICheckBox("Auto Unbuzz");
		autoUp.setSelected(Main.autoUp);
		autoUp.setBounds(200, 45, 175, 40);
		autoUp.setFont(UI.getFont(14));
		autoUp.setFocusable(false);	
		optionsPanel.add(autoUp);
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
		CacheIO.loadOptions();
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
	
	public void startMain(boolean attemptBuzzers, String name){	
		Main m;
		BuzzerBinding buzzers;
		//Save the options
		Main.debug = debug.isSelected();
		Main.failSound = failSound.isSelected();
		Main.timerSound = beepSound.isSelected();
		Main.successSound = successSound.isSelected();
		Main.autoUp = autoUp.isSelected();
		CacheIO.saveOptions();
		
		if(attemptBuzzers){			
			try {
				buzzers = BuzzerBindingFactory.getBinding();
				buzzers.setButtonSensitivity(true, false, false, false, false);
				m = new Main(buzzers, name);
				buzzers.registerBuzzHandler(new Handler(buzzers, m));
			} catch (IOException e) {
				log.log(Level.WARNING, "An error occurred binding to the buzzers.", e);
				e.printStackTrace();
				log.log(Level.WARNING, "Buzzers will not function properly.");
				m = new Main(null, name);
			}
		}else
			m = new Main(null, name);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("new")){
			input.setVisible(true);
			combo.setVisible(false);
			panel.setVisible(true);
			newGame.setBorder(BorderFactory.createMatteBorder(0, 0, 8, 0, UI.colour.ROLLOVER));
			contGame.setBorder(null);
		}else if(e.getActionCommand().equals("cont")){
			input.setVisible(false);
			combo.setVisible(true);
			panel.setVisible(true);
			newGame.setBorder(null);
			contGame.setBorder(BorderFactory.createMatteBorder(0, 0, 8, 0, UI.colour.ROLLOVER));
		}else if(e.getActionCommand().equals("go")){
			if(input.isVisible()&&input.getText().isEmpty()){
				input.setText("Enter pack name...");
				label.requestFocus();
				return;
			}else if(input.isVisible()){
				if(input.getText().equals("Enter pack name..."))
					return;
				startMain(!debug.isSelected(),input.getText());
			}else if(combo.isVisible()){
				if(combo.getSelectedItem().equals("No games available"))
					return;
				startMain(!debug.isSelected(),combo.getSelectedItem().toString());				
			}
			dispose();
		}else if(e.getActionCommand().equals("options")){
			if(this.getHeight()==360){
				setSize(this.getWidth(),this.getHeight()+150);
				optionsPanel.setVisible(true);
			}else{
				setSize(this.getWidth(),360);
				optionsPanel.setVisible(false);
			}
		}
	}
}
