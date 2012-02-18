package gui;

import java.awt.*;

/**
 * @author Aly
 *
 */
public class PlayFrame extends DefaultFrame{
	
	public PlayFrame(){
		super("Game Time!");
		setLayout(new GridLayout(1,3));
		createComponents();
		
	}

	private void createComponents() {
		add(new AlyButton("Text"));
	}

}
